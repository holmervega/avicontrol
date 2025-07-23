package Pruebas;

import Controlador.PedidoDetalleDAO;
import Modelo.PedidoDetalle;
import Modelo.PedidoCabecera;
import Modelo.Productos;
import Modelo.Unidades;

import java.util.List;

public class MainReportePrueba {

    public static void main(String[] args) {

        // Parámetros de prueba (puedes cambiarlos)
        String identificacion = "123";  // Cambia esto según tu base de datos
        String fecha = "";              // O pon una fecha válida tipo "2025-07-23"

        PedidoDetalleDAO dao = new PedidoDetalleDAO();
        List<PedidoDetalle> pedidos = dao.listarPedidosFiltrados(identificacion, fecha);

        if (pedidos.isEmpty()) {
            System.out.println("⚠️ No se encontraron pedidos para los filtros ingresados.");
        } else {
            for (PedidoDetalle pd : pedidos) {
                PedidoCabecera pc = pd.getPedidoCabecera();
                System.out.println("📦 Pedido #" + pc.getNumeroPedido() + " | Fecha: " + pc.getFechaPedido());
                System.out.println("👤 Cliente: " + pc.getNombrePersona() + " " + pc.getApellidoPersona());

                for (int i = 0; i < pd.getProductos().size(); i++) {
                    Productos prod = pd.getProductos().get(i);
                    Unidades uni = pd.getUnidades().get(i);
                    double cantidad = pd.getCantidades().get(i);
                    double precio = pd.getPreciosUnitarios().get(i);

                    System.out.printf("🛒 Producto: %s | Unidad: %s | Cantidad: %.0f | Precio: %.0f\n",
                            prod.getNombre(), uni.getDescripcionUnidades(), cantidad, precio);
                }

                System.out.println("------------------------------------------------------------");
            }
        }
    }
}
