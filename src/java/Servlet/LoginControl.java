package Servlet;

import Controlador.UsuariosDAO;
import Modelo.Persona;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "LoginControl", urlPatterns = {"/LoginControl"})
public class LoginControl extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener los parámetros del formulario
        String usuario = request.getParameter("usuario");
        String contrasena = request.getParameter("contrasena");

        System.out.println("Usuario recibido: " + usuario);
        System.out.println("Contraseña recibida: " + contrasena);

        // Instanciar el DAO y realizar la validación
        UsuariosDAO usuarioDAO = new UsuariosDAO();
        Persona persona = null;
        try {
            persona = usuarioDAO.ValidarUsuarioLogin(usuario, contrasena);
        } catch (Exception ex) {
            // Registrar el error en caso de problemas al consultar la base de datos
            System.err.println("Error al validar el usuario: " + ex.getMessage());
            response.sendRedirect("index.jsp?error=2"); // Error genérico al conectar con la base de datos
            return;
        }

        // Depuración: Verificar si la persona fue encontrada
        if (persona != null) {
            System.out.println("Usuario validado. Persona encontrada:");
            System.out.println("ID Persona: " + persona.getIdPersona());
            System.out.println("Rol: " + persona.getDescripcionRol());
        } else {
            System.out.println("Usuario no encontrado o contraseña incorrecta.");
        }

        // Si el usuario es válido, se guarda en sesión y se redirige
        if (persona != null) {
            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuario);
            session.setAttribute("idPersona", persona.getIdPersona());
            session.setAttribute("idRol", persona.getIdRoles());
            session.setAttribute("rolDescripcion", persona.getDescripcionRol());

            // Redireccionar según el rol del usuario
            String rol = persona.getDescripcionRol();
            if ("administrador".equalsIgnoreCase(rol)) {
                response.sendRedirect("home.jsp");
                System.out.println("Rol recibido de la BD: '" + rol + "'");

            } else if ("conductor".equalsIgnoreCase(rol)) {
                response.sendRedirect("home.jsp");
            } else {
                response.sendRedirect("home.jsp"); // Ruta genérica si no se identifica el rol
            }

        } else {
            // En caso de que no se haya encontrado al usuario o la contraseña sea incorrecta
            response.sendRedirect("index.jsp?error=1");
        }
    }
}
