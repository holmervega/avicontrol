package Servlet;

import Controlador.PersonaDAO;
import Controlador.RolesDAO;
import Controlador.TipoIdentificacionDAO;
import Controlador.UsuariosDAO;
import Modelo.InformacionUsuariosDTO;
import Modelo.Persona;
import Modelo.RegistroUsuariosDTO;
import Modelo.Roles;
import Modelo.TipoIdentificacion;
import Modelo.Usuarios;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "UsuariosControl", urlPatterns = {"/UsuariosControl"})
public class UsuariosControl extends HttpServlet {

    private PersonaDAO personaDAO = new PersonaDAO();
    private RolesDAO rolesDAO = new RolesDAO();
    private UsuariosDAO usuariosDAO = new UsuariosDAO();
    private TipoIdentificacionDAO tipoIdentificacionDAO = new TipoIdentificacionDAO();
    
// cargar usuarios con todos los datos en la vista usuarios
   @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String action = request.getParameter("action");
    
    if (action != null && action.equals("editar")) {
        int idPersona = Integer.parseInt(request.getParameter("idPersona")); // Obtener el idPersona desde el parámetro

        // Obtener los datos de la persona
        Persona persona = personaDAO.obtenerPersonaPorId(idPersona);
        
        // Obtener el nombre de usuario y contraseña asociados
        Usuarios usuario = usuariosDAO.obtenerUsuarioPorId(persona.getUsuarios_idUsuarios());

        // Cargar roles y tipos de identificación
        List<TipoIdentificacion> tiposIdentificacion = tipoIdentificacionDAO.obtenerTipoIdentificacionBD();
        List<Roles> roles = rolesDAO.obtenerRolesdeBD();

        // Pasar los datos a la vista
        request.setAttribute("persona", persona);
        request.setAttribute("usuario", usuario);
        request.setAttribute("tipoIdentificacion", tiposIdentificacion);
        request.setAttribute("roles", roles);
        
        // Redirigir a la vista de edición (editar.jsp)
        RequestDispatcher dispatcher = request.getRequestDispatcher("editar.jsp");
        dispatcher.forward(request, response);
    } else {
        // Cargar la lista de usuarios (como ya lo haces en el método doGet actual)
        List<Persona> listaPersonas = personaDAO.listarPersonas();
        List<InformacionUsuariosDTO> listaInformacionUsuarios = new ArrayList<>();

        for (Persona p : listaPersonas) {
            String descripcionRol = rolesDAO.obtenerDescripcionporid(p.getRoles_idRoles());
            Usuarios usuario = usuariosDAO.obtenerUsuarioPorId(p.getUsuarios_idUsuarios());
            String nombreUsuario = (usuario != null) ? usuario.getNombreUsuario() : "N/A";
            String contrasenaUsuario = (usuario != null) ? usuario.getContrasenaUsuario() : "N/A";
            String descripcionTipoIdentificacion = tipoIdentificacionDAO.obtenerDescripcionTipoIdentificacionpoId(p.getTipoIdentificacion_idTipoIdentificacion());

            InformacionUsuariosDTO infoUsuario = new InformacionUsuariosDTO(
                    p.getNumeroIdentificacion(),
                    p.getNombres(),
                    p.getApellidos(),
                    p.getTelefono(),
                    p.getCorreo(),
                    p.getDireccion(),
                    descripcionRol,
                    nombreUsuario,
                    contrasenaUsuario,
                    descripcionTipoIdentificacion
            );

            listaInformacionUsuarios.add(infoUsuario);
        }

        // Cargar roles y tipos de identificación para el formulario dentro del modal
        List<TipoIdentificacion> tiposIdentificacion = tipoIdentificacionDAO.obtenerTipoIdentificacionBD();
        List<Roles> roles = rolesDAO.obtenerRolesdeBD();

        request.setAttribute("informacionUsuarios", listaInformacionUsuarios);
        request.setAttribute("tipoIdentificacion", tiposIdentificacion);
        request.setAttribute("roles", roles);
        RequestDispatcher dispatcher = request.getRequestDispatcher("usuarios.jsp");
        dispatcher.forward(request, response);
    }
}

    // Registrar una nueva persona con usuario
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");

        // Capturar el valor recibido antes de convertirlo
        String idTipoIdentificacionStr = request.getParameter("idTipoIdentificacion");

        if (idTipoIdentificacionStr == null || idTipoIdentificacionStr.isEmpty()) {
            System.out.println("Error: idTipoIdentificacion es null o vacío");
            response.sendRedirect("error.jsp"); // Puedes redirigir a una página de error si lo deseas
            return;
        }
        int idTipoIdentificacion = Integer.parseInt(request.getParameter("idTipoIdentificacion"));
        int numeroIdentificacion = Integer.parseInt(request.getParameter("numeroIdentificacion"));
        String nombres = request.getParameter("nombres");
        String apellidos = request.getParameter("apellidos");
        String telefono = request.getParameter("telefono");
        String correo = request.getParameter("correo");
        String direccion = request.getParameter("direccion");
        int idRol = Integer.parseInt(request.getParameter("idRol"));
        String nombreUsuario = request.getParameter("nombreUsuario");
        String contrasenaUsuario = request.getParameter("contrasenaUsuario");

        // Crear objeto DTO con los datos del usuario
        RegistroUsuariosDTO registroDTO = new RegistroUsuariosDTO(idTipoIdentificacion, numeroIdentificacion, nombres, apellidos, telefono, correo, direccion, idRol, nombreUsuario, contrasenaUsuario);

// Crear usuario y obtener ID generado
        int idUsuario = usuariosDAO.crearUsuario(registroDTO);

        if (idUsuario > 0) {
            // Asignar el idUsuario generado al DTO antes de registrar la persona
            registroDTO.setIdUsuarios(idUsuario);
            personaDAO.registrarPersona(registroDTO, idUsuario);

        } else {
            System.out.println("Error al registrar el usuario.");
        }

        response.sendRedirect("UsuariosControl");

    }

}
