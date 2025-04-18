package Pruebas;

import Controlador.UnidadesDAO;
import Modelo.Unidades;
import java.util.List;

public class PruebaPedido {

    public static void main(String[] args) {
        // Crear instancia de UnidadesDAO
        UnidadesDAO unidadesDAO = new UnidadesDAO();

        // Obtener todas las unidades
        List<Unidades> listaUnidades = unidadesDAO.obtenerUnidades();

        // Verificar si la lista tiene datos
        if (listaUnidades.isEmpty()) {
            System.out.println("No se encontraron unidades.");
        } else {
            // Imprimir los datos de las unidades obtenidas
            System.out.println("Lista de Unidades:");
            for (Unidades unidad : listaUnidades) {
                System.out.println("ID: " + unidad.getIdUnidades() + ", Descripción: " + unidad.getDescripcionUnidades());
            }
        }
    }
}
