package com.bolsadeideas.springboot.app.springbootdi.Model.domain;

public class itemFactura {
    private Producto producto;
    private Integer cantidad;

    public itemFactura(Producto producto, Integer cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer calcularImporte(){
        return cantidad *producto.getPrecio();
    }

}
