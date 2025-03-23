package Controlador;

import Modelo.Persona;
import Modelo.RegistroUsuariosDTO;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonaDAO {
    //para mostar los datos en la vita de ususarios

    public List<Persona> listarPersonas() {
        List<Persona> lista = new ArrayList<>();
        String sql = "SELECT * FROM persona";

        try {
            Conexion miconexion = new Conexion();
            Connection nuevaCon = miconexion.getConn();
            PreparedStatement ps = nuevaCon.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Persona p = new Persona();
                p.setIdPersona(rs.getInt("idPersona"));
                p.setNumeroIdentificacion(rs.getInt("numeroIdentificacion"));
                p.setNombres(rs.getString("nombres"));
                p.setApellidos(rs.getString("apellidos"));
                p.setTelefono(rs.getString("telefono"));
                p.setCorreo(rs.getString("correo"));
                p.setDireccion(rs.getString("direccion"));
                p.setTipoIdentificacion_idTipoIdentificacion(rs.getInt("TipoIdentificacion_idTipoIdentificacion"));
                p.setRoles_idRoles(rs.getInt("Roles_idRoles"));
                p.setUsuarios_idUsuarios(rs.getInt("Usuarios_idUsuarios"));

                lista.add(p);
            }

            rs.close();
            ps.close();
            nuevaCon.close();

        } catch (SQLException e) {
        }

        return lista;
    }
//registrar una nueva persona en el modal

    public boolean registrarPersona(RegistroUsuariosDTO registroDTO, int idUsuario) {
        String sql = "INSERT INTO persona (TipoIdentificacion_idTipoIdentificacion, numeroIdentificacion, nombres, apellidos, telefono, correo, direccion, Roles_idRoles, Usuarios_idUsuarios) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            Conexion miconexion = new Conexion();
            Connection nuevaCon = miconexion.getConn();
            PreparedStatement ps = nuevaCon.prepareStatement(sql);

            ps.setInt(1, registroDTO.getIdTipoIdentificacion());
            ps.setInt(2, registroDTO.getNumeroIdentificacion());
            ps.setString(3, registroDTO.getNombres());
            ps.setString(4, registroDTO.getApellidos());
            ps.setString(5, registroDTO.getTelefono());
            ps.setString(6, registroDTO.getCorreo());
            ps.setString(7, registroDTO.getDireccion());
            ps.setInt(8, registroDTO.getIdRol());
            ps.setInt(9, idUsuario); // Asignar el ID del usuario creado

            int filasAfectadas = ps.executeUpdate();
            ps.close();
            nuevaCon.close();

            return filasAfectadas > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
//obtener la persona por el id y mostarla en el modal de editar al hacer clic
    public Persona obtenerPersonaPorId(int idPersona) {
        Persona persona = null;
        String sql = "SELECT numeroIdentificacion, nombres, apellidos, telefono, correo, direccion, TipoIdentificacion_idTipoIdentificacion, Roles_idRoles, Usuarios_idUsuarios FROM persona WHERE idPersona = ?";

        try {
            Conexion miconexion = new Conexion(); // Crea la conexión
            Connection nuevaCon = miconexion.getConn(); // Obtiene la conexión
            PreparedStatement ps = nuevaCon.prepareStatement(sql); // Prepara la consulta

            ps.setInt(1, idPersona); // Establece el parámetro de la consulta (idPersona)
            ResultSet rs = ps.executeQuery(); // Ejecuta la consulta y obtiene el ResultSet

            // Si se encuentra el registro, lo mapeamos a la clase Persona
            if (rs.next()) {
                persona = new Persona();
                persona.setIdPersona(idPersona);
                persona.setNumeroIdentificacion(rs.getInt("numeroIdentificacion"));
                persona.setNombres(rs.getString("nombres"));
                persona.setApellidos(rs.getString("apellidos"));
                persona.setTelefono(rs.getString("telefono"));
                persona.setCorreo(rs.getString("correo"));
                persona.setDireccion(rs.getString("direccion"));
                persona.setTipoIdentificacion_idTipoIdentificacion(rs.getInt("TipoIdentificacion_idTipoIdentificacion"));
                persona.setRoles_idRoles(rs.getInt("Roles_idRoles"));
                persona.setUsuarios_idUsuarios(rs.getInt("Usuarios_idUsuarios"));
            }

            // Cerramos la conexión
            rs.close();
            ps.close();
            nuevaCon.close();

        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de excepciones
        }

        return persona; // Retorna el objeto Persona con los datos obtenidos
    }

}
