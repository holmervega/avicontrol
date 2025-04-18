package Modelo;

public class PedidoDetalle {

    private int idPedidoDetalle;
    private double cantidad;
    private double precioUnitario;
    private double valorPedido;
    private int PedidoCabecera_idPedidoCabecera;
    private int Productos_idProductos;
    private int Unidades_idUnidades;
    private PedidoCabecera pedidoCabecera;
    private Productos producto;
    private Unidades unidad;

    public Productos getProducto() {
        return producto;
    }

    public void setProducto(Productos producto) {
        this.producto = producto;
    }

    public Unidades getUnidad() {
        return unidad;
    }

    public void setUnidad(Unidades unidad) {
        this.unidad = unidad;
    }

    public PedidoCabecera getPedidoCabecera() {
        return pedidoCabecera;
    }

    public void setPedidoCabecera(PedidoCabecera pedidoCabecera) {
        this.pedidoCabecera = pedidoCabecera;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getValorPedido() {
        return valorPedido;
    }

    public void setValorPedido(double valorPedido) {
        this.valorPedido = valorPedido;
    }

    public int getIdPedidoDetalle() {
        return idPedidoDetalle;
    }

    public void setIdPedidoDetalle(int idPedidoDetalle) {
        this.idPedidoDetalle = idPedidoDetalle;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public int getPedidoCabecera_idPedidoCabecera() {
        return PedidoCabecera_idPedidoCabecera;
    }

    public void setPedidoCabecera_idPedidoCabecera(int PedidoCabecera_idPedidoCabecera) {
        this.PedidoCabecera_idPedidoCabecera = PedidoCabecera_idPedidoCabecera;
    }

    public int getProductos_idProductos() {
        return Productos_idProductos;
    }

    public void setProductos_idProductos(int Productos_idProductos) {
        this.Productos_idProductos = Productos_idProductos;
    }

    public int getUnidades_idUnidades() {
        return Unidades_idUnidades;
    }

    public void setUnidades_idUnidades(int Unidades_idUnidades) {
        this.Unidades_idUnidades = Unidades_idUnidades;
    }

}
