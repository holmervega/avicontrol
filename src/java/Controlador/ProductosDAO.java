/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Productos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author holmer
 */
public class ProductosDAO {
    //para cargar los productos en el selct al registrar un pedido
 public List<Productos> listarProductos() {
    List<Productos> productos = new ArrayList<>();
    String sql = "SELECT idProductos, nombre, descripcion FROM productos";
    Conexion miconexion = new Conexion(); // Crear una nueva instancia de Conexion
    try (Connection conn = miconexion.getConn(); // Obtener la conexión desde miconexion
         PreparedStatement ps = conn.prepareStatement(sql); // Preparar la consulta
         ResultSet rs = ps.executeQuery()) { // Ejecutar la consulta
        while (rs.next()) {
            Productos p = new Productos();
            p.setIdProductos(rs.getInt("idProductos"));
            p.setNombre(rs.getString("nombre"));
            p.setDescripcion(rs.getString("descripcion"));
            productos.add(p);
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Manejo de excepciones
    }
    return productos;
}


    
}
