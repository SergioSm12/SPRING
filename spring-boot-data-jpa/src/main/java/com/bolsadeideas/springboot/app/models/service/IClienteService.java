package com.bolsadeideas.springboot.app.models.service;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.entity.Factura;
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

    //firma del metodo para optimizar consulta cliente factura ver detalle del cliente
    public Cliente fetchByIdWithFacturas(Long id);

    public void delete(Long id);

    //Metodo para autocomplte producto
    public List<Producto> findByNombre(String term);

    public void saveFactura(Factura factura);

    public Producto findProductoById(Long id);

    public Factura findFacturaById(Long id);

    public void deleteFactura(Long id);

    //firma del metodo para optimizar consultas al ver detalle factura
    public Factura fetchFacturaByIdWithClienteWithItemFacturaWithProducto(Long id);
}
