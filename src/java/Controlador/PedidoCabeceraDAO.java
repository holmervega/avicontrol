package Controlador;

import Modelo.PedidoCabecera;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PedidoCabeceraDAO {

//obtener sigueinte numero de pedido para mostrar en el formulario de registro
    public int obtenerSiguienteNumeroPedido() {
        int numero = 1; // valor por defecto si no hay registros
        Conexion miconexion = new Conexion();
        Connection nuevaCon = miconexion.getConn();

        String sql = "SELECT MAX(numeroPedido) FROM pedidocabecera";
        try {
            PreparedStatement ps = nuevaCon.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int max = rs.getInt(1);
                if (!rs.wasNull()) {
                    numero = max + 1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numero;
    }

    //metodo para registrar la cabeceda del pedido   
    public boolean registrarPedidoCabecera(PedidoCabecera pedido) {
        boolean registrado = false;
        String sql = "INSERT INTO pedidocabecera (numeroPedido, fechaPedido, Persona_idPersona) VALUES (?, ?, ?)";

        Conexion con = new Conexion();

        try (
                Connection conn = con.getConn(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, pedido.getNumeroPedido());
            ps.setString(2, pedido.getFechaPedido());
            ps.setInt(3, pedido.getPersona_idPersona());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        pedido.setIdPedidoCabecera(rs.getInt(1));
                    }
                }
                registrado = true;
            }
        } catch (Exception e) {
            System.out.println("Error al registrar pedido cabecera: " + e.getMessage());
        }

        return registrado;
    }

}
