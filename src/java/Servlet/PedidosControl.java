package Servlet;

import Controlador.PedidoDetalleDAO;
import Controlador.PersonaDAO;
import Controlador.ProductosDAO;
import Controlador.UnidadesDAO;
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
        String action = request.getParameter("action");

        if ("buscarCliente".equals(action)) {
            buscarClienteYcargarProductos(request, response);
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

        // Pasar la lista de productos al request
        request.setAttribute("productos", productos);
        
        // Pasar la lista de unidades al request
        request.setAttribute("unidades", unidades);
        

        // Redirigir a la página que contiene el formulario
        request.getRequestDispatcher("registrarPedidos.jsp").forward(request, response);
    }
}