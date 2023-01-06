package com.bolsadeideas.springboot.app.dao;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import org.springframework.data.repository.CrudRepository;



public interface IClienteDao extends CrudRepository<Cliente, Long> {


}
