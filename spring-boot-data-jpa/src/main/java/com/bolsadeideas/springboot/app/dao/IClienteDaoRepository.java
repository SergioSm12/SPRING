package com.bolsadeideas.springboot.app.dao;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IClienteDaoRepository extends CrudRepository<Cliente, Long> {

    //Query para optimizar consultas cuando se selecciona ver detalle del cliente
    @Query("select  c from Cliente c left join  fetch c.facturas f where c.id=?1")
    public Cliente fetchByIdWithFacturas(Long id);
}
