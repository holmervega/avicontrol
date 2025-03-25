/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Pruebas;
import Controlador.UsuariosDAO;
import Modelo.Usuarios;

public class Main {





    public static void main(String[] args) {
        UsuariosDAO usuarioDAO = new UsuariosDAO();
        
        // ID de la persona que queremos obtener
        int idPersona = 2; // Cambia este valor según tu base de datos
        
        Usuarios usuario = usuarioDAO.obtenerUsuarioPorId(idPersona);

        if (usuario != null) {
            System.out.println("✅ Usuario encontrado:");
            System.out.println("ID Usuario: " + usuario.getIdUsuarios());
            System.out.println("Nombre Usuario: " + usuario.getNombreUsuario());
            System.out.println("Contraseña: " + usuario.getContrasenaUsuario());
            System.out.println("ID Persona: " + usuario.getPersona().getIdPersona());
            System.out.println("Nombres: " + usuario.getPersona().getNombres());
            System.out.println("Apellidos: " + usuario.getPersona().getApellidos());
            System.out.println("Teléfono: " + usuario.getPersona().getTelefono());
            System.out.println("Correo: " + usuario.getPersona().getCorreo());
            System.out.println("Dirección: " + usuario.getPersona().getDireccion());
            System.out.println("Tipo Identificación: " + usuario.getPersona().getDescripcionTipoIdentificacion());
            System.out.println("Rol: " + usuario.getPersona().getDescripcionRol());
        } else {
            System.out.println("❌ No se encontró un usuario con el ID de persona: " + idPersona);
        }
    }
}




