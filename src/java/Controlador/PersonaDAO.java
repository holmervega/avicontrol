package Controlador;

import Modelo.Persona;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonaDAO {
  
      
    public Persona obtenerPersonaPorId(int idPersona) {
    Conexion miconexion = new Conexion();
    Connection nuevaCon = miconexion.getConn();
    Persona persona = null;

    String sql = "SELECT * FROM persona WHERE idPersona = ?";
    
    try {
        PreparedStatement ps = nuevaCon.prepareStatement(sql);
        ps.setInt(1, idPersona);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            persona = new Persona();
            persona.setIdPersona(rs.getInt("idPersona"));
            persona.setNumeroIdentificacion(rs.getInt("numeroIdentificacion"));
            persona.setNombres(rs.getString("nombres"));
            persona.setApellidos(rs.getString("apellidos"));
            persona.setTelefono(rs.getString("telefono"));
            persona.setCorreo(rs.getString("correo"));
            persona.setDireccion(rs.getString("direccion"));
            persona.setTipoIdentificacion_idTipoIdentificacion(rs.getInt("TipoIdentificacion_idTipoIdentificacion"));
            persona.setRoles_idRoles(rs.getInt("Roles_idRoles"));
        }

        rs.close();
        ps.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return persona;
}



}
