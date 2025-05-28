package Controlador;

import Modelo.Persona;
import Modelo.Usuarios;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

public class UsuariosDAO {
    // validar el ususario para el login

public Persona ValidarUsuarioLogin(String nombreUsuario, String contrasenaUsuario) {
    Persona persona = null;

    Conexion miconexion = new Conexion();
    Connection nuevaCon = miconexion.getConn();

    try {
        String querySQL = "SELECT p.idPersona, p.numeroIdentificacion, p.nombres, p.apellidos, " +
                          "p.telefono, p.correo, p.direccion, p.TipoIdentificacion_idTipoIdentificacion, " +
                          "r.idRoles, r.descripcionRol AS rolDescripcion " +
                          "FROM persona p " +
                          "INNER JOIN usuarios u ON p.Usuarios_idUsuarios = u.idUsuarios " + // Aquí usamos el nombre correcto
                          "INNER JOIN roles r ON p.Roles_idRoles = r.idRoles " + // Relacionamos con la tabla roles
                          "WHERE u.nombreUsuario = ? AND u.contrasenaUsuario = ?";

        PreparedStatement sentencia = nuevaCon.prepareStatement(querySQL);
        sentencia.setString(1, nombreUsuario);
        sentencia.setString(2, contrasenaUsuario);
        ResultSet rs = sentencia.executeQuery();

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
            persona.setRoles_idRoles(rs.getInt("idRoles"));
            persona.setDescripcionRol(rs.getString("rolDescripcion"));
        }

    } catch (Exception ex) {
        System.err.println("Error al validar Usuario: " + ex.getMessage());
    }

    return persona;

}


    //cargar datos de los ususarios registrados en la vista de usuarios.jsp
    public List<Usuarios> listarUsuarios() {
        List<Usuarios> listaUsuarios = new ArrayList<>();
        Conexion miconexion = new Conexion();
        Connection nuevaCon = miconexion.getConn();

        try {
            String querySQL = "SELECT u.idUsuarios, u.nombreUsuario, u.contrasenaUsuario, "
                    + "p.idPersona, p.numeroIdentificacion, p.nombres, p.apellidos, "
                    + "p.telefono, p.correo, p.direccion, "
                    + "p.TipoIdentificacion_idTipoIdentificacion, ti.descripcionTipoIdentificacion, "
                    + "p.Roles_idRoles, r.descripcionRol "
                    + "FROM Usuarios u "
                    + "INNER JOIN persona p ON u.idUsuarios = p.Usuarios_idUsuarios "
                    + "INNER JOIN tipoidentificacion ti ON p.TipoIdentificacion_idTipoIdentificacion = ti.idTipoIdentificacion "
                    + "INNER JOIN roles r ON p.Roles_idRoles = r.idRoles";

            PreparedStatement sentencia = nuevaCon.prepareStatement(querySQL);
            ResultSet rs = sentencia.executeQuery();

            while (rs.next()) {
                Usuarios usuario = new Usuarios();
                usuario.setIdUsuarios(rs.getInt("idUsuarios"));
                usuario.setNombreUsuario(rs.getString("nombreUsuario"));
                usuario.setContrasenaUsuario(rs.getString("contrasenaUsuario"));

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

                usuario.setPersona(persona);
                listaUsuarios.add(usuario);
            }

        } catch (Exception ex) {
            System.err.println("Error al listar usuarios: " + ex.getMessage());
        }

        return listaUsuarios;
    }
