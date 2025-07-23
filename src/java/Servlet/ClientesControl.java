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
                String estado = request.getParameter("estado");

                Boolean filtroEstado = null;

                if (estado == null) {
                    // Valor por defecto: mostrar solo activos
                    filtroEstado = true;
                    estado = "activos"; // Para que el <select> sepa qué opción seleccionar
                } else if (!estado.isEmpty()) {
                    if ("activos".equals(estado)) {
                        filtroEstado = true;
                    } else if ("inactivos".equals(estado)) {
                        filtroEstado = false;
                    }
                }

                List<Persona> listaClientes = personaDAO.listarClientes(filtroEstado);

                request.setAttribute("clientes", listaClientes);
                request.setAttribute("estadoSeleccionado", estado); // lo usamos en el JSP
                request.getRequestDispatcher("clientes.jsp").forward(request, response);
                break;

            case "nuevoCliente":
                List<Roles> listaRoles = rolesDAO.obtenerRolesBD();
                List<TipoIdentificacion> listaTipos = tipoIdentificacionDAO.obtenerTipoIdentificacionBD();

                request.setAttribute("listaRoles", listaRoles);
                request.setAttribute("listaTiposIdentificacion", listaTipos);
                request.getRequestDispatcher("registrarClientes.jsp").forward(request, response);
                break;

            case "editarCliente":
    try {
                int idEditar = Integer.parseInt(request.getParameter("id"));

                Persona clienteEditar = personaDAO.obtenerClientePorId(idEditar);

                if (clienteEditar == null) {
                    System.out.println("⚠ No se encontró el cliente con ID: " + idEditar);
                    response.sendRedirect("ClientesControl?action=listarClientes&error=ClienteNoEncontrado");
                    return;
                }

                // Obtener lista de tipos de identificación
                List<TipoIdentificacion> tiposIdentificacion = tipoIdentificacionDAO.obtenerTipoIdentificacionBD();

                // Obtener descripción (opcional, si quieres mostrarla)
                String descripcionTipoIdentificacion = tipoIdentificacionDAO.obtenerDescripcionPorIdPersona(idEditar);

                System.out.println("✅ Cliente encontrado: " + clienteEditar.getNombres() + " " + clienteEditar.getApellidos());

                // Pasar datos al JSP
                request.setAttribute("persona", clienteEditar);
                request.setAttribute("tiposIdentificacion", tiposIdentificacion);
                request.setAttribute("descripcionTipoIdentificacion", descripcionTipoIdentificacion);

                request.getRequestDispatcher("editarClientes.jsp").forward(request, response);

            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("ClientesControl?action=listarClientes&error=ErrorEnEdicion");
            }
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

        PersonaDAO personaDAO = new PersonaDAO();

        switch (action) {
            case "registrarCliente":
                // Obtener datos del formulario
                int numeroIdentificacion = Integer.parseInt(request.getParameter("numeroIdentificacion"));
                String nombres = request.getParameter("nombres");
                String apellidos = request.getParameter("apellidos");
                String telefono = request.getParameter("telefono");
                String correo = request.getParameter("correo");
                String direccion = request.getParameter("direccion");
                int tipoIdentificacion = Integer.parseInt(request.getParameter("tipoIdentificacion"));

                // Crear objeto Persona
                Persona nuevoCliente = new Persona();
                nuevoCliente.setNumeroIdentificacion(numeroIdentificacion);
                nuevoCliente.setNombres(nombres);
                nuevoCliente.setApellidos(apellidos);
                nuevoCliente.setTelefono(telefono);
                nuevoCliente.setCorreo(correo);
                nuevoCliente.setDireccion(direccion);
                nuevoCliente.setTipoIdentificacion_idTipoIdentificacion(tipoIdentificacion);
                nuevoCliente.setRoles_idRoles(9); // Cliente

                // Registrar en la base de datos
                boolean registrado = personaDAO.registrarClientes(nuevoCliente);

                if (registrado) {
                    // Redirigir a la vista que lista los clientes
                    response.sendRedirect("ClientesControl?action=listarClientes");
                } else {
                    // Redirigir a un error o mostrar un mensaje
                    response.sendRedirect("registroCliente.jsp?error=1");
                }
                break;

            case "eliminarCliente":
                int idEliminar = Integer.parseInt(request.getParameter("id"));
                PersonaDAO daoEliminar = new PersonaDAO();
                boolean eliminado = daoEliminar.deshabilitarCliente(idEliminar);

                if (eliminado) {
                    System.out.println("✅ Cliente deshabilitado correctamente.");
                } else {
                    System.out.println("❌ Error al deshabilitar cliente.");
                }

                response.sendRedirect("ClientesControl?action=listarClientes");
                break;

            default:
                System.out.println("❌ Acción POST no reconocida, redirigiendo a home.jsp");
                response.sendRedirect("home.jsp");
                break;

            case "activarCliente":

                int idActivar = Integer.parseInt(request.getParameter("id"));
                boolean activado = personaDAO.activarCliente(idActivar);

                if (activado) {
                    response.sendRedirect("ClientesControl?action=listarClientes&estado=inactivos");
                } else {
                    response.sendRedirect("ClientesControl?action=listarClientes&estado=inactivos&error=1");
                }
                break;

            case "actualizarCliente": {
                try {
                    Persona persona = new Persona();
                    persona.setIdPersona(Integer.parseInt(request.getParameter("idPersona")));
                    persona.setTipoIdentificacion_idTipoIdentificacion(Integer.parseInt(request.getParameter("tipoIdentificacion")));
                    persona.setNumeroIdentificacion(Integer.parseInt(request.getParameter("numeroIdentificacion")));
                    persona.setNombres(request.getParameter("nombres"));
                    persona.setApellidos(request.getParameter("apellidos"));
                    persona.setTelefono(request.getParameter("telefono"));
                    persona.setCorreo(request.getParameter("correo"));
                    persona.setDireccion(request.getParameter("direccion"));

                    boolean actualizado = personaDAO.actualizarCliente(persona);

                    if (actualizado) {
                        // Redirige correctamente como GET
                        response.sendRedirect("ClientesControl?action=listarClientes");
                    } else {
                        request.setAttribute("mensajeError", "Error al actualizar el cliente.");
                        request.getRequestDispatcher("editarClientes.jsp").forward(request, response);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("mensajeError", "Error inesperado al actualizar el cliente.");
                    request.getRequestDispatcher("editarClientes.jsp").forward(request, response);
                }
                break;
            }

        }

    }

}
