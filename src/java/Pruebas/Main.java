/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Pruebas;
import Controlador.PersonaDAO;
import Modelo.Persona;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        // Instanciar el DAO y llamar al método que obtiene los datos de la persona
        PersonaDAO personaDAO = new PersonaDAO();
        
        // Suponiendo que quieres obtener la persona con idPersona = 1
        int idPersona = 2;
        
        // Obtener la persona
        Persona persona = personaDAO.obtenerPersonaPorId(idPersona);
        
        // Verificar si la persona fue encontrada y mostrar los datos
        if (persona != null) {
            System.out.println("Persona encontrada:");
            System.out.println("ID Persona: " + persona.getNumeroIdentificacion());
            System.out.println("Tipo de Identificación: " + persona.getTipoIdentificacion_idTipoIdentificacion());
            System.out.println("Número de Identificación: " + persona.getNumeroIdentificacion());
            System.out.println("Nombres: " + persona.getNombres());
            System.out.println("Apellidos: " + persona.getApellidos());
            System.out.println("Teléfono: " + persona.getTelefono());
            System.out.println("Correo: " + persona.getCorreo());
            System.out.println("Dirección: " + persona.getDireccion());
            System.out.println("ID Rol: " + persona.getRoles_idRoles());
        } else {
            System.out.println("Persona no encontrada.");
        }
    }
}
