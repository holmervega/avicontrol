package Controlador;

import Modelo.PedidoCabecera;
import Modelo.PedidoDetalle;
import Modelo.Productos;
import Modelo.Unidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PedidoDetalleDAO {

    //listar los pedidos en la vista de pedidos.jsp
    public List<PedidoDetalle> listarPedidos() {
        List<PedidoDetalle> lista = new ArrayList<>();

        // Crear la conexión dentro del método
        Conexion miconexion = new Conexion();
        Connection conn = miconexion.getConn();

        // Modificar la consulta SQL para no agrupar los productos, cantidades y precios
        String sql = "SELECT pc.idPedidoCabecera AS idCabecera, pc.numeroPedido, pc.fechaPedido, "
                + "p.nombres AS nombrePersona, p.apellidos AS apellidoPersona, p.numeroIdentificacion AS identificacionPersona, "
                + "pr.nombre AS nombreProducto, pr.descripcion AS descripcionProducto, "
                + "u.descripcionUnidades AS descripcionUnidad, "
                + "pd.cantidad, pd.precioUnitario, pd.valorPedido "
                + "FROM pedidodetalle pd "
                + "JOIN pedidocabecera pc ON pd.PedidoCabecera_idPedidoCabecera = pc.idPedidoCabecera "
                + "JOIN persona p ON pc.Persona_idPersona = p.idPersona "
                + "JOIN productos pr ON pd.Productos_idProductos = pr.idProductos "
                + "JOIN unidades u ON pd.Unidades_idUnidades = u.idUnidades "
                + "ORDER BY pc.idPedidoCabecera";

        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            Map<Integer, PedidoDetalle> mapaPedidos = new HashMap<>();

            while (rs.next()) {
                int idPedidoCabecera = rs.getInt("idCabecera");

                // Si el pedido no existe en el mapa, crear uno nuevo
                PedidoDetalle pd = mapaPedidos.get(idPedidoCabecera);
                if (pd == null) {
                    pd = new PedidoDetalle();

                    // PedidoCabecera
                    PedidoCabecera cabecera = new PedidoCabecera();
                    cabecera.setIdPedidoCabecera(idPedidoCabecera);
                    cabecera.setNumeroPedido(rs.getInt("numeroPedido"));
                    cabecera.setFechaPedido(rs.getString("fechaPedido"));
                    pd.setPedidoCabecera(cabecera);

                    // Datos de Persona
                    cabecera.setNombrePersona(rs.getString("nombrePersona"));
                    cabecera.setApellidoPersona(rs.getString("apellidoPersona"));
                    cabecera.setNumeroIdentificacion(rs.getString("identificacionPersona"));

                    // Inicializar las listas
                    pd.setProductos(new ArrayList<>());
                    pd.setUnidades(new ArrayList<>());
                    pd.setCantidades(new ArrayList<>());
                    pd.setPreciosUnitarios(new ArrayList<>());

                    mapaPedidos.put(idPedidoCabecera, pd);
                }

                // Crear y agregar el producto
                Productos producto = new Productos();
                producto.setNombre(rs.getString("nombreProducto"));
                producto.setDescripcion(rs.getString("descripcionProducto"));
                pd.getProductos().add(producto);

                // Crear y agregar la unidad
                Unidades unidad = new Unidades();
                unidad.setDescripcionUnidades(rs.getString("descripcionUnidad"));
                pd.getUnidades().add(unidad);

                // Agregar la cantidad y el precio unitario
                pd.getCantidades().add(rs.getDouble("cantidad"));
                pd.getPreciosUnitarios().add(rs.getDouble("precioUnitario"));

                // No necesitamos agregar el valor total ya que lo calcularemos en la vista
            }

            // Agregar todos los pedidos al listado
            lista.addAll(mapaPedidos.values());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return lista;

    }

    //metodo para registrar el pedido desde el formulario
    public boolean registrarPedidoDetalle(PedidoDetalle detalle) {
        boolean registrado = false;
        String sql = "INSERT INTO pedidodetalle (cantidad, precioUnitario, valorPedido, PedidoCabecera_idPedidoCabecera, Productos_idProductos, Unidades_idUnidades) VALUES (?, ?, ?, ?, ?, ?)";

        Conexion con = new Conexion();

        try (
                Connection conn = con.getConn(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, detalle.getCantidad());
            ps.setDouble(2, detalle.getPrecioUnitario());
            ps.setDouble(3, detalle.getValorPedido());
            ps.setInt(4, detalle.getPedidoCabecera_idPedidoCabecera());
            ps.setInt(5, detalle.getProductos_idProductos());
            ps.setInt(6, detalle.getUnidades_idUnidades());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                registrado = true;
            }
        } catch (Exception e) {
            System.out.println("Error al registrar detalle del pedido: " + e.getMessage());
        }

        return registrado;
    }
    //metodo para filtar los pedidos y luego poder mostrarlos en la vista pedidos.jsp//
  public List<PedidoDetalle> listarPedidosFiltrados(String numeroIdentificacion, String fechaPedido) {
    List<PedidoDetalle> lista = new ArrayList<>();

    Conexion miconexion = new Conexion();
    Connection conn = miconexion.getConn();
    
    if ((numeroIdentificacion == null || numeroIdentificacion.trim().isEmpty()) &&
    (fechaPedido == null || fechaPedido.trim().isEmpty())) {
    // No hay filtros: retornar lista vacía
    return new ArrayList<>();
}


    // Consulta base
    StringBuilder sql = new StringBuilder("SELECT pc.idPedidoCabecera AS idCabecera, pc.numeroPedido, pc.fechaPedido, "
            + "p.nombres AS nombrePersona, p.apellidos AS apellidoPersona, p.numeroIdentificacion AS identificacionPersona, "
            + "pr.nombre AS nombreProducto, pr.descripcion AS descripcionProducto, "
            + "u.descripcionUnidades AS descripcionUnidad, "
            + "pd.cantidad, pd.precioUnitario, pd.valorPedido "
            + "FROM pedidodetalle pd "
            + "JOIN pedidocabecera pc ON pd.PedidoCabecera_idPedidoCabecera = pc.idPedidoCabecera "
            + "JOIN persona p ON pc.Persona_idPersona = p.idPersona "
            + "JOIN productos pr ON pd.Productos_idProductos = pr.idProductos "
            + "JOIN unidades u ON pd.Unidades_idUnidades = u.idUnidades "
            + "WHERE 1=1 ");

    // Agregar condiciones si los parámetros no están vacíos
    if (numeroIdentificacion != null && !numeroIdentificacion.trim().isEmpty()) {
        sql.append("AND p.numeroIdentificacion = ? ");
    }
    if (fechaPedido != null && !fechaPedido.trim().isEmpty()) {
        sql.append("AND pc.fechaPedido = ? ");
    }

    sql.append("ORDER BY pc.idPedidoCabecera");

    try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
        int index = 1;
        if (numeroIdentificacion != null && !numeroIdentificacion.trim().isEmpty()) {
            ps.setString(index++, numeroIdentificacion);
        }
        if (fechaPedido != null && !fechaPedido.trim().isEmpty()) {
            ps.setString(index++, fechaPedido);
        }

        try (ResultSet rs = ps.executeQuery()) {
            Map<Integer, PedidoDetalle> mapaPedidos = new HashMap<>();

            while (rs.next()) {
                int idPedidoCabecera = rs.getInt("idCabecera");

                PedidoDetalle pd = mapaPedidos.get(idPedidoCabecera);
                if (pd == null) {
                    pd = new PedidoDetalle();

                    PedidoCabecera cabecera = new PedidoCabecera();
                    cabecera.setIdPedidoCabecera(idPedidoCabecera);
                    cabecera.setNumeroPedido(rs.getInt("numeroPedido"));
                    cabecera.setFechaPedido(rs.getString("fechaPedido"));
                    cabecera.setNombrePersona(rs.getString("nombrePersona"));
                    cabecera.setApellidoPersona(rs.getString("apellidoPersona"));
                    cabecera.setNumeroIdentificacion(rs.getString("identificacionPersona"));
                    pd.setPedidoCabecera(cabecera);

                    pd.setProductos(new ArrayList<>());
                    pd.setUnidades(new ArrayList<>());
                    pd.setCantidades(new ArrayList<>());
                    pd.setPreciosUnitarios(new ArrayList<>());

                    mapaPedidos.put(idPedidoCabecera, pd);
                }

                Productos producto = new Productos();
                producto.setNombre(rs.getString("nombreProducto"));
                producto.setDescripcion(rs.getString("descripcionProducto"));
                pd.getProductos().add(producto);

                Unidades unidad = new Unidades();
                unidad.setDescripcionUnidades(rs.getString("descripcionUnidad"));
                pd.getUnidades().add(unidad);

                pd.getCantidades().add(rs.getDouble("cantidad"));
                pd.getPreciosUnitarios().add(rs.getDouble("precioUnitario"));
            }

            lista.addAll(mapaPedidos.values());
        }

    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    return lista;
}
}
