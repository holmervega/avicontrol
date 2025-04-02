package Pruebas;

import Controlador.UsuariosDAO;
import Modelo.Persona;
import Modelo.Usuarios;

public class Main1 {

    public static void main(String[] args) {
        // Crear objetos de prueba
        Usuarios usuario = new Usuarios();
        usuario.setNombreUsuario("holmer");
        usuario.setContrasenaUsuario("1234");

        Persona persona = new Persona();
        persona.setNumeroIdentificacion(1001);
        persona.setNombres("Juan");
        persona.setApellidos("Pérez");
        persona.setTelefono("3001234567");
        persona.setCorreo("juan.perez@example.com");
        persona.setDireccion("Calle 123 #45-67");
        persona.setTipoIdentificacion_idTipoIdentificacion(1); // Suponiendo que 1 es un ID válido
        persona.setRoles_idRoles(6); // Suponiendo que 2 es un ID válido

        // Instanciar la clase que contiene el método registrarUsuario
        UsuariosDAO usuarioDAO = new UsuariosDAO();

        // Intentar registrar el usuario
        boolean resultado = usuarioDAO.registrarUsuario(persona, usuario);

        // Mostrar resultado
        if (resultado) {
            System.out.println("Usuario registrado correctamente.");
        } else {
            System.out.println("Error al registrar el usuario.");
        }
    }
}
