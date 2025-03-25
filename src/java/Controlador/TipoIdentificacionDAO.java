package Controlador;

import Modelo.TipoIdentificacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TipoIdentificacionDAO {
    
    //obtener el tipo de identificacion por medio de del idtopoidentificaion para mostar en la vista usuarios

    public String obtenerDescripcionTipoIdentificacionpoId(int idTipoIdentificacion) {
        String descripcion = "No encontrado";
        String sql = "SELECT descripcionTipoIdentificacion FROM tipoidentificacion WHERE idTipoIdentificacion = ?";

        try {
            Conexion miconexion = new Conexion();
            Connection nuevaCon = miconexion.getConn();
            PreparedStatement ps = nuevaCon.prepareStatement(sql);
            ps.setInt(1, idTipoIdentificacion);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                descripcion = rs.getString("descripcionTipoIdentificacion");
            }

            rs.close();
            ps.close();
            nuevaCon.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return descripcion;
    }
    
    public List<TipoIdentificacion> obtenerTipoIdentificacionBD() {
        List<TipoIdentificacion> listaTipos = new ArrayList<>();
        String sql = "SELECT * FROM tipoIdentificacion";

        try {
            Conexion miconexion = new Conexion();
            Connection nuevaCon = miconexion.getConn();
            PreparedStatement ps = nuevaCon.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TipoIdentificacion tipo = new TipoIdentificacion();
                tipo.setIdTipoIdentificacion(rs.getInt("idTipoIdentificacion"));
                tipo.setDescripcionTipoIdentificacion(rs.getString("descripcionTipoIdentificacion")); // Aquí aseguramos que coincide

                listaTipos.add(tipo);
            }

            rs.close();
            ps.close();
            nuevaCon.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaTipos;
    }
    
    //obtener la descripcion de rol por medio de Roles_idRoles de la tabla ususario
    public String obtenerNombreRolPorId(int idRoles) {
    String nombreRol = null;
    String sql = "SELECT descripcionRol FROM roles WHERE idRoles = ?";
    
    try {
        Conexion miconexion = new Conexion(); // Crea la conexión
        Connection nuevaCon = miconexion.getConn(); // Obtiene la conexión
        PreparedStatement ps = nuevaCon.prepareStatement(sql); // Prepara la consulta

        ps.setInt(1, idRoles); // Establece el parámetro (idRol)
        ResultSet rs = ps.executeQuery(); // Ejecuta la consulta y obtiene el ResultSet

        if (rs.next()) {
            nombreRol = rs.getString("descripcionRol"); // Obtiene el nombre del rol
        }

        // Cerramos la conexión
        rs.close();
        ps.close();
        nuevaCon.close();

    } catch (SQLException e) {
        e.printStackTrace(); // Manejo de excepciones
    }

    return nombreRol; // Retorna el nombre del rol
}

}
