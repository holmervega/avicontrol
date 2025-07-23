package Modelo;

public class TipoIdentificacion {

    private int idTipoIdentificacion;
    private String descripcionTipoIdentificacion;
    private String descripcion;

    // ✅ Agregar este constructor para solucionar el error
    public TipoIdentificacion(int idTipoIdentificacion, String descripcion) {
        this.idTipoIdentificacion = idTipoIdentificacion;
        this.descripcion = descripcion;
    }

    // 🔥 Agregar constructor vacío para evitar errores
    public TipoIdentificacion() {

    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdTipoIdentificacion() {
        return idTipoIdentificacion;
    }

    public void setIdTipoIdentificacion(int idTipoIdentificacion) {
        this.idTipoIdentificacion = idTipoIdentificacion;
    }

    public String getDescripcionTipoIdentificacion() {
        return descripcionTipoIdentificacion;
    }

    public void setDescripcionTipoIdentificacion(String descripcionTipoIdentificacion) {
        this.descripcionTipoIdentificacion = descripcionTipoIdentificacion;
    }

}
