package Servlet;


import Controlador.PedidoDetalleDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import Modelo.PedidoDetalle;

@WebServlet("/ReportesControl")
public class ReportesControl extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String identificacion = request.getParameter("identificacion");
        String fecha = request.getParameter("fecha");

        PedidoDetalleDAO dao = new PedidoDetalleDAO();
        List<PedidoDetalle> listaPedidosFiltrados = dao.listarPedidosFiltrados(identificacion, fecha);

        request.setAttribute("listaPedidosFiltrados", listaPedidosFiltrados);
        request.getRequestDispatcher("reportes.jsp").forward(request, response);
    }
}
