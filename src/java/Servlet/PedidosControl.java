package Servlet;

import Controlador.PedidoCabeceraDAO;
import Controlador.PedidoDetalleDAO;
import Controlador.PersonaDAO;
import Controlador.ProductosDAO;
import Controlador.UnidadesDAO;
import Modelo.PedidoCabecera;
import Modelo.PedidoDetalle;
import Modelo.Persona;
import Modelo.Productos;
import Modelo.Unidades;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "/PedidosControl", urlPatterns = {"/PedidosControl"})
public class PedidosControl extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("📥 Entrando en PedidoControl - doGet");

        String action = request.getParameter("action");
        System.out.println("🔍 Acción recibida: " + action);

        if (action == null) {
            System.out.println("⚠ Acción no especificada, redirigiendo a home.jsp");
            response.sendRedirect("home.jsp");
            return;
        }

        switch (action) {
            case "listarPedidos":
                listarPedidos(request, response);
                break;
            default:
                System.out.println("❓ Acción desconocida: " + action);
                response.sendRedirect("home.jsp");
        }
    }

    private void listarPedidos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("📦 Listando pedidos...");

        // Crear la conexión dentro del método y obtener los pedidos
        PedidoDetalleDAO pedidoDAO = new PedidoDetalleDAO();
        List<PedidoDetalle> listaPedidos = pedidoDAO.listarPedidos();

        // Verificamos que la lista de pedidos no esté vacía
        if (listaPedidos != null && !listaPedidos.isEmpty()) {
            // Si hay pedidos, los pasamos como atributo al JSP
            request.setAttribute("listaPedidos", listaPedidos);
        } else {
            // Si no hay pedidos, se puede poner un mensaje en la vista
            request.setAttribute("mensaje", "No hay pedidos registrados.");
        }

        // Redirigir a la vista que mostrará los pedidos
        System.out.println("Cargando vista pedidos.jsp");
        request.getRequestDispatcher("pedidos.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("📥 Entrando en PedidoControl - doPost");
        String action = request.getParameter("action");
        System.out.println("🔍 Acción recibida: " + action);

        if ("buscarCliente".equals(action)) {
            buscarClienteYcargarProductos(request, response);
        }

        if ("registrarPedido".equals(action)) {
            registrarPedido(request, response);
        }

    }

    private void buscarClienteYcargarProductos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener el número de identificación ingresado
        int numeroIdentificacion = Integer.parseInt(request.getParameter("numeroIdentificacion"));

        // Llamar al DAO para obtener la persona
        PersonaDAO personaDAO = new PersonaDAO();
        Persona persona = personaDAO.obtenerPersonaPorNumeroIdentificacion(numeroIdentificacion);

        // Verificar si se encontró la persona
        if (persona != null) {
            // Establecer los datos en el request para pasarlos a la JSP
            request.setAttribute("persona", persona);
        } else {
            // Si no se encuentra la persona, mostrar un mensaje
            request.setAttribute("mensaje", "No se encontró cliente asociado a ese numero de identificacion.");
        }

        // Cargar los productos desde la base de datos
        ProductosDAO productosDAO = new ProductosDAO();
        List<Productos> productos = productosDAO.listarProductos();

        //cargar las unidades desde la base de datos 
        UnidadesDAO unidadesDAO = new UnidadesDAO();
        List<Unidades> unidades = unidadesDAO.obtenerUnidades();

        //cargar el numero siguiente del pedido
        PedidoCabeceraDAO pedidoCabeceraDAO = new PedidoCabeceraDAO();
        int siguienteNumeroPedido = pedidoCabeceraDAO.obtenerSiguienteNumeroPedido();

        // Pasar la lista de productos al request
        request.setAttribute("productos", productos);

        // Pasar la lista de unidades al request
        request.setAttribute("unidades", unidades);

        //Pasar el numero siguiente al request
        request.setAttribute("numeroPedido", siguienteNumeroPedido);

        // Redirigir a la página que contiene el formulario
        request.getRequestDispatcher("registrarPedidos.jsp").forward(request, response);
    }

   private void registrarPedido(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    try {
        int numeroPedido = Integer.parseInt(request.getParameter("numeroPedido"));
        String fechaPedido = request.getParameter("fechaPedido");
        int idPersona = Integer.parseInt(request.getParameter("idPersona"));

        PedidoCabecera cabecera = new PedidoCabecera();
        cabecera.setNumeroPedido(numeroPedido);
        cabecera.setFechaPedido(fechaPedido);
        cabecera.setPersona_idPersona(idPersona);

        PedidoCabeceraDAO daoCabecera = new PedidoCabeceraDAO();
        boolean registrada = daoCabecera.registrarPedidoCabecera(cabecera);
        System.out.println("✅ Cabecera registrada con ID: " + cabecera.getIdPedidoCabecera());

        if (registrada) {
            int idCabecera = cabecera.getIdPedidoCabecera();

            String[] cantidades = request.getParameterValues("cantidad[]");
            String[] precios = request.getParameterValues("precioUnitario[]");
            String[] idsProducto = request.getParameterValues("idProducto[]");
            String[] idsUnidad = request.getParameterValues("idUnidad[]");

            PedidoDetalleDAO daoDetalle = new PedidoDetalleDAO();

            for (int i = 0; i < cantidades.length; i++) {
                double cantidad = Double.parseDouble(cantidades[i]);
                double precio = Double.parseDouble(precios[i]);
                double valor = cantidad * precio;
                int idProducto = Integer.parseInt(idsProducto[i]);
                int idUnidad = Integer.parseInt(idsUnidad[i]);

                PedidoDetalle detalle = new PedidoDetalle();
                detalle.setCantidad(cantidad);
                detalle.setPrecioUnitario(precio);
                detalle.setValorPedido(valor);
                detalle.setPedidoCabecera_idPedidoCabecera(idCabecera);
                detalle.setProductos_idProductos(idProducto);
                detalle.setUnidades_idUnidades(idUnidad);

                boolean detalleRegistrado = daoDetalle.registrarPedidoDetalle(detalle);
                System.out.println("📋 Detalle [" + i + "] registrado: " + detalleRegistrado);
            }

            request.setAttribute("mensajeExito", "Pedido registrado correctamente.");
        } else {
            request.setAttribute("mensajeError", "No se pudo registrar la cabecera del pedido.");
        }

    } catch (Exception e) {
        System.out.println("❌ Excepción al registrar el pedido: " + e.getMessage());
        request.setAttribute("mensajeError", "Error al registrar el pedido: " + e.getMessage());
    }

    response.sendRedirect("PedidosControl?action=listarPedidos");
}
}