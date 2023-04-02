package com.prueba.crud.app.dao;

import com.prueba.crud.app.models.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IProductDao extends CrudRepository<Product, Long> {
    //Add query
    @Query("select p from Product p where p.name like %?1%")
    public List<Product> findByName(String term);
}
