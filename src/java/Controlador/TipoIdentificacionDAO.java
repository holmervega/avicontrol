package Controlador;

import Modelo.TipoIdentificacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TipoIdentificacionDAO {

    public List<TipoIdentificacion> obtenerTipoIdentificacionBD() {
        List<TipoIdentificacion> listaTipos = new ArrayList<>();
        String sql = "SELECT * FROM tipoIdentificacion";
        Connection nuevaCon = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Obtener la conexión a la base de datos
            Conexion miconexion = new Conexion();
            nuevaCon = miconexion.getConn();

            // Preparar la consulta
            ps = nuevaCon.prepareStatement(sql);

            // Ejecutar la consulta
            rs = ps.executeQuery();

            // Procesar los resultados
            while (rs.next()) {
                TipoIdentificacion tipo = new TipoIdentificacion();
                tipo.setIdTipoIdentificacion(rs.getInt("idTipoIdentificacion"));
                tipo.setDescripcionTipoIdentificacion(rs.getString("descripcionTipoIdentificacion")); // Verifica si es correcto

                listaTipos.add(tipo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar los recursos explícitamente
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (nuevaCon != null) {
                    nuevaCon.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return listaTipos;
    }
    
    // obterner descrion para mostraren la tabla editar 
    public String obtenerDescripcionPorIdPersona(int idPersona) {
    Conexion miconexion = new Conexion();
    Connection nuevaCon = miconexion.getConn();
    String descripcion = null;

    String sql = "SELECT ti.descripcionTipoIdentificacion " +
                 "FROM persona p " +
                 "JOIN tipoidentificacion ti ON p.TipoIdentificacion_idTipoIdentificacion = ti.idTipoIdentificacion " +
                 "WHERE p.idPersona = ?";

    try (PreparedStatement pst = nuevaCon.prepareStatement(sql)) {
        pst.setInt(1, idPersona);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            descripcion = rs.getString("descripcionTipoIdentificacion");
        }
    } catch (SQLException e) {
        System.out.println("⚠ Error al obtener la descripción del tipo de identificación: " + e.getMessage());
    } finally {
        try {
            if (nuevaCon != null) {
                nuevaCon.close();
            }
        } catch (SQLException e) {
            System.out.println("⚠ Error al cerrar la conexión: " + e.getMessage());
        }
    }
    return descripcion;
}

    
}
