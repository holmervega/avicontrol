package Controlador;

import Modelo.Persona;
import Modelo.Roles;
import Modelo.TipoIdentificacion;
import Modelo.Usuarios;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
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

    //cargar datos de los ususarios registrados 
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

// cargar tipo de identificaion en el selec
    public List<TipoIdentificacion> obtenerTiposIdentificacion() {
        List<TipoIdentificacion> listaTipos = new ArrayList<>();
        Conexion miconexion = new Conexion();
        Connection nuevaCon = miconexion.getConn();

        String sql = "SELECT idTipoIdentificacion, descripcionTipoIdentificacion FROM tipoidentificacion";

        try {
            PreparedStatement ps = nuevaCon.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TipoIdentificacion tipo = new TipoIdentificacion();
                tipo.setIdTipoIdentificacion(rs.getInt("idTipoIdentificacion"));
                tipo.setDescripcionTipoIdentificacion(rs.getString("descripcionTipoIdentificacion"));
                listaTipos.add(tipo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaTipos;
    }

    // cargar roles en el selc 
    public List<Roles> obtenerRoles() {
        List<Roles> listaRoles = new ArrayList<>();
        Conexion miconexion = new Conexion();
        Connection nuevaCon = miconexion.getConn();

        String sql = "SELECT idRoles, descripcionRol FROM roles";

        try {
            PreparedStatement ps = nuevaCon.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Roles rol = new Roles();
                rol.setIdRoles(rs.getInt("idRoles"));
                rol.setDescripcionRol(rs.getString("descripcionRol"));
                listaRoles.add(rol);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaRoles;
    }

    // crear usuario 
    public boolean registrarUsuario(Persona persona, Usuarios usuarios) {
        boolean registrado = false;
        Conexion miconexion = new Conexion();
        Connection conn = miconexion.getConn();

        try {
            conn.setAutoCommit(false); // Iniciar transacción

            // ️⃣ Insertar usuario en la tabla `usuarios`
            String sqlUsuario = "INSERT INTO usuarios (nombreUsuario, contrasenaUsuario) VALUES (?, ?)";
            PreparedStatement psUsuario = conn.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS);
            psUsuario.setString(1, usuarios.getNombreUsuario());
            psUsuario.setString(2, usuarios.getContrasenaUsuario());
            psUsuario.executeUpdate();

            // Obtener el ID generado
            ResultSet rs = psUsuario.getGeneratedKeys();
            int idUsuario = 0;
            if (rs.next()) {
                idUsuario = rs.getInt(1);
            }

            // 2️⃣ Insertar la persona en la tabla `persona`
            String sqlPersona = "INSERT INTO persona (numeroIdentificacion, nombres, apellidos, telefono, correo, direccion, TipoIdentificacion_idTipoIdentificacion, Roles_idRoles, Usuarios_idUsuarios) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement psPersona = conn.prepareStatement(sqlPersona);
            psPersona.setInt(1, persona.getNumeroIdentificacion());
            psPersona.setString(2, persona.getNombres());
            psPersona.setString(3, persona.getApellidos());
            psPersona.setString(4, persona.getTelefono());
            psPersona.setString(5, persona.getCorreo());
            psPersona.setString(6, persona.getDireccion());
            psPersona.setInt(7, persona.getIdTipoIdentificacion());
            psPersona.setInt(8, persona.getIdRoles());
            psPersona.setInt(9, idUsuario);
            psPersona.executeUpdate();

            conn.commit(); // Confirmar transacción
            registrado = true;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback(); // Revertir cambios en caso de error
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return registrado;
    }
// obtiene los usuarios para mostrar el el modal de verificar
    public Usuarios obtenerUsuarioPorId(int idPersona) {
        Conexion miconexion = new Conexion();
        Connection nuevaCon = miconexion.getConn();
        Usuarios usuario = null;

        try {
            String querySQL = "SELECT u.idUsuarios, u.nombreUsuario, u.contrasenaUsuario, "
                    + "p.idPersona, p.numeroIdentificacion, p.nombres, p.apellidos, "
                    + "p.telefono, p.correo, p.direccion, "
                    + "p.TipoIdentificacion_idTipoIdentificacion, ti.descripcionTipoIdentificacion, "
                    + "p.Roles_idRoles, r.descripcionRol "
                    + "FROM Usuarios u "
                    + "INNER JOIN persona p ON u.idUsuarios = p.Usuarios_idUsuarios "
                    + "INNER JOIN tipoidentificacion ti ON p.TipoIdentificacion_idTipoIdentificacion = ti.idTipoIdentificacion "
                    + "INNER JOIN roles r ON p.Roles_idRoles = r.idRoles "
                    + "WHERE p.idPersona = ?";

            PreparedStatement sentencia = nuevaCon.prepareStatement(querySQL);
            sentencia.setInt(1, idPersona);
            ResultSet rs = sentencia.executeQuery();
            

            if (rs.next()) {
                usuario = new Usuarios();
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
            }

        } catch (Exception ex) {
            System.err.println("Error al obtener usuario por ID: " + ex.getMessage());
        }

        return usuario;
    }

}
