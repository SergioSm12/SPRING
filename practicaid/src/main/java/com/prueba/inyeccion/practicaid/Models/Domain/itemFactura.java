package com.prueba.inyeccion.practicaid.Models.Domain;

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

    public String calculaPrecio() {
        String resultado = String.valueOf(cantidad * producto.getPrecio());
        return resultado;
    }

}
