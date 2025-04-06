package Controlador;

import Modelo.Persona;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

      //cargar datos de los cliente registrados, en la vista de Clientes.jsp
    public List<Persona> listarClientes() {
        List<Persona> listaClientes = new ArrayList<>();
        Conexion miconexion = new Conexion();
        Connection nuevaCon = miconexion.getConn();

        try {
            String querySQL = "SELECT p.idPersona, p.numeroIdentificacion, p.nombres, p.apellidos, " +
                     "p.telefono, p.correo, p.direccion, " +
                     "p.TipoIdentificacion_idTipoIdentificacion, ti.descripcionTipoIdentificacion, " +
                     "p.Roles_idRoles, r.descripcionRol " +
                     "FROM persona p " +
                     "INNER JOIN tipoidentificacion ti ON p.TipoIdentificacion_idTipoIdentificacion = ti.idTipoIdentificacion " +
                     "INNER JOIN roles r ON p.Roles_idRoles = r.idRoles " +
                     "WHERE r.descripcionRol = 'Cliente'";
 
            PreparedStatement sentencia = nuevaCon.prepareStatement(querySQL);
            ResultSet rs = sentencia.executeQuery();

            while (rs.next()) {         
                Persona persona = new Persona();
                persona.setIdPersona(rs.getInt("idPersona"));
                persona.setNumeroIdentificacion(rs.getInt("numeroIdentificacion"));
                persona.setNombres(rs.getString("nombres"));
                persona.setApellidos(rs.getString("apellidos"));
                persona.setTelefono(rs.getString("telefono"));
                persona.setCorreo(rs.getString("correo"));
                persona.setDireccion(rs.getString("direccion"));
                persona.setTipoIdentificacion_idTipoIdentificacion(rs.getInt("TipoIdentificacion_idTipoIdentificacion"));
                persona.setRoles_idRoles(rs.getInt("Roles_idRoles"));
                persona.setDescripcionTipoIdentificacion(rs.getString("descripcionTipoIdentificacion"));
                persona.setDescripcionRol(rs.getString("descripcionRol"));

                
                listaClientes.add(persona);
            }

        } catch (Exception ex) {
            System.err.println("Error al listar clientes: " + ex.getMessage());
        }

        return listaClientes;
    }


}
