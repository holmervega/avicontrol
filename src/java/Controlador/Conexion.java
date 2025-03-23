package Controlador;

import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Connection;

public class Conexion {

    private Connection conn;
    private Statement stm;
    private String driver = "com.mysql.cj.jdbc.Driver";
    private String user = "root";
    private String password = "";
    private String basedatos = "avicontrol";
    private String url = "jdbc:mysql://localhost:3306/" + basedatos + "?useTimezone=true&serverTimezone=UTC";

    public Conexion() {

        conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
           /* if (conn == null) {
                System.out.println("no se pude hacer la conexion" + url);
            } else {
                System.out.println("la conexion se establecio corectamente con la base de datos " + basedatos);

            }*/
        } catch (Exception e) {
            System.err.println(e.getMessage());

        }
    }
    public Connection getConn(){
        return conn;
    }

}