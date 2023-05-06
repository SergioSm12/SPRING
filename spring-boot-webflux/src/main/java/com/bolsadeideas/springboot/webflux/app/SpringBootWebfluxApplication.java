package com.bolsadeideas.springboot.webflux.app;

import com.bolsadeideas.springboot.webflux.app.models.dao.IProductoDao;
import com.bolsadeideas.springboot.webflux.app.models.documents.Producto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;

import java.util.Date;

@SpringBootApplication
public class SpringBootWebfluxApplication implements CommandLineRunner {
    @Autowired
    private IProductoDao dao;
    @Autowired
    private ReactiveMongoTemplate mongoTemplate;
    private static final Logger log = LoggerFactory.getLogger(SpringBootWebfluxApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootWebfluxApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        mongoTemplate.dropCollection("productos").subscribe();

        Flux.just(
                        new Producto("Tv panasonic Pantalla LCD", 456.89),
                        new Producto("Sony camara HD Digital", 177.89),
                        new Producto("Apple iPod", 46.89),
                        new Producto("Sony Notebook", 846.89),
                        new Producto("Hewlett Packard Multifuncional", 200.89),
                        new Producto("Bianchi Bicicleta", 70.89),
                        new Producto("HP Notebook Oren 17", 2500.89),
                        new Producto("Mica comoda 5 cajones", 150.89),
                        new Producto("Tv Sony Bravia OLEDK Ultra HD ", 2255.89)
                ).flatMap(producto -> {
                    producto.setCreateAt(new Date());
                    return dao.save(producto);
                })
                .subscribe(producto -> log.info("Insert: " + producto.getId() + " " + producto.getNombre()));
    }
}
