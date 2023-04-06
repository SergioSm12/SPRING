package com.bolsadeideas.springboot.app.dao;

import com.bolsadeideas.springboot.app.models.entity.Factura;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IFacturaDao extends CrudRepository<Factura, Long> {

    //Optimizacion de la consulta
    @Query("select f from Factura  f join fetch f.cliente c join fetch  f.items l join fetch l.producto where f.id=?1")
    public Factura fetchByIdWithClienteWhithItemFacturaWithProducto(Long id);

}
