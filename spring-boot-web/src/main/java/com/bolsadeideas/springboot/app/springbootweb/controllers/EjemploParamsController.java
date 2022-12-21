package com.bolsadeideas.springboot.app.springbootweb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/params")
public class EjemploParamsController {

    @GetMapping("/")
    public String index() {
        return "params/index";
    }

    // Envio de un solo parametro
    @GetMapping("/string")
    public String param(
            @RequestParam(name = "texto", required = false, defaultValue = "valor por defecto en caso de que no se envie un parametro") String texto,
            Model model) {

        model.addAttribute("resultado", "El texto enviado es: " + texto);
        return "params/ver";

    }

    // varios parametros
    @GetMapping("/mix-params")
    public String param(@RequestParam String saludo, @RequestParam Integer numero, Model model) {

        model.addAttribute("resultado",
                "El saludo enviado es: '" + saludo + "' y el numero enviado es '" + numero + "'");
        return "params/ver";

    }

    // envio de parametros atraves de HttpServlet
    @GetMapping("/mix-params-request")
    public String param(HttpServletRequest request, Model model) {

        String saludo = request.getParameter("saludo");
        Integer numero = null;
        try {
            numero = Integer.parseInt(request.getParameter("numero"));
        } catch (NumberFormatException e) {
            numero = 0;
        }

        model.addAttribute("resultado",
                "El saludo enviado es: '" + saludo + "' y el numero enviado es '" + numero + "'");
        return "params/ver";

    }

}
