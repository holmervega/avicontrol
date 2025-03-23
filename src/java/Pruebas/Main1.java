/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Pruebas;

import java.sql.SQLException;
import java.sql.Connection;
import Controlador.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main1 {
    public static void main(String[] args) {
        // ID del rol que quieres consultar
        int idRol = 6;  // Cambia este valor por un ID válido de tu base de datos
        
        // Llamada al método para obtener el nombre del rol
        String nombreRol = obtenerNombreRolPorId(idRol);
        
        // Verifica si se encontró el rol y muestra el resultado
        if (nombreRol != null) {
            System.out.println("El nombre del rol con ID " + idRol + " es: " + nombreRol);
        } else {
            System.out.println("No se encontró el rol con ID: " + idRol);
        }
    }

    // Método para obtener el nombre del rol por el idRoles
    public static String obtenerNombreRolPorId(int idRoles) {
        String nombreRol = null;
        String sql = "SELECT descripcionRol FROM roles WHERE idRoles = ?";
        
        try {
            Conexion miconexion = new Conexion(); // Crea la conexión
            Connection nuevaCon = miconexion.getConn(); // Obtiene la conexión
            PreparedStatement ps = nuevaCon.prepareStatement(sql); // Prepara la consulta

            ps.setInt(1, idRoles); // Establece el parámetro (idRol)
            ResultSet rs = ps.executeQuery(); // Ejecuta la consulta y obtiene el ResultSet

            if (rs.next()) {
                nombreRol = rs.getString("descripcionRol"); // Obtiene la descripción del rol
            } else {
                // Si no se encuentra el rol, puedes manejar el caso aquí
                System.out.println("No se encontró el rol con el id: " + idRoles);
            }

            // Cerramos la conexión
            rs.close();
            ps.close();
            nuevaCon.close();

        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de excepciones, considera usar un logger en lugar de imprimir
        }

        return nombreRol; // Retorna el nombre del rol o null si no se encontró
    }
}

