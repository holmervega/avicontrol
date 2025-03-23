package Pruebas;

import Controlador.PersonaDAO;
import Controlador.RolesDAO;
import Controlador.UsuariosDAO;
import Controlador.TipoIdentificacionDAO;
import Modelo.Persona;
import Modelo.Usuarios;
import java.util.List;

public class ListarPersonas {

    public static void main(String[] args) {
        PersonaDAO personaDAO = new PersonaDAO();
        RolesDAO rolesDAO = new RolesDAO();
        UsuariosDAO usuariosDAO = new UsuariosDAO();
        TipoIdentificacionDAO tipoIdentificacionDAO = new TipoIdentificacionDAO(); // Nuevo DAO agregado

        List<Persona> listaPersonas = personaDAO.listarPersonas();

        if (listaPersonas.isEmpty()) {
            System.out.println("No se encontraron personas en la base de datos.");
        } else {
            System.out.println("Lista de personas registradas:");
            for (Persona p : listaPersonas) {
                // Obtener la descripción del rol
                String descripcionRol = rolesDAO.obtenerDescripcionporid(p.getRoles_idRoles());

                // Obtener la descripción del tipo de identificación
                String descripcionTipoIdentificacion = tipoIdentificacionDAO.obtenerDescripcionTipoIdentificacionpoId(p.getTipoIdentificacion_idTipoIdentificacion());

                // Obtener el usuario asociado
                Usuarios usuario = usuariosDAO.obtenerUsuarioPorId(p.getUsuarios_idUsuarios());
                String nombreUsuario = (usuario != null) ? usuario.getNombreUsuario() : "N/A";
                String contrasenaUsuario = (usuario != null) ? usuario.getContrasenaUsuario() : "N/A";

                System.out.println("Tipo Identificación: " + descripcionTipoIdentificacion +  // Se reemplaza el ID por la descripción
                        ", Número de Identificación: " + p.getNumeroIdentificacion() +
                        ", Nombre: " + p.getNombres() +
                        ", Apellido: " + p.getApellidos() +
                        ", Teléfono: " + p.getTelefono() +
                        ", Correo: " + p.getCorreo() +
                        ", Dirección: " + p.getDireccion() +
                        ", Rol: " + descripcionRol +
                        ", Nombre de Usuario: " + nombreUsuario +
                        ", Contraseña: " + contrasenaUsuario);
            }
        }
    }
}
