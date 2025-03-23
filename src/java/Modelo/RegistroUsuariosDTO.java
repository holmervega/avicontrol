package Modelo;

public class RegistroUsuariosDTO {

    public int getIdUsuarios() {
        return idUsuarios;
    }

    public void setIdUsuarios(int idUsuarios) {
        this.idUsuarios = idUsuarios;
    }
    private int idTipoIdentificacion;
    private int numeroIdentificacion;
    private String nombres;
    private int idUsuarios;
    private String apellidos;
    private String telefono;
    private String correo;
    private String direccion;
    private int idRol;
    private String nombreUsuario;
    private String contrasenaUsuario;

    // Constructor
    public RegistroUsuariosDTO(int idTipoIdentificacion, int numeroIdentificacion, String nombres, String apellidos,
                               String telefono, String correo, String direccion, int idRol, String nombreUsuario,
                               String contrasenaUsuario) {
        this.idTipoIdentificacion = idTipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        this.idRol = idRol;
        this.nombreUsuario = nombreUsuario;
        this.contrasenaUsuario = contrasenaUsuario;
    }

    // Getters y Setters
    public int getIdTipoIdentificacion() { return idTipoIdentificacion; }
    public void setIdTipoIdentificacion(int idTipoIdentificacion) { this.idTipoIdentificacion = idTipoIdentificacion; }

    public int getNumeroIdentificacion() { return numeroIdentificacion; }
    public void setNumeroIdentificacion(int numeroIdentificacion) { this.numeroIdentificacion = numeroIdentificacion; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public int getIdRol() { return idRol; }
    public void setIdRol(int idRol) { this.idRol = idRol; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getContrasenaUsuario() { return contrasenaUsuario; }
    public void setContrasenaUsuario(String contrasenaUsuario) { this.contrasenaUsuario = contrasenaUsuario; }
}
