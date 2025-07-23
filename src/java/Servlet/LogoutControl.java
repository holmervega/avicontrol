package Servlet;



import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(name = "LogoutControl", urlPatterns = {"/LogoutControl"})
public class LogoutControl extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Cierra la sesión actual
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Redirige al login (index.jsp)
        response.sendRedirect("index.jsp");
    }
}