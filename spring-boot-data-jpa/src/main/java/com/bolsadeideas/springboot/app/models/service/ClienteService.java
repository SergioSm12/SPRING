package com.bolsadeideas.springboot.app.models.service;

import com.bolsadeideas.springboot.app.dao.IClienteDao;
import com.bolsadeideas.springboot.app.dao.IClienteDaoRepository;
import com.bolsadeideas.springboot.app.dao.IProductoDao;
import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.entity.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService implements IClienteService {

    //inyeccion para paginacion
    @Autowired
    private IClienteDao clienteDao;

    //inyeccion de manejo de datos
    @Autowired
    private IClienteDaoRepository clienteDaoRepository;

    //inyeccion para autocomplte producto
    @Autowired
    private IProductoDao productoDao;

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        return (List<Cliente>) clienteDaoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Cliente> findAll(Pageable pageable) {
        return clienteDao.findAll(pageable);
    }

    @Override
    @Transactional
    public void save(Cliente cliente) {
        clienteDaoRepository.save(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente findOne(Long id) {
        return clienteDaoRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        clienteDaoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findByNombre(String term) {
        return productoDao.findByNombreLikeIgnoreCase("%"+term+"%");
    }
}
