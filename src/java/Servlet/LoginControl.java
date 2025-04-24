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

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String usuario = request.getParameter("usuario");
        String contrasena = request.getParameter("contrasena");

         System.out.println("Usuario recibido: " + usuario);
        System.out.println("Contraseña recibida: " + contrasena);
        
        UsuariosDAO usuarioDAO = new UsuariosDAO();
        Persona persona = usuarioDAO.ValidarUsuarioLogin(usuario, contrasena);

          // Depuración: Verificar si la persona fue encontrada
        if (persona != null) {
            System.out.println("Usuario validado. Persona encontrada:");
            System.out.println("ID Persona: " + persona.getIdPersona());
            System.out.println("Rol: " + persona.getDescripcionRol());
        } else {
            System.out.println("Usuario no encontrado o contraseña incorrecta.");
        }
        
        if (persona != null) {
            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuario);
            session.setAttribute("idPersona", persona.getIdPersona());
            session.setAttribute("idRol", persona.getIdRoles());
            session.setAttribute("rolDescripcion", persona.getDescripcionRol());

            // Redireccionar según rol
            String rol = persona.getDescripcionRol();
            if ("Administrador".equalsIgnoreCase(rol)) {
                response.sendRedirect("admin/home.jsp");
            } else if ("Cliente".equalsIgnoreCase(rol)) {
                response.sendRedirect("cliente/home.jsp");
            } else {
                response.sendRedirect("home.jsp"); // Ruta genérica si no se identifica el rol
            }

        } else {
            response.sendRedirect("index.jsp?error=1");
        }
    }
}
