package Pruebas;

import Controlador.Conexion;
import java.sql.Connection;
import java.sql.SQLException;

public class PruebaConexion {

    public static void main(String[] args) {
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConn();

        if (conn != null) {
            System.out.println("✅ Conexión establecida correctamente a la base de datos.");
            try {
                conn.close();
                System.out.println("✅ Conexión cerrada correctamente.");
            } catch (SQLException e) {
                System.err.println("❌ Error al cerrar la conexión: " + e.getMessage());
            }
        } else {
            System.out.println("❌ No se pudo establecer la conexión a la base de datos.");
        }
    }
}
