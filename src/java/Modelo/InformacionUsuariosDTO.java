
package Modelo;

public class InformacionUsuariosDTO {
    private int numeroIdentificacion;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String correo;
    private String direccion;
    private String descripcionRol;
    private String nombreUsuario;
    private String contrasenaUsuario;
    private String descripcionTipoIdentificacion;

    // Constructor
   public InformacionUsuariosDTO(int numeroIdentificacion, String nombres, String apellidos, String telefono, String correo, String direccion, 
                      String descripcionRol, String nombreUsuario, String contrasenaUsuario, String descripcionTipoIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        this.descripcionRol = descripcionRol;
        this.nombreUsuario = nombreUsuario;
        this.contrasenaUsuario = contrasenaUsuario;
        this.descripcionTipoIdentificacion = descripcionTipoIdentificacion;
    }
   
   

    // Getters y Setters
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

    public String getDescripcionRol() { return descripcionRol; }
    public void setDescripcionRol(String descripcionRol) { this.descripcionRol = descripcionRol; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getContrasenaUsuario() { return contrasenaUsuario; }
    public void setContrasenaUsuario(String contrasenaUsuario) { this.contrasenaUsuario = contrasenaUsuario; }

    public String getDescripcionTipoIdentificacion() { return descripcionTipoIdentificacion; }
    public void setDescripcionTipoIdentificacion(String descripcionTipoIdentificacion) { this.descripcionTipoIdentificacion = descripcionTipoIdentificacion; }
}