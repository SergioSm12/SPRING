package com.prueba.inyeccion.practicaid.Models.Domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Factura {
    @Value("${factura.descripcion}")
    private String descripcion;

    @Autowired
    private Cliente cliente;

    @Autowired
    @Qualifier("Setup")
    private List<itemFactura> items;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<itemFactura> getItems() {
        return items;
    }

    public void setItems(List<itemFactura> items) {
        this.items = items;
    }

    

}
