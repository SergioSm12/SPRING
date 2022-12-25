package com.bolsadeideas.springboot.app.springbootdi.Model.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component
public class Factura {

    @Value("${factura.descripcion}")
    private String descripcion;
    @Autowired
    private Cliente cliente;
    @Autowired
    @Qualifier("itemsFacturaOficina")
    private List<itemFactura> items;

    @PostConstruct // Despues de que el objeto se construya.
    public void inicializar() {

        cliente.setNombre(cliente.getNombre().concat(" ").concat("Jose"));
        descripcion = descripcion.concat(" del cliente ").concat(cliente.getNombre());

    }

    @PreDestroy
    public void destruir() {
        System.out.println("Factura destruida : ".concat(descripcion));
    }

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
