package com.bolsadeideas.springboot.app.models.service;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.entity.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IClienteService {
    public List<Cliente> findAll();

    //Paginacion
    public Page<Cliente> findAll(Pageable pageable);

    public void save(Cliente cliente);

    public Cliente findOne(Long id);

    public void delete(Long id);

    //Metodo para autocomplte producto
    public List<Producto> findByNombre(String term);

}
