package Servlet;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

import Controlador.PedidoDetalleDAO;
import Modelo.PedidoDetalle;
import Modelo.Productos;
import Modelo.Unidades;

@WebServlet("/ReportePDFControl")
public class ReportePDFControl extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        // 1. Obtener parámetros
        String identificacion = request.getParameter("identificacion");
        String fecha = request.getParameter("fecha");

        // 2. Consultar los pedidos
        PedidoDetalleDAO dao = new PedidoDetalleDAO();
        List<PedidoDetalle> pedidos = dao.listarPedidosFiltrados(identificacion, fecha);

        // 3. Configurar la respuesta como PDF
        response.setContentType("application/pdf");
     response.setHeader("Content-Disposition", "inline; filename=reporte_pedidos.pdf");


        try {
            Document documento = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(documento, response.getOutputStream());
            documento.open();

            Font titulo = new Font(Font.HELVETICA, 16, Font.BOLD);
            Font encabezado = new Font(Font.HELVETICA, 12, Font.BOLD);
            Font normal = new Font(Font.HELVETICA, 10);

            documento.add(new Paragraph("Reporte de Pedidos", titulo));
            documento.add(new Paragraph(" "));

            double totalGeneral = 0;

            for (PedidoDetalle p : pedidos) {
                double totalPedido = 0;

                // Datos del pedido
                documento.add(new Paragraph("Pedido Nº: " + p.getPedidoCabecera().getNumeroPedido(), encabezado));
                documento.add(new Paragraph("Fecha: " + p.getPedidoCabecera().getFechaPedido(), encabezado));
                documento.add(new Paragraph("Cliente: " + p.getPedidoCabecera().getNombrePersona() + " " + p.getPedidoCabecera().getApellidoPersona(), encabezado));
                documento.add(new Paragraph(" "));

                // Tabla de productos
                PdfPTable tabla = new PdfPTable(5);
                tabla.setWidthPercentage(100);
                tabla.setWidths(new float[]{3, 2, 2, 2, 2});

                tabla.addCell(new PdfPCell(new Phrase("Producto", encabezado)));
                tabla.addCell(new PdfPCell(new Phrase("Unidad", encabezado)));
                tabla.addCell(new PdfPCell(new Phrase("Cantidad", encabezado)));
                tabla.addCell(new PdfPCell(new Phrase("Precio Unitario", encabezado)));
                tabla.addCell(new PdfPCell(new Phrase("Subtotal", encabezado)));

                List<Productos> productos = p.getProductos();
                List<Unidades> unidades = p.getUnidades();
                List<Double> cantidades = p.getCantidades();
                List<Double> precios = p.getPreciosUnitarios();

                for (int i = 0; i < productos.size(); i++) {
                    String nombre = productos.get(i).getNombre();
                    String unidad = unidades.get(i).getDescripcionUnidades();
                    double cantidad = cantidades.get(i);
                    double precio = precios.get(i);
                    double subtotal = cantidad * precio;
                    totalPedido += subtotal;

                    tabla.addCell(new Phrase(nombre, normal));
                    tabla.addCell(new Phrase(unidad, normal));
                    tabla.addCell(new Phrase(String.format("%.0f", cantidad), normal));
                    tabla.addCell(new Phrase(String.format("%,.0f", precio), normal));
                    tabla.addCell(new Phrase(String.format("%,.0f", subtotal), normal));
                }

                // Total por pedido
                PdfPCell celdaTotal = new PdfPCell(new Phrase("Total del pedido", encabezado));
                celdaTotal.setColspan(4);
                celdaTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
                tabla.addCell(celdaTotal);
                tabla.addCell(new Phrase(String.format("%,.0f", totalPedido), encabezado));

                documento.add(tabla);
                documento.add(new Paragraph(" "));

                totalGeneral += totalPedido;
            }

            // Total general
            Paragraph totalG = new Paragraph("Total general: " + String.format("%,.0f", totalGeneral), encabezado);
            totalG.setAlignment(Paragraph.ALIGN_RIGHT);
            documento.add(totalG);

            documento.close();

        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/plain");
            response.getWriter().write("Error generando PDF: " + e.getMessage());
        }
    }
}
