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
    public List<Persona> listarClientes(Boolean estado) {
        List<Persona> listaClientes = new ArrayList<>();
        Conexion miconexion = new Conexion();
        Connection nuevaCon = miconexion.getConn();

        try {
            String querySQL = "SELECT p.idPersona, p.numeroIdentificacion, p.nombres, p.apellidos, "
                    + "p.telefono, p.correo, p.direccion, "
                    + "p.TipoIdentificacion_idTipoIdentificacion, ti.descripcionTipoIdentificacion, "
                    + "p.Roles_idRoles, r.descripcionRol, p.estado "
                    + "FROM persona p "
                    + "INNER JOIN tipoidentificacion ti ON p.TipoIdentificacion_idTipoIdentificacion = ti.idTipoIdentificacion "
                    + "INNER JOIN roles r ON p.Roles_idRoles = r.idRoles "
                    + "WHERE r.descripcionRol = 'Cliente'";

            if (estado != null) {
                querySQL += " AND p.estado = ?";
            }

            PreparedStatement sentencia = nuevaCon.prepareStatement(querySQL);

            if (estado != null) {
                sentencia.setBoolean(1, estado);
            }

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
                persona.setEstado(rs.getBoolean("estado"));

                listaClientes.add(persona);
            }

        } catch (Exception ex) {
            System.err.println("Error al listar clientes: " + ex.getMessage());
        }
        System.out.println("Total clientes activos: " + listaClientes.size());

        return listaClientes;
    }

    //regsistrar cliente nuevo en la bd 
    public boolean registrarClientes(Persona persona) {
        Conexion miconexion = new Conexion();
        Connection nuevaCon = miconexion.getConn();

        String sqlPersona = "INSERT INTO persona (numeroIdentificacion, nombres, apellidos, telefono, correo, direccion, TipoIdentificacion_idTipoIdentificacion, Roles_idRoles) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement psPersona = nuevaCon.prepareStatement(sqlPersona);
            psPersona.setInt(1, persona.getNumeroIdentificacion());
            psPersona.setString(2, persona.getNombres());
            psPersona.setString(3, persona.getApellidos());
            psPersona.setString(4, persona.getTelefono());
            psPersona.setString(5, persona.getCorreo());
            psPersona.setString(6, persona.getDireccion());
            psPersona.setInt(7, persona.getTipoIdentificacion_idTipoIdentificacion());
            psPersona.setInt(8, 9); // Rol fijo: Cliente

            psPersona.executeUpdate();
            psPersona.close();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    //deshabilitar el cliente para que no apareaca dentro de la lista de clientes

    public boolean deshabilitarCliente(int idPersona) {
        Conexion miconexion = new Conexion();
        Connection nuevaCon = miconexion.getConn();

        String sql = "UPDATE persona SET estado = FALSE WHERE idPersona = ?";

        try {
            PreparedStatement ps = nuevaCon.prepareStatement(sql);
            ps.setInt(1, idPersona);
            int filas = ps.executeUpdate();
            ps.close();
            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean activarCliente(int idPersona) {
        Conexion miconexion = new Conexion();
        Connection nuevaCon = miconexion.getConn();

        String sql = "UPDATE persona SET estado = true WHERE idPersona = ?";

        try {
            PreparedStatement ps = nuevaCon.prepareStatement(sql);
            ps.setInt(1, idPersona);
            int filas = ps.executeUpdate();
            ps.close();

            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    //obtenr el id para cargar datos en la vista de editar
    public Persona obtenerClientePorId(int idPersona) {
    Persona persona = null;
    Conexion miconexion = new Conexion();
    Connection nuevaCon = miconexion.getConn();

    String querySQL = "SELECT p.idPersona, p.numeroIdentificacion, p.nombres, p.apellidos, "
                    + "p.telefono, p.correo, p.direccion, "
                    + "p.TipoIdentificacion_idTipoIdentificacion, ti.descripcionTipoIdentificacion, "
                    + "p.Roles_idRoles, r.descripcionRol, p.estado "
                    + "FROM persona p "
                    + "INNER JOIN tipoidentificacion ti ON p.TipoIdentificacion_idTipoIdentificacion = ti.idTipoIdentificacion "
                    + "INNER JOIN roles r ON p.Roles_idRoles = r.idRoles "
                    + "WHERE p.idPersona = ?";

    try {
        PreparedStatement stmt = nuevaCon.prepareStatement(querySQL);
        stmt.setInt(1, idPersona);
        ResultSet rs = stmt.executeQuery();

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
            persona.setDescripcionTipoIdentificacion(rs.getString("descripcionTipoIdentificacion"));
            persona.setRoles_idRoles(rs.getInt("Roles_idRoles"));
            persona.setDescripcionRol(rs.getString("descripcionRol"));
            persona.setEstado(rs.getBoolean("estado"));
        }

    } catch (Exception ex) {
        System.err.println("Error al obtener cliente por ID: " + ex.getMessage());
    }

    return persona;
}

public boolean actualizarPersona(Persona persona) {
    boolean actualizado = false;
    Connection conn = null;
    PreparedStatement ps = null;

    try {
        conn = new Conexion().getConn();
        String sql = "UPDATE persona SET nombres = ?, apellidos = ?, correo = ?, direccion = ?, tipo_identificacion_idTipoIdentificacion = ? WHERE idPersona = ?";
        ps = conn.prepareStatement(sql);
        ps.setString(1, persona.getNombres());
        ps.setString(2, persona.getApellidos());
        ps.setString(3, persona.getCorreo());
        ps.setString(4, persona.getDireccion());
        ps.setInt(5, persona.getTipoIdentificacion_idTipoIdentificacion());
        ps.setInt(6, persona.getIdPersona());

        actualizado = ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try { if (ps != null) ps.close(); } catch (Exception e) {}
        try { if (conn != null) conn.close(); } catch (Exception e) {}
    }

    return actualizado;
}
//actualizar el cliente en la bd
public boolean actualizarCliente(Persona persona) {
    boolean actualizado = false;
    Connection conn = null;
    PreparedStatement ps = null;

    try {
        conn = new Conexion().getConn();
        String sql = "UPDATE persona SET tipoIdentificacion_idTipoIdentificacion = ?, numeroIdentificacion = ?, nombres = ?, apellidos = ?, telefono = ?, correo = ?, direccion = ? WHERE idPersona = ?";
        ps = conn.prepareStatement(sql);
        ps.setInt(1, persona.getTipoIdentificacion_idTipoIdentificacion());
        ps.setInt(2, persona.getNumeroIdentificacion());
        ps.setString(3, persona.getNombres());
        ps.setString(4, persona.getApellidos());
        ps.setString(5, persona.getTelefono());
        ps.setString(6, persona.getCorreo());
        ps.setString(7, persona.getDireccion());
        ps.setInt(8, persona.getIdPersona());

        actualizado = ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    return actualizado;
}
// para la tabla de pedios 
public Persona obtenerPersonaPorNumeroIdentificacion(int numeroIdentificacion) {
    Conexion miconexion = new Conexion();
    Connection nuevaCon = miconexion.getConn();
    Persona persona = null;

    String sql = "SELECT * FROM persona WHERE numeroIdentificacion = ?";

    try {
        PreparedStatement ps = nuevaCon.prepareStatement(sql);
        ps.setInt(1, numeroIdentificacion);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            // Verificar el rol de la persona
            int rolId = rs.getInt("Roles_idRoles");
            if (rolId == 9) {
                // Si tiene el rol 9, cargamos la persona
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
            } else {
                // Si no tiene el rol 9, no devolvemos la persona
                persona = null;
            }
        }

        rs.close();
        ps.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return persona;
}



}
