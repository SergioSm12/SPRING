package com.bolsadeideas.springboot.app.dao;

import com.bolsadeideas.springboot.app.models.entity.Producto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IProductoDao extends CrudRepository<Producto,Long> {
    //Agregamos un metodo que no define crudrepository
    @Query("select p from Producto p where p.nombre like %?1%")
    public List<Producto> findByNombre(String term);

    //Otra manera de hacer la consulta
    public List<Producto> findByNombreLikeIgnoreCase(String term);

}
