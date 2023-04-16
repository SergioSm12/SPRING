package com.bolsadeideas.springboot.app.models.service;

import com.bolsadeideas.springboot.app.dao.IClienteDao;
import com.bolsadeideas.springboot.app.dao.IClienteDaoRepository;
import com.bolsadeideas.springboot.app.dao.IFacturaDao;
import com.bolsadeideas.springboot.app.dao.IProductoDao;
import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.entity.Factura;
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

    //Inyeccion para guardar factura
    @Autowired
    private IFacturaDao facturaDao;

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

    //Metodo para optimizar consulta cliente factura
    @Override
    @Transactional(readOnly = true)
    public Cliente fetchByIdWithFacturas(Long id) {
        return clienteDaoRepository.fetchByIdWithFacturas(id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        clienteDaoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findByNombre(String term) {
        return productoDao.findByNombreLikeIgnoreCase("%" + term + "%");
    }

    @Override
    @Transactional
    public void saveFactura(Factura factura) {
        facturaDao.save(factura);
    }

    @Override
    @Transactional(readOnly = true)
    public Producto findProductoById(Long id) {
        return productoDao.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Factura findFacturaById(Long id) {
        return facturaDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deleteFactura(Long id) {
        facturaDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Factura fetchFacturaByIdWithClienteWithItemFacturaWithProducto(Long id) {
        return facturaDao.fetchByIdWithClienteWhithItemFacturaWithProducto(id);
    }
}
