package com.prueba.crud.app.models.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "invoices")
public class Invoice implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private String observation;

    @Temporal(TemporalType.DATE)
    @Column(name = "create_at")
    private Date createAt;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_id")//crea el campo en item
    private List<InvoiceDetail> items;

    @PrePersist
    public void prePersist() {
        createAt = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<InvoiceDetail> getItems() {
        return items;
    }

    public void setItems(List<InvoiceDetail> items) {
        this.items = items;
    }

    //añadir details
    public void addDetailInvoice(InvoiceDetail item) {
        this.items.add(item);
    }

    //suma total
    public Double getTotal() {
        Double total = 0.0;
        int size = items.size();

        for (int i = 0; i < size; i++) {
            total += items.get(i).calculateAmount();
        }
        return total;

    }

    private static final long serialVersionUID = 1L;
}
