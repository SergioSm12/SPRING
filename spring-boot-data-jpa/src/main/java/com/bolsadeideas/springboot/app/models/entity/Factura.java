package com.bolsadeideas.springboot.app.models.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "facturas")
public class Factura implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descripcion;
    private String observacion;
    @Temporal(TemporalType.DATE)
    @Column(name = "create_at")
    private Date createAt;
    @ManyToOne(fetch = FetchType.LAZY)//muchas facturas a un cliente- lazy solo realiza consultas cuando se le llama
    private Cliente cliente;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//cascade type para que si se elimina un elemento se elimine en cascada
    //se pone por que no hay relacion en ambos sentidos
    @JoinColumn(name = "factura_id")//crea el campo factura id en itemfactura id
// se especifica el join ya que itemFactura no necesita ninguna relacion con Factura entonces no se puede por mappedby
    private List<ItemFactura> items;

    //usamos el evento prepersist para la fecha automatica
    @PrePersist
    public void prePersist() {
        createAt = new Date();
    }

    public Factura() {
        this.items = new ArrayList<ItemFactura>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ItemFactura> getItems() {
        return items;
    }

    public void setItems(List<ItemFactura> items) {
        this.items = items;
    }

    //Añadir items de factura
    public void addItemFactura(ItemFactura item) {
        this.items.add(item);
    }

    //Gran total la suma total de la factura
    public Double getTotal() {
        Double total = 0.0;
        int size = items.size();

        for (int i = 0; i < size; i++) {
            total += items.get(i).calcularImporte();
        }
        return total;
    }

    private static final long serialVersionUID = 1L;
}
