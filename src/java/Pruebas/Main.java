package Pruebas;

import Controlador.UsuariosDAO;

public class Main {

    public static void main(String[] args) {
        // Instancia de la clase que tiene el método eliminarUsuarioPorIdPersona
        UsuariosDAO usuarioDAO = new UsuariosDAO();

        int idPersonaAEliminar = 45; // Cambia este ID por uno existente en tu base de datos

        boolean resultado = usuarioDAO.eliminarUsuarioPorIdPersona(idPersonaAEliminar);

        if (resultado) {
            System.out.println("✅ Eliminación exitosa: Se eliminó la persona y su usuario.");
        } else {
            System.out.println("❌ Error: No se pudo eliminar la persona y/o su usuario.");
        }
    }
}
