package Modelo;

public class PedidoCabecera {

    private int idPedidoCabecera;
    private int numeroPedido;
    private String fechaPedido;
    private int Persona_idPersona;
    private String nombrePersona;
    private String apellidoPersona;
    private String numeroIdentificacion;

    public String getNombrePersona() {
        return nombrePersona;
    }

    public void setNombrePersona(String nombrePersona) {
        this.nombrePersona = nombrePersona;
    }

    public String getApellidoPersona() {
        return apellidoPersona;
    }

    public void setApellidoPersona(String apellidoPersona) {
        this.apellidoPersona = apellidoPersona;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public int getIdPedidoCabecera() {
        return idPedidoCabecera;
    }

    public void setIdPedidoCabecera(int idPedidoCabecera) {
        this.idPedidoCabecera = idPedidoCabecera;
    }

    public int getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(int numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public String getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(String fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public int getPersona_idPersona() {
        return Persona_idPersona;
    }

    public void setPersona_idPersona(int Persona_idPersona) {
        this.Persona_idPersona = Persona_idPersona;
    }
}
