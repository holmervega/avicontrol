package Pruebas;

import Controlador.PersonaDAO;
import Modelo.Persona;

public class Main {




    public static void main(String[] args) {
        // Crear un objeto Persona con datos de prueba
        Persona persona = new Persona();

        // 🔹 Simula los datos que vendrían del formulario
        persona.setIdPersona(62); // ID de la persona a actualizar (ajusta según tu BD)
        persona.setTipoIdentificacion_idTipoIdentificacion(2); // Ej: CC
        persona.setNumeroIdentificacion(1234567890);
        persona.setNombres("Juan");
        persona.setApellidos("Pérez");
        persona.setTelefono("3001234567");
        persona.setCorreo("juan.perez@example.com");
        persona.setDireccion("Calle 123 #45-67");

        // Llama al DAO
        PersonaDAO dao = new PersonaDAO();
        boolean resultado = dao.actualizarCliente(persona);

        // Resultado
        if (resultado) {
            System.out.println("✅ Cliente actualizado correctamente.");
        } else {
            System.out.println("❌ Error al actualizar el cliente.");
        }
    }
}

