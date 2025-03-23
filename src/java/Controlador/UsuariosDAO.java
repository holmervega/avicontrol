package Controlador;

import Modelo.RegistroUsuariosDTO;
import Modelo.Usuarios;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuariosDAO {
     // validar el ususario para el login
    public boolean ValidarUsuarioLogin(String nombreUsuario, String contrasenaUsuario) {
        boolean existe = false;

        Conexion miconexion = new Conexion();
        Connection nuevaCon = miconexion.getConn();

        try {
            String querySQL = "SELECT * FROM Usuarios WHERE nombreUsuario = ? AND contrasenaUsuario = ?";
            PreparedStatement sentencia = nuevaCon.prepareStatement(querySQL);
            sentencia.setString(1, nombreUsuario);
            sentencia.setString(2, contrasenaUsuario);
            ResultSet rs = sentencia.executeQuery();

            if (rs.next()) {
                existe = true;
            }
        } catch (Exception ex) {
            System.err.println("Error al validar Usuario: " + ex.getMessage());
        }

        return existe;
    }
// obtener los usuario por el id para mostrarlos en la vista de usuarios
    public Usuarios obtenerUsuarioPorId(int idUsuarios) {
        
        Usuarios usuario = null;
        String sql = "SELECT nombreUsuario, contrasenaUsuario FROM Usuarios WHERE idUsuarios = ?";

        try {
            Conexion miconexion = new Conexion();
            Connection nuevaCon = miconexion.getConn();
            PreparedStatement ps = nuevaCon.prepareStatement(sql);
            ps.setInt(1, idUsuarios);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                usuario = new Usuarios();
                usuario.setNombreUsuario(rs.getString("nombreUsuario"));
                usuario.setContrasenaUsuario(rs.getString("contrasenaUsuario"));
            }

            rs.close();
            ps.close();
            nuevaCon.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return usuario;
    }


    // Método para crear un nuevo usuario usando RegistroUsuariosDTO
    public int crearUsuario(RegistroUsuariosDTO registroDTO) {
        String sql = "INSERT INTO usuarios (nombreUsuario, contrasenaUsuario) VALUES (?, ?)";
        int idGenerado = -1;

        try {
            Conexion miconexion = new Conexion();
            Connection nuevaCon = miconexion.getConn();
            PreparedStatement ps = nuevaCon.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, registroDTO.getNombreUsuario());
            ps.setString(2, registroDTO.getContrasenaUsuario());

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                var rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                }
            }

            ps.close();
            nuevaCon.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idGenerado;
    }
}

