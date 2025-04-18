package Controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PedidoCabeceraDAO {

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

}
