package com.bolsadeideas.springboot.reactor.app;

import com.bolsadeideas.springboot.reactor.app.models.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(SpringBootReactorApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootReactorApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<String> usuariosList = new ArrayList<>();
        usuariosList.add("Sergio Mesa");
        usuariosList.add("David Buitrago");
        usuariosList.add("Miguel Buitrago");
        usuariosList.add("Santiago Mesa");
        usuariosList.add("Bruce Lee");
        usuariosList.add("Bruce willis");
        Flux<String> nombres = Flux.fromIterable(usuariosList);/*Flux.just("Sergio Mesa", "David Buitrago", "Miguel Buitrago", "Santiago Mesa", "Bruce Lee", "Bruce willis");*/
    Flux<Usuario> usuarios = nombres.map(nombre -> new Usuario(nombre.split(" ")[0].toUpperCase(), nombre.split(" ")[1].toUpperCase()))
                .filter(usuario -> usuario.getNombre().toLowerCase().equals("bruce"))
                .doOnNext(usuario -> {
                    if (usuario == null) {
                        throw new RuntimeException("Nombres no pueden ser vacíos");
                    }
                    System.out.println(usuario.getNombre().concat(" ").concat(usuario.getApellido()));
                }).map(usuario -> {
                    String nombre = usuario.getNombre().toLowerCase();
                    usuario.setNombre(nombre);
                    return usuario;
                });

        usuarios.subscribe(e -> log.info(e.toString()), error -> log.error(error.getMessage()),
                new Runnable() {
                    @Override
                    public void run() {
                        log.info("Ha finalizado la ejecucion del observable con exito");
                    }
                });
    }
}
