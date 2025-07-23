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

        // DEPURACIÓN: imprimir filtros recibidos
        System.out.println("📌 Filtros recibidos:");
        System.out.println("Identificación: " + identificacion);
        System.out.println("Fecha: " + fecha);

        PedidoDetalleDAO dao = new PedidoDetalleDAO();
        List<PedidoDetalle> listaPedidosFiltrados = dao.listarPedidosFiltrados(identificacion, fecha);

        // DEPURACIÓN: imprimir tamaño de la lista
        if (listaPedidosFiltrados != null) {
            System.out.println("✅ Total de pedidos encontrados: " + listaPedidosFiltrados.size());
        } else {
            System.out.println("⚠️ La lista de pedidos es null.");
        }

        request.setAttribute("listaPedidosFiltrados", listaPedidosFiltrados);
        request.getRequestDispatcher("reportes.jsp").forward(request, response);

        // DEPURACIÓN: confirmación de reenvío
        System.out.println("➡️ Datos enviados a reportes.jsp");
    }
}
