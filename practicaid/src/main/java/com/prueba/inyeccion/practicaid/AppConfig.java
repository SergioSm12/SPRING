package com.prueba.inyeccion.practicaid;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.prueba.inyeccion.practicaid.Models.Domain.Producto;
import com.prueba.inyeccion.practicaid.Models.Domain.itemFactura;

@Configuration
public class AppConfig {

    @Bean("Setup")
    public List<itemFactura> addItem() {
        Producto producto1 = new Producto("Monitor 4k", 2500000);
        Producto producto2 = new Producto("Teclado Asus", 500000);
        Producto producto3 = new Producto("Mouse gaming Asus", 4000000);
        Producto producto4 = new Producto("Torre Asus Gaming ", 15000000);

        itemFactura linea1 = new itemFactura(producto1, 2);
        itemFactura linea2 = new itemFactura(producto2, 1);
        itemFactura linea3 = new itemFactura(producto3, 1);
        itemFactura linea4 = new itemFactura(producto4, 1);

        return Arrays.asList(linea1, linea2, linea3, linea4);

    }

}
