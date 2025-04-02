package Modelo;

public class Roles {

    private int idRoles;
    private String descripcionRol;
    private String descripcion;
    
       public Roles(int idRoles, String descripcion) {
        this.idRoles = idRoles;
        this.descripcion = descripcion;
    }

    public Roles() {
       
    }
    

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
  

    public int getIdRoles() {
        return idRoles;
    }

    public void setIdRoles(int idRoles) {
        this.idRoles = idRoles;
    }

    public String getDescripcionRol() {
        return descripcionRol;
    }

    public void setDescripcionRol(String descripcionRol) {
        this.descripcionRol = descripcionRol;
    }

}