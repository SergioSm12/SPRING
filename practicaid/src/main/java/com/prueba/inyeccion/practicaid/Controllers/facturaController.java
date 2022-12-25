package com.prueba.inyeccion.practicaid.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.prueba.inyeccion.practicaid.Models.Domain.Factura;

@Controller
@RequestMapping("/factura")
public class facturaController {
    
    @Autowired
    private Factura factura;

    @GetMapping("/setup")
    public String setup(Model model){
     model.addAttribute("factura", factura);
     model.addAttribute("titulo", "Detalle de factura por inyeccion de despendencias");

     
     return "setup";

    }
}
