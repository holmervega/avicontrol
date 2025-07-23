package Modelo;

public class Persona {

    private int idPersona;
    private int numeroIdentificacion;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String correo;
    private String direccion;
    private int TipoIdentificacion_idTipoIdentificacion;
    private int Roles_idRoles;
    private int Usuarios_idUsuarios;
    public int IdRoles;
    private String descripcionTipoIdentificacion;
    private String descripcionRol;
    private TipoIdentificacion tipoIdentificacion;
    private Roles roles;
    private boolean estado;
    

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    } 
     
      
    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }
  


    public TipoIdentificacion getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }
     
    
     public int getIdRoles() {
        return IdRoles;
    }

    public void setIdRoles(int IdRoles) {
        this.IdRoles = IdRoles;
    }

    public String getDescripcionRol() {
        return descripcionRol;
    }

    public void setDescripcionRol(String descripcionRol) {
        this.descripcionRol = descripcionRol;
    }

    public String getDescripcionTipoIdentificacion() {
        return descripcionTipoIdentificacion;
    }

    public void setDescripcionTipoIdentificacion(String descripcionTipoIdentificacion) {
        this.descripcionTipoIdentificacion = descripcionTipoIdentificacion;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public int getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(int numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getTipoIdentificacion_idTipoIdentificacion() {
        return TipoIdentificacion_idTipoIdentificacion;
    }

    public void setTipoIdentificacion_idTipoIdentificacion(int TipoIdentificacion_idTipoIdentificacion) {
        this.TipoIdentificacion_idTipoIdentificacion = TipoIdentificacion_idTipoIdentificacion;
    }

    public int getRoles_idRoles() {
        return Roles_idRoles;
    }

    public void setRoles_idRoles(int Roles_idRoles) {
        this.Roles_idRoles = Roles_idRoles;
    }

    public int getUsuarios_idUsuarios() {
        return Usuarios_idUsuarios;
    }

    public void setUsuarios_idUsuarios(int Usuarios_idUsuarios) {
        this.Usuarios_idUsuarios = Usuarios_idUsuarios;
    }

}
