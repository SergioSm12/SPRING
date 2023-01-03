package com.bolsadeideas.springboot.form.app.controllers;

import com.bolsadeideas.springboot.form.app.models.domain.usuario;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.HashMap;
import java.util.Map;

@Controller
public class FormController {

    @GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("titulo", "Formulario usuarios");
        return "form";
    }

    @PostMapping("/form")
    public String procesar(@Valid usuario usuario, BindingResult result, Model model) {
        model.addAttribute("titulo", "Resultado del form enviado desde el post");

        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err -> {
                errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
            });
            model.addAttribute("error", errores);
            return "form";
        }
        model.addAttribute("usuario", usuario);
        return "resultado";
    }
}
