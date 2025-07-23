package Controlador;

import Modelo.Unidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UnidadesDAO {

    // Método para obtener todas las unidades desde la base de datos
    public List<Unidades> obtenerUnidades() {
        List<Unidades> listaUnidades = new ArrayList<>();
        String query = "SELECT * FROM unidades"; // Cambia 'unidades' por el nombre correcto de tu tabla si es diferente
        Connection nuevaCon = null;
        PreparedStatement pst = null;
        ResultSet resultSet = null;

        try {
            Conexion miconexion = new Conexion();
            nuevaCon = miconexion.getConn(); // Utilizando el método getConn() de Conexion
            pst = nuevaCon.prepareStatement(query);
            resultSet = pst.executeQuery();

            // Iteramos sobre el resultSet para extraer los datos y llenar la lista
            while (resultSet.next()) {
                Unidades unidad = new Unidades();
                unidad.setIdUnidades(resultSet.getInt("idUnidades"));
                unidad.setDescripcionUnidades(resultSet.getString("descripcionUnidades"));
                listaUnidades.add(unidad);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cierra los recursos
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (nuevaCon != null) {
                    nuevaCon.close(); // Asegúrate de cerrar la conexión aquí también
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return listaUnidades;
    }
}

