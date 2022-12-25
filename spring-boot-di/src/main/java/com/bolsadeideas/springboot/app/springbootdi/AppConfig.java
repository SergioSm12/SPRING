package com.bolsadeideas.springboot.app.springbootdi;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bolsadeideas.springboot.app.springbootdi.Model.domain.Producto;
import com.bolsadeideas.springboot.app.springbootdi.Model.domain.itemFactura;
import com.bolsadeideas.springboot.app.springbootdi.Model.service.IService;
import com.bolsadeideas.springboot.app.springbootdi.Model.service.MiServicio;
import com.bolsadeideas.springboot.app.springbootdi.Model.service.MiServicioComplejo;

@Configuration
public class AppConfig {

    @Bean("miServicioSimple")
    public IService registrarMIServicio() {
        return new MiServicio();
    }

    @Bean("miServicioComplejo")
    public IService registrarMIServicioComplejo() {
        return new MiServicioComplejo();
    }

    @Bean("itemsFactura")
    public List<itemFactura> registrarItems() {

        Producto producto1 = new Producto("Camara Sony", 150000);
        Producto producto2 = new Producto("Bicileta Bianchi", 1800000);

        itemFactura linea1 = new itemFactura(producto1, 2);
        itemFactura linea2 = new itemFactura(producto2, 4);

        return Arrays.asList(linea1, linea2);

    }

    @Bean("itemsFacturaOficina")
    public List<itemFactura> registrarItemsOficina() {

        Producto producto1 = new Producto("Monitor LG", 100000);
        Producto producto2 = new Producto("Notebook Asus", 5000000);
        Producto producto3 = new Producto("Impresora HP", 8000000);
        Producto producto4 = new Producto("Escritorio Oficina", 500000);

        itemFactura linea1 = new itemFactura(producto1, 2);
        itemFactura linea2 = new itemFactura(producto2, 1);
        itemFactura linea3 = new itemFactura(producto3, 1);
        itemFactura linea4 = new itemFactura(producto4, 1);

        return Arrays.asList(linea1, linea2, linea3, linea4);

    }
}
