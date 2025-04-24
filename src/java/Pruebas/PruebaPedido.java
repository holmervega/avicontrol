package Pruebas;

import Controlador.PedidoDetalleDAO;
import Modelo.PedidoDetalle;
import Modelo.Productos;
import Modelo.Unidades;
import java.util.List;

public class PruebaPedido {

 
    public static void main(String[] args) {
        // Crear una instancia de PedidoDetalleDAO
        PedidoDetalleDAO pedidoDetalleDAO = new PedidoDetalleDAO();

        // Llamar al método listarPedidos() para obtener los pedidos
        List<PedidoDetalle> listaPedidos = pedidoDetalleDAO.listarPedidos();

        // Imprimir los detalles de los pedidos
        for (PedidoDetalle pedidoDetalle : listaPedidos) {
            System.out.println("Pedido Cabecera: ");
            System.out.println("ID Pedido: " + pedidoDetalle.getPedidoCabecera().getIdPedidoCabecera());
            System.out.println("Número de Pedido: " + pedidoDetalle.getPedidoCabecera().getNumeroPedido());
            System.out.println("Fecha de Pedido: " + pedidoDetalle.getPedidoCabecera().getFechaPedido());
            System.out.println("Nombre de Persona: " + pedidoDetalle.getPedidoCabecera().getNombrePersona());
            System.out.println("Apellido de Persona: " + pedidoDetalle.getPedidoCabecera().getApellidoPersona());
            System.out.println("Identificación de Persona: " + pedidoDetalle.getPedidoCabecera().getNumeroIdentificacion());

            // Mostrar los productos
            System.out.println("Productos: ");
            for (Productos producto : pedidoDetalle.getProductos()) {
                System.out.println("  Nombre: " + producto.getNombre());
                System.out.println("  Descripción: " + producto.getDescripcion());
            }

            // Mostrar las unidades
            System.out.println("Unidades: ");
            for (Unidades unidad : pedidoDetalle.getUnidades()) {
                System.out.println("  Descripción: " + unidad.getDescripcionUnidades());
            }

            // Mostrar el total de la cantidad y el valor del pedido
            System.out.println("Cantidad Total: " + pedidoDetalle.getCantidad());
            System.out.println("Precio Unitario: " + pedidoDetalle.getPrecioUnitario());
            System.out.println("Valor Total: " + pedidoDetalle.getValorPedido());

            System.out.println("-------------------------------");
        }
    }
}
