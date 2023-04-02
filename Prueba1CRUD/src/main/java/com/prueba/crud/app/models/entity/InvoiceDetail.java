package com.prueba.crud.app.models.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "invoices_detail")
public class InvoiceDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")// asi se va a llamar el fk
    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double calculateAmount(){
        return amount.doubleValue() * product.getPrice();
    }

    private static final long serialVersionUID = 1L;
}
