package Controlador;

import Modelo.Roles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RolesDAO {

    // obtener personas por id para mostrar en la vista de ususarios
    public String obtenerDescripcionporid(int idRoles) {
        String descripcionRol = "";
        String sql = "SELECT descripcionRol FROM roles WHERE idRoles = ?";

        try {
            Conexion miconexion = new Conexion();
            Connection nuevaCon = miconexion.getConn();
            PreparedStatement ps = nuevaCon.prepareStatement(sql);
            ps.setInt(1, idRoles);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                descripcionRol = rs.getString("descripcionRol");
            }

            rs.close();
            ps.close();
            nuevaCon.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return descripcionRol;
    }

    // mostrar los roles creados en la base de datos para poder selecionar 
    public List<Roles> obtenerRolesdeBD() {
        List<Roles> listaRoles = new ArrayList<>();
        String sql = "SELECT * FROM roles";

        try {
            Conexion miconexion = new Conexion();
            Connection nuevaCon = miconexion.getConn();
            PreparedStatement ps = nuevaCon.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Roles rol = new Roles();
                rol.setIdRoles(rs.getInt("idRoles"));
                rol.setDescripcionRol(rs.getString("descripcionRol"));
                listaRoles.add(rol);
            }

            rs.close();
            ps.close();
            nuevaCon.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaRoles;
    }

}
