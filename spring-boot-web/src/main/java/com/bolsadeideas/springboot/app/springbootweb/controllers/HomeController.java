package com.bolsadeideas.springboot.app.springbootweb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {

        //redirecciona cambiando la ruta 
        //return "redirect:https://www.google.com";
        // redireccion sin cambiar la ruta
        return "forward:/app/index";

    }
}
