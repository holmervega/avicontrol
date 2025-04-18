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
import java.util.List;

public class PedidoDetalleDAO {



// mostrar los pedios que hay en la base de datos
    public List<PedidoDetalle> listarPedidos() {
        List<PedidoDetalle> lista = new ArrayList<>();

        // Crear la conexión dentro del método
        Conexion miconexion = new Conexion();
        Connection conn = miconexion.getConn();

        String sql = "SELECT pd.*, pc.idPedidoCabecera AS idCabecera, pc.numeroPedido, pc.fechaPedido, "
                + "p.nombres AS nombrePersona, p.apellidos AS apellidoPersona,p.numeroIdentificacion AS identificacionPersona, "
                + "pr.nombre AS nombreProducto, pr.descripcion AS descripcionProducto, "
                + "u.descripcionUnidades AS descripcionUnidad "
                + "FROM pedidodetalle pd "
                + "JOIN pedidocabecera pc ON pd.PedidoCabecera_idPedidoCabecera = pc.idPedidoCabecera "
                + "JOIN persona p ON pc.Persona_idPersona = p.idPersona "
                + "JOIN productos pr ON pd.Productos_idProductos = pr.idProductos "
                + "JOIN unidades u ON pd.Unidades_idUnidades = u.idUnidades";

        // Usamos try-with-resources para asegurarnos de que la conexión y los recursos se cierren adecuadamente
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                PedidoDetalle pd = new PedidoDetalle();
                // PedidoDetalle
                pd.setIdPedidoDetalle(rs.getInt("idPedidoDetalle"));
                pd.setCantidad(rs.getDouble("cantidad"));
                pd.setPrecioUnitario(rs.getDouble("precioUnitario"));
                pd.setValorPedido(rs.getDouble("valorPedido"));
                pd.setPedidoCabecera_idPedidoCabecera(rs.getInt("PedidoCabecera_idPedidoCabecera"));
                pd.setProductos_idProductos(rs.getInt("Productos_idProductos"));
                pd.setUnidades_idUnidades(rs.getInt("Unidades_idUnidades"));

                // PedidoCabecera
                PedidoCabecera cabecera = new PedidoCabecera();
                cabecera.setIdPedidoCabecera(rs.getInt("idCabecera"));
                cabecera.setNumeroPedido(rs.getInt("numeroPedido"));
                cabecera.setFechaPedido(rs.getString("fechaPedido"));
                pd.setPedidoCabecera(cabecera);

                // Productos
                Productos producto = new Productos();
                producto.setIdProductos(pd.getProductos_idProductos()); // del mismo detalle
                producto.setNombre(rs.getString("nombreProducto"));
                producto.setDescripcion(rs.getString("descripcionProducto"));
                pd.setProducto(producto); // Necesitarías agregar este campo en tu clase PedidoDetalle

                // Unidades
                Unidades unidad = new Unidades();
                unidad.setIdUnidades(pd.getUnidades_idUnidades()); // del mismo detalle
                unidad.setDescripcionUnidades(rs.getString("descripcionUnidad"));
                pd.setUnidad(unidad); // Necesitarías agregar este campo en tu clase PedidoDetalle

                // datos de Persona
                cabecera.setNombrePersona(rs.getString("nombrePersona"));
                cabecera.setApellidoPersona(rs.getString("apellidoPersona"));
                cabecera.setNumeroIdentificacion(rs.getString("identificacionPersona"));

                lista.add(pd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close(); // Asegúrate de cerrar la conexión al final
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return lista;
    }
}
