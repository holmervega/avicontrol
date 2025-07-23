package Controlador;

import Modelo.Roles;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RolesDAO {
    
    //para mostrar en lod diferentes select 
    
     public List<Roles> obtenerRolesBD() {
        List<Roles> listaRoles = new ArrayList<>();
        String sql = "SELECT * FROM roles";
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
                Roles rol = new Roles();
                rol.setIdRoles(rs.getInt("idRoles"));  // Asegúrate de que el nombre de la columna coincida
                rol.setDescripcionRol(rs.getString("descripcionRol"));  // Asegúrate de que el nombre de la columna coincida

                listaRoles.add(rol);
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

        return listaRoles;
    }
//obtener la descripcion del rol para mostrar en la table de editar 
    public String obtenerDescripcionRolPorIdPersona(int idPersona) {
        Conexion miconexion = new Conexion();
        Connection nuevaCon = miconexion.getConn();
        String descripcionRol = null;

        String sql = "SELECT r.descripcionRol "
                + "FROM persona p "
                + "JOIN roles r ON p.Roles_idRoles = r.idRoles "
                + "WHERE p.idPersona = ?";

        try (PreparedStatement pst = nuevaCon.prepareStatement(sql)) {
            pst.setInt(1, idPersona);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                descripcionRol = rs.getString("descripcionRol");
            }
        } catch (SQLException e) {
            System.out.println("⚠ Error al obtener la descripción del rol: " + e.getMessage());
        } finally {
            try {
                if (nuevaCon != null) {
                    nuevaCon.close();
                }
            } catch (SQLException e) {
                System.out.println("⚠ Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return descripcionRol;
    }


}
