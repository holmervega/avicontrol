
package Servlet;

import Controlador.UsuariosDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginControl", urlPatterns = {"/LoginControl"})
public class LoginControl extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

  
        String usuario = request.getParameter("usuario");
        String contrasena = request.getParameter("contrasena");

   
        UsuariosDAO usuarioDAO = new UsuariosDAO();
        boolean esValido = usuarioDAO.ValidarUsuarioLogin(usuario, contrasena);

          if (esValido) {
            // Crear una sesión para el usuario
            request.getSession().setAttribute("usuario", usuario);
            response.sendRedirect("home.jsp"); 
        } else {
            response.sendRedirect("index.jsp?error=1");
        }
    }
}
