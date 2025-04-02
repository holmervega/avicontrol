package Servlet;

import Controlador.PersonaDAO;
import Controlador.RolesDAO;
import Controlador.UsuariosDAO;
import Controlador.TipoIdentificacionDAO;
import Modelo.Persona;
import Modelo.Roles;
import Modelo.Usuarios;
import Modelo.TipoIdentificacion;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UsuariosControl", urlPatterns = {"/UsuariosControl"})
public class UsuariosControl extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       System.out.println("📢 Entrando en doGet");
        // Obtener la acción desde el parámetro
        String action = request.getParameter("action");
        // 🔹 Verifica qué valor está llegando
      System.out.println("📢 Verificando acción en doGet: " + action);

        if (action == null) {
            System.out.println("⚠ No se recibió acción, redirigiendo a home.jsp...");
            response.sendRedirect("home.jsp");
            return;
        }
        
         UsuariosDAO usuarioDAO = new UsuariosDAO();
        

        // Obtener listas de la base de datos
        List<Usuarios> listaUsuarios;
        List<Roles> listaRoles = new RolesDAO().obtenerRolesBD();
        TipoIdentificacionDAO tipoIdentificacionDAO = new TipoIdentificacionDAO();
        List<TipoIdentificacion> tiposIdentificacion = tipoIdentificacionDAO.obtenerTipoIdentificacionBD();
        System.out.println("Acción recibida: " + action);

        switch (action) {
            case "mostrarUsuarios":
                listaUsuarios = new UsuariosDAO().listarUsuarios();
                request.setAttribute("usuarios", listaUsuarios);
                request.getRequestDispatcher("usuarios.jsp").forward(request, response);
                break;
                
                
            case "eliminarUsuario":
                String idPersonaStrEliminar = request.getParameter("idPersona");
                System.out.println("🗑️ ID de Persona recibido: " + idPersonaStrEliminar);

                if (idPersonaStrEliminar == null || idPersonaStrEliminar.trim().isEmpty()) {
                    System.out.println("🚨 Error: ID de Persona no recibido.");
                    response.sendRedirect("usuarios.jsp?error=ID inválido");
                    return;
                }

                int idPersonaEliminar = Integer.parseInt(idPersonaStrEliminar);
              
                boolean eliminado = usuarioDAO.eliminarUsuarioPorIdPersona(idPersonaEliminar);

                if (eliminado) {
                    System.out.println("✅ Usuario con ID " + idPersonaEliminar + " eliminado correctamente.");
                    response.sendRedirect("UsuariosControl?action=mostrarUsuarios&mensaje=Usuario eliminado");
                } else {
                    System.out.println("🚨 Error al eliminar el usuario con ID Persona " + idPersonaEliminar);
                    response.sendRedirect("UsuariosControl?action=mostrarUsuarios&error=No se pudo eliminar");
                }
                return;

            case "registrarUsuario":
                request.setAttribute("tiposIdentificacion", tiposIdentificacion);
                request.setAttribute("roles", listaRoles);
                request.getRequestDispatcher("RegistrarUsuarios.jsp").forward(request, response);
                break;

            case "editarUsuario": // 🔹 ¡Lo volvemos a agregar aquí!
                String idPersonaStr = request.getParameter("idPersona");

                if (idPersonaStr == null || idPersonaStr.isEmpty()) {
                       System.out.println("⚠ Error: idPersona no fue recibido.");
                    response.sendRedirect("usuarios.jsp");
                    return;
                }
                System.out.println("✅ ID recibido en el Servlet: " + idPersonaStr);

                try {
                    int idPersona = Integer.parseInt(idPersonaStr);

                    // Obtener los datos de la persona con el ID
                    PersonaDAO personaDAO = new PersonaDAO();
                    Persona persona = personaDAO.obtenerPersonaPorId(idPersona);

                    if (persona == null) {
                         System.out.println("⚠ No se encontró la persona con ID: " + idPersona);
                        request.setAttribute("mensaje", "No se encontró la persona.");
                        request.getRequestDispatcher("usuarios.jsp").forward(request, response);
                        return;
                    }
                    // 📌 Obtener los datos del usuario usando el método en UsuariosDAO
                    
                    Usuarios usuario = usuarioDAO.obtenerUsuarioPorIdPersona(idPersona);

                    if (usuario == null) {
                        System.out.println("⚠ No se encontró el usuario asociado a la persona con ID: " + idPersona);
                        request.setAttribute("mensaje", "No se encontró el usuario.");
                        request.getRequestDispatcher("usuarios.jsp").forward(request, response);
                        return;
                    }
                    
                    // 📌 Obtener la descripción del tipo de identificación
                    String descripcionTipoIdentificacion = tipoIdentificacionDAO.obtenerDescripcionPorIdPersona(idPersona);
                    // Obtener la descripción del rol
                    RolesDAO rolesDAO = new RolesDAO();
                    String descripcionRol = rolesDAO.obtenerDescripcionRolPorIdPersona(idPersona);

   
                    System.out.println("✅ Persona encontrada: " + persona.getNombres() + " " + persona.getApellidos());
                    System.out.println("✅ Usuario encontrado: " + usuario.getNombreUsuario());

                    // 🔹 Mensajes adicionales para depuración
                    System.out.println("🔹 Enviando datos a editarusuario.jsp...");
                    System.out.println("   - Persona: " + persona.getNombres() + " " + persona.getApellidos());
                    System.out.println("   - Roles: " + (listaRoles != null ? listaRoles.size() : "No cargados"));
                    System.out.println("   - Tipos de Identificación: " + (tiposIdentificacion != null ? tiposIdentificacion.size() : "No cargados"));

                    request.setAttribute("persona", persona);
                    request.setAttribute("roles", listaRoles);
                    request.setAttribute("descripcionTipoIdentificacion", descripcionTipoIdentificacion);
                    request.setAttribute("usuario", usuario);
                    request.setAttribute("descripcionRol", descripcionRol);
                    request.setAttribute("tiposIdentificacion", tiposIdentificacion);



                    request.getRequestDispatcher("editarusuario.jsp").forward(request, response);
                } catch (NumberFormatException e) {
                    System.out.println("⚠ Error: idPersona no es un número válido.");
                    
                    response.sendRedirect("usuarios.jsp");
                }
                break;

            default:
                response.sendRedirect("home.jsp");
                break;
        }
    }
   
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       System.out.println("📢 Entrando en doPost");
        String action = request.getParameter("action");
        System.out.println("🔍 Acción recibida en doPost: " + action);

        if ("eliminarUsuario".equals(action)) {
            String idPersonaStr = request.getParameter("idPersona");
            System.out.println("🗑️ ID de Persona recibido: " + idPersonaStr);

            if (idPersonaStr == null || idPersonaStr.trim().isEmpty()) {
                System.out.println("🚨 Error: ID de Persona no recibido.");
                response.sendRedirect("usuarios.jsp?error=ID inválido");
                return;
            }

            int idPersona = Integer.parseInt(idPersonaStr);
            UsuariosDAO usuarioDAO = new UsuariosDAO();
            boolean eliminado = usuarioDAO.eliminarUsuarioPorIdPersona(idPersona);

            if (eliminado) {
                System.out.println("✅ Usuario con ID " + idPersona + " eliminado correctamente.");
                response.sendRedirect("UsuariosControl?action=mostrarUsuarios&mensaje=Usuario eliminado");
            } else {
                System.out.println("🚨 Error al eliminar el usuario con ID Persona " + idPersona);
                response.sendRedirect("UsuariosControl?action=mostrarUsuarios&error=No se pudo eliminar");
            }
            return;
        }

        System.out.println("⚠ Acción no reconocida.");

        if ("actualizarUsuario".equals(action)) {
            // 📌 Capturar datos del formulario
            String idPersonaStr = request.getParameter("idPersona");
            String idUsuarioStr = request.getParameter("idUsuario");
            String nombreUsuario = request.getParameter("usuario");
            String contrasenaUsuario = request.getParameter("contrasena");
            String numeroIdentificacionStr = request.getParameter("numeroIdentificacion");
            String nombres = request.getParameter("nombres");
            String apellidos = request.getParameter("apellidos");
            String telefono = request.getParameter("telefono");
            String correo = request.getParameter("correo");
            String direccion = request.getParameter("direccion");
            String tipoIdentificacionStr = request.getParameter("tipoIdentificacion");
            String rolStr = request.getParameter("rol");

            // 📌 Validar que no haya valores nulos o vacíos
            if (idPersonaStr == null || idUsuarioStr == null || numeroIdentificacionStr == null ||
                tipoIdentificacionStr == null || rolStr == null || nombreUsuario == null || 
                contrasenaUsuario == null || nombres == null || apellidos == null || 
                telefono == null || correo == null || direccion == null ||
                idPersonaStr.isEmpty() || idUsuarioStr.isEmpty() || numeroIdentificacionStr.isEmpty() ||
                tipoIdentificacionStr.isEmpty() || rolStr.isEmpty() || nombreUsuario.isEmpty() ||
                contrasenaUsuario.isEmpty()) {

                request.setAttribute("mensaje", "⚠ Error: Todos los campos son obligatorios.");
                request.getRequestDispatcher("usuarios.jsp").forward(request, response);
                return;
            }

            // 📌 Convertir valores a enteros
            int idPersona = Integer.parseInt(idPersonaStr);
            int idUsuario = Integer.parseInt(idUsuarioStr);
            int numeroIdentificacion = Integer.parseInt(numeroIdentificacionStr);
            int tipoIdentificacion = Integer.parseInt(tipoIdentificacionStr);
            int rol = Integer.parseInt(rolStr);

            // 📌 Crear objeto `Persona`
            Persona persona = new Persona();
            persona.setIdPersona(idPersona);
            persona.setNumeroIdentificacion(numeroIdentificacion);
            persona.setNombres(nombres);
            persona.setApellidos(apellidos);
            persona.setTelefono(telefono);
            persona.setCorreo(correo);
            persona.setDireccion(direccion);
            System.out.println("Valor de tipoIdentificacion antes de asignar: " + tipoIdentificacion);

            persona.setTipoIdentificacion(new TipoIdentificacion(tipoIdentificacion, ""));
            System.out.println("TipoIdentificacion en persona: " + persona.getTipoIdentificacion());

            persona.setRoles(new Roles(rol, ""));

            // 📌 Crear objeto `Usuarios`
            Usuarios usuario = new Usuarios();
            usuario.setIdUsuarios(idUsuario);
            usuario.setNombreUsuario(nombreUsuario);
            usuario.setContrasenaUsuario(contrasenaUsuario);

            // 📌 Llamar al método de actualización
            UsuariosDAO usuarioDAO = new UsuariosDAO();
            boolean actualizado = usuarioDAO.actualizarUsuario(persona, usuario);

            
            if (actualizado) {
                request.getSession().setAttribute("mensaje", "✅ Usuario modificado correctamente.");
            } else {
                request.getSession().setAttribute("mensaje", "❌ Error al modificar el usuario.");
            }
            response.sendRedirect("UsuariosControl?action=mostrarUsuarios");

        } else {
            // Aquí mantienes la lógica para registrar un usuario si no es actualización
            String nombreUsuario = request.getParameter("usuario");
            String contrasenaUsuario = request.getParameter("contrasena");
            String numeroIdentificacionStr = request.getParameter("numeroIdentificacion");
            String nombres = request.getParameter("nombres");
            String apellidos = request.getParameter("apellidos");
            String telefono = request.getParameter("telefono");
            String correo = request.getParameter("correo");
            String direccion = request.getParameter("direccion");
            String tipoIdentificacionStr = request.getParameter("tipoIdentificacion");
            String rolStr = request.getParameter("rol");

            if (numeroIdentificacionStr == null || numeroIdentificacionStr.isEmpty()
                    || tipoIdentificacionStr == null || tipoIdentificacionStr.isEmpty()
                    || rolStr == null || rolStr.isEmpty()
                    || nombreUsuario == null || nombreUsuario.isEmpty()
                    || contrasenaUsuario == null || contrasenaUsuario.isEmpty()) {
                request.setAttribute("mensaje", "Error: Verifique que todos los campos estén llenos.");
                request.getRequestDispatcher("usuarios.jsp").forward(request, response);
                return;
            }

            int numeroIdentificacion = Integer.parseInt(numeroIdentificacionStr);
            int tipoIdentificacion = Integer.parseInt(tipoIdentificacionStr);
            int rol = Integer.parseInt(rolStr);

            Persona persona = new Persona();
            persona.setNumeroIdentificacion(numeroIdentificacion);
            persona.setNombres(nombres);
            persona.setApellidos(apellidos);
            persona.setTelefono(telefono);
            persona.setCorreo(correo);
            persona.setDireccion(direccion);
           persona.setTipoIdentificacion_idTipoIdentificacion(tipoIdentificacion);
             persona.setRoles_idRoles(rol);

            Usuarios usuario = new Usuarios();
            usuario.setNombreUsuario(nombreUsuario);
            usuario.setContrasenaUsuario(contrasenaUsuario);

            UsuariosDAO usuarioDAO = new UsuariosDAO();
            boolean registrado = usuarioDAO.registrarUsuario(persona, usuario);

            if (registrado) {
                request.getSession().setAttribute("mensaje", "✅ Usuario registrado correctamente.");
            } else {
                request.getSession().setAttribute("mensaje", "❌ Error al registrar el usuario.");
            }
            response.sendRedirect("UsuariosControl?action=mostrarUsuarios");
        }

    }

}
