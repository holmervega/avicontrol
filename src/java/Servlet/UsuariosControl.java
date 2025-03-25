package Servlet;

import Controlador.UsuariosDAO;
import Modelo.Persona;
import Modelo.Roles;
import Modelo.TipoIdentificacion;
import Modelo.Usuarios;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

// Cargar datos en la lista de usuarios registrados
@WebServlet(name = "UsuariosControl", urlPatterns = {"/UsuariosControl"})
public class UsuariosControl extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UsuariosDAO usuariosDAO = new UsuariosDAO();

        // Obtener la lista de usuarios registrados
        List<Usuarios> listaUsuarios = usuariosDAO.listarUsuarios();
        request.setAttribute("listaUsuarios", listaUsuarios);

        // Obtener la lista de tipos de identificación para el select
        List<TipoIdentificacion> listaTipos = usuariosDAO.obtenerTiposIdentificacion();
        request.setAttribute("listaTipos", listaTipos);

        // Obtener la lista de roles para el select
        List<Roles> listaRoles = usuariosDAO.obtenerRoles();
        request.setAttribute("listaRoles", listaRoles);

        request.getRequestDispatcher("usuarios.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("registrarUsuario".equals(action)) {
            registrarUsuario(request, response);
        } else if ("verificarUsuario".equals(action)) { // Nueva acción para obtener usuario por ID
            verificarUsuario(request, response);
        }
    }
//registrar ususario en la base de dats
    private void registrarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UsuariosDAO usuarioDAO = new UsuariosDAO();

        // Capturar datos del formulario
        String nombreUsuario = request.getParameter("nombreUsuario");
        String contrasenaUsuario = request.getParameter("contrasenaUsuario");
        int idTipoIdentificacion = Integer.parseInt(request.getParameter("idTipoIdentificacion"));
        int numeroIdentificacion = Integer.parseInt(request.getParameter("numeroIdentificacion"));
        String nombres = request.getParameter("nombres");
        String apellidos = request.getParameter("apellidos");
        String telefono = request.getParameter("telefono");
        String correo = request.getParameter("correo");
        String direccion = request.getParameter("direccion");
        int idRoles = Integer.parseInt(request.getParameter("idRoles"));

        // Crear objetos de Usuario y Persona
        Usuarios usuario = new Usuarios();
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setContrasenaUsuario(contrasenaUsuario);

        Persona persona = new Persona();
        persona.setNumeroIdentificacion(numeroIdentificacion);
        persona.setNombres(nombres);
        persona.setApellidos(apellidos);
        persona.setTelefono(telefono);
        persona.setCorreo(correo);
        persona.setDireccion(direccion);
        persona.setIdTipoIdentificacion(idTipoIdentificacion);
        persona.setIdRoles(idRoles);

        // Registrar en la base de datos
        boolean registroExitoso = usuarioDAO.registrarUsuario(persona, usuario);

        if (registroExitoso) {
            request.getSession().setAttribute("success", "true");
        } else {
            request.getSession().setAttribute("error", "true");
        }
        response.sendRedirect("UsuariosControl");
    }

    private void verificarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idPersona = Integer.parseInt(request.getParameter("idPersona"));
        UsuariosDAO usuariosDAO = new UsuariosDAO();
        Usuarios usuario = usuariosDAO.obtenerUsuarioPorId(idPersona);

        if (usuario != null) {
            request.setAttribute("usuario", usuario);
            request.getRequestDispatcher("usuarios.jsp").forward(request, response);
        } else {
            response.sendRedirect("usuarios.jsp?error=UsuarioNoEncontrado");
        }
    }
}
