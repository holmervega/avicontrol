package Controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Conexion {

    private Connection conn;
    private Statement stm;

    private final String driver = "com.mysql.cj.jdbc.Driver";
    private final String user = "root";
    private final String password = "";
    private final String basedatos = "avicontrol";
    private final String url = "jdbc:mysql://localhost:3306/" + basedatos + "?useTimezone=true&serverTimezone=UTC";

    public Conexion() {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);

            if (conn != null) {
                System.out.println("✅ Conexión establecida con la base de datos: " + basedatos);
            } else {
                System.err.println("❌ No se pudo establecer la conexión con la base de datos: " + basedatos);
            }
        } catch (Exception e) {
            System.err.println("❌ Error al conectar con la base de datos:");
            e.printStackTrace(); // Imprime el detalle completo del error
        }
    }

    public Connection getConn() {
        return conn;
    }
}
