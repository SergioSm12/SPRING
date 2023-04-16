package com.bolsadeideas.springboot.app.dao;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long> {


}