//regsistrar usuario nuevo en la bd 

    public boolean registrarUsuario(Persona persona, Usuarios usuario) {
        Conexion miconexion = new Conexion();
        Connection nuevaCon = miconexion.getConn();

        String sqlUsuario = "INSERT INTO usuarios (nombreUsuario, contrasenaUsuario) VALUES (?, ?)";
        String sqlPersona = "INSERT INTO persona (numeroIdentificacion, nombres, apellidos, telefono, correo, direccion, TipoIdentificacion_idTipoIdentificacion, Roles_idRoles, Usuarios_idUsuarios) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            nuevaCon.setAutoCommit(false); // Iniciamos la transacción

            // Insertar usuario
            PreparedStatement psUsuario = nuevaCon.prepareStatement(sqlUsuario, PreparedStatement.RETURN_GENERATED_KEYS);
            psUsuario.setString(1, usuario.getNombreUsuario());
            psUsuario.setString(2, usuario.getContrasenaUsuario());
            psUsuario.executeUpdate();

            // Obtener el ID generado para usuario
            ResultSet rs = psUsuario.getGeneratedKeys();
            int idUsuarioGenerado = -1;
            if (rs.next()) {
                idUsuarioGenerado = rs.getInt(1);
            }
            rs.close();
            psUsuario.close();

            if (idUsuarioGenerado == -1) {
                nuevaCon.rollback();
                return false;
            }

            // Insertar persona
            PreparedStatement psPersona = nuevaCon.prepareStatement(sqlPersona);
            psPersona.setInt(1, persona.getNumeroIdentificacion());
            psPersona.setString(2, persona.getNombres());
            psPersona.setString(3, persona.getApellidos());
            psPersona.setString(4, persona.getTelefono());
            psPersona.setString(5, persona.getCorreo());
            psPersona.setString(6, persona.getDireccion());
            psPersona.setInt(7, persona.getTipoIdentificacion_idTipoIdentificacion());
            psPersona.setInt(8, persona.getRoles_idRoles());
            psPersona.setInt(9, idUsuarioGenerado);
            psPersona.executeUpdate();
            psPersona.close();

            nuevaCon.commit(); // Confirmamos la transacción
            return true;

        } catch (SQLException e) {
            try {
                nuevaCon.rollback(); // Revertimos la transacción en caso de error
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                nuevaCon.setAutoCommit(true); // Restauramos el modo por defecto
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
//obterner para la tabla de editar 

    public Usuarios obtenerUsuarioPorIdPersona(int idPersona) {
        Conexion miconexion = new Conexion();
        Connection nuevaCon = miconexion.getConn();
        Usuarios usuario = null;

        String sql = "SELECT u.idUsuarios, u.nombreUsuario, u.contrasenaUsuario "
                + "FROM usuarios u "
                + "JOIN persona p ON u.idUsuarios = p.Usuarios_idUsuarios "
                + // Cambio aquí
                "WHERE p.idPersona = ?";

        try (PreparedStatement pst = nuevaCon.prepareStatement(sql)) {
            pst.setInt(1, idPersona);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                usuario = new Usuarios();
                usuario.setIdUsuarios(rs.getInt("idUsuarios"));
                usuario.setNombreUsuario(rs.getString("nombreUsuario"));
                usuario.setContrasenaUsuario(rs.getString("contrasenaUsuario"));
            }
        } catch (SQLException e) {
            System.out.println("⚠ Error al obtener usuario por idPersona: " + e.getMessage());
        } finally {
            try {
                if (nuevaCon != null) {
                    nuevaCon.close();
                }
            } catch (SQLException e) {
                System.out.println("⚠ Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return usuario;
    }

    // metodo para actualizar el usuario en la base de datos 
    public boolean actualizarUsuario(Persona persona, Usuarios usuario) {
        Conexion miconexion = new Conexion();
        Connection nuevaCon = miconexion.getConn();
        boolean actualizado = false;

        // 📌 Ahora el rol también se actualiza en la tabla `persona`
        String sqlPersona = "UPDATE persona SET nombres = ?, apellidos = ?, direccion = ?, correo = ?, "
                + "TipoIdentificacion_idTipoIdentificacion = ?, Roles_idRoles = ?, numeroIdentificacion = ?, telefono = ? WHERE idPersona = ?";

        String sqlUsuario = "UPDATE usuarios SET nombreUsuario = ?, contrasenaUsuario = ? WHERE idUsuarios = ?";

        try {
            nuevaCon.setAutoCommit(false); // Iniciar transacción

            try (PreparedStatement pstPersona = nuevaCon.prepareStatement(sqlPersona); PreparedStatement pstUsuario = nuevaCon.prepareStatement(sqlUsuario)) {

                // 📌 Actualizar Persona (incluye Tipo de Identificación, Rol, Número de Identificación y Teléfono)
                pstPersona.setString(1, persona.getNombres());
                pstPersona.setString(2, persona.getApellidos());
                pstPersona.setString(3, persona.getDireccion());
                pstPersona.setString(4, persona.getCorreo());
                System.out.println("TipoIdentificacion en DAO: " + persona.getTipoIdentificacion());

                pstPersona.setInt(5, persona.getTipoIdentificacion().getIdTipoIdentificacion());
                pstPersona.setInt(6, persona.getRoles().getIdRoles());  // ✅ Ahora actualizamos el rol en persona
                pstPersona.setInt(7, persona.getNumeroIdentificacion());
                pstPersona.setString(8, persona.getTelefono());
                pstPersona.setInt(9, persona.getIdPersona());
                pstPersona.executeUpdate();

                // 📌 Actualizar Usuario (pero ya no actualizamos el rol aquí)
                pstUsuario.setString(1, usuario.getNombreUsuario());
                pstUsuario.setString(2, usuario.getContrasenaUsuario());
                pstUsuario.setInt(3, usuario.getIdUsuarios());
                pstUsuario.executeUpdate();

                nuevaCon.commit(); // Confirmar transacción
                actualizado = true;
            } catch (SQLException e) {
                nuevaCon.rollback(); // Revertir en caso de error
                System.out.println("⚠ Error al actualizar usuario: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("⚠ Error al manejar la transacción: " + e.getMessage());
        } finally {
            try {
                if (nuevaCon != null) {
                    nuevaCon.setAutoCommit(true);
                    nuevaCon.close();
                }
            } catch (SQLException e) {
                System.out.println("⚠ Error al cerrar la conexión: " + e.getMessage());
            }
        }

        return actualizado;
    }

    //metodo para eliminar  
public boolean eliminarUsuarioPorIdPersona(int idPersona) {
    Conexion miconexion = new Conexion();
    Connection nuevaCon = miconexion.getConn();
    
    String sqlObtenerUsuario = "SELECT Usuarios_idUsuarios FROM persona WHERE idPersona = ?";
    String sqlEliminarPersona = "DELETE FROM persona WHERE idPersona = ?";
    String sqlEliminarUsuario = "DELETE FROM usuarios WHERE idUsuarios = ?";
    
    try {
        nuevaCon.setAutoCommit(false); // Iniciar transacción

        int idUsuario = -1;

        // 📌 Obtener el ID del usuario asociado a la persona
        try (PreparedStatement pstObtener = nuevaCon.prepareStatement(sqlObtenerUsuario)) {
            pstObtener.setInt(1, idPersona);
            try (ResultSet rs = pstObtener.executeQuery()) {
                if (rs.next()) {
                    idUsuario = rs.getInt("Usuarios_idUsuarios");
                }
            }
        }

        if (idUsuario == -1) {
            System.out.println("⚠ No se encontró usuario asociado a la persona con ID: " + idPersona);
            nuevaCon.rollback();
            return false;
        }

        // 📌 Eliminar la persona primero
        try (PreparedStatement pstPersona = nuevaCon.prepareStatement(sqlEliminarPersona)) {
            pstPersona.setInt(1, idPersona);
            pstPersona.executeUpdate();
        }

        // 📌 Luego eliminar el usuario
        try (PreparedStatement pstUsuario = nuevaCon.prepareStatement(sqlEliminarUsuario)) {
            pstUsuario.setInt(1, idUsuario);
            pstUsuario.executeUpdate();
        }

        nuevaCon.commit(); // Confirmar cambios
        return true;

    } catch (SQLException e) {
        try {
            nuevaCon.rollback(); // Revertir cambios en caso de error
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        e.printStackTrace();
        return false;
    } finally {
        try {
            nuevaCon.setAutoCommit(true);
            nuevaCon.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


}
