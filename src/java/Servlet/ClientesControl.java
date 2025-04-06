package Servlet;

import Controlador.PersonaDAO;
import Controlador.RolesDAO;
import Controlador.TipoIdentificacionDAO;
import Modelo.Persona;
import Modelo.Roles;
import Modelo.TipoIdentificacion;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ClientesControl", urlPatterns = {"/ClientesControl"})
public class ClientesControl extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("📢 Entrando en doGet - ClientesControl");

        String action = request.getParameter("action");
        System.out.println("📢 Acción recibida en doGet: " + action);

        if (action == null) {
            System.out.println("⚠ No se recibió acción, redirigiendo a home.jsp...");
            response.sendRedirect("home.jsp");
            return;
        }

        PersonaDAO personaDAO = new PersonaDAO();
        RolesDAO rolesDAO = new RolesDAO();
        TipoIdentificacionDAO tipoIdentificacionDAO = new TipoIdentificacionDAO();

        switch (action) {
            case "listarClientes":
                List<Persona> listaClientes = personaDAO.listarClientes();
                request.setAttribute("clientes", listaClientes);
                request.getRequestDispatcher("Clientes.jsp").forward(request, response);
                break;

            case "nuevoCliente":
                List<Roles> listaRoles = rolesDAO.obtenerRolesBD();
                List<TipoIdentificacion> listaTipos = tipoIdentificacionDAO.obtenerTipoIdentificacionBD();

                request.setAttribute("listaRoles", listaRoles);
                request.setAttribute("listaTiposIdentificacion", listaTipos);
                request.getRequestDispatcher("registroCliente.jsp").forward(request, response);
                break;

            // Puedes agregar más casos aquí como editarCliente, eliminarCliente, etc.

            default:
                System.out.println("❌ Acción no reconocida, redirigiendo a home.jsp");
                response.sendRedirect("home.jsp");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("📢 Entrando en doPost - ClientesControl");

        String action = request.getParameter("action");
        System.out.println("📢 Acción recibida en doPost: " + action);

        if (action == null) {
            response.sendRedirect("home.jsp");
            return;
        }

        switch (action) {
            case "registrarCliente":
                // Aquí puedes implementar la lógica para guardar un cliente
                // usando PersonaDAO y redireccionar de vuelta a mostrarClientes
                break;

            case "actualizarCliente":
                // Aquí puedes agregar la lógica para actualizar
                break;

            default:
                System.out.println("❌ Acción POST no reconocida, redirigiendo a home.jsp");
                response.sendRedirect("home.jsp");
                break;
        }
    }
}
