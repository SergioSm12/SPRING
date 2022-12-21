package com.bolsadeideas.springboot.app.springbootweb.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bolsadeideas.springboot.app.springbootweb.models.Usuario;

@Controller
@RequestMapping("/app") // ruta de primer nivel 8080/app/index
public class IndexController {

    @GetMapping({ "/index", "/" })
    public String index(Model model) {
        model.addAttribute("titulo", "Hola spring framework");
        return "index";
    }

    @RequestMapping("/perfil")
    public String perfil(Model model) {
        Usuario usuario = new Usuario();
        usuario.setNombre("Sergio");
        usuario.setApellido("Mesa");
        usuario.setEmail("davidmesab7@gmail.com");
        model.addAttribute("usuario", usuario);
        model.addAttribute("titulo", "Perfil del usuario : ".concat(usuario.getNombre()));
        return "perfil";
    }

    @RequestMapping("/listar")
    public String listar(Model model) {

        model.addAttribute("titulo", "Listado de usuarios");

        return "listar";

    }

    @ModelAttribute("usuarios")// pasar datos a la vista es comun para cada una del controlador.
    public List<Usuario> poblarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(new Usuario("Andres", "Guzman", "andres@gmail.com"));
        usuarios.add(new Usuario("John", "Doe", "john@gmail.com"));
        usuarios.add(new Usuario("Jane", "Doe", "jane@gmail.com"));
        usuarios.add(new Usuario("Tornado", "doe", "doe@correo.com"));

        return usuarios;
    }
}
