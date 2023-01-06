package com.bolsadeideas.springboot.app.controllers;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.service.IClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.Map;

@Controller
@SessionAttributes("cliente")//envia el id al momento del post.
public class ClienteController {
    @Autowired
    private IClienteService clienteService;

    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public String listar(Model model) {
        model.addAttribute("titulo", "listado de clientes");
        model.addAttribute("clientes", clienteService.findAll());
        return "listar";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String crear(Map<String, Object> model) {
        Cliente cliente = new Cliente();
        model.put("cliente", cliente);
        model.put("titulo", "Formulario de cliente");
        return "form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    //bindingResult siempre debe estar al aldo del modelo
    public String guardar(@Valid Cliente cliente, BindingResult bindingResult, Model model, SessionStatus status) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("titulo", "Formulario del Cliente");
            return "form";
        }
        clienteService.save(cliente);
        status.setComplete();
        return "redirect:listar";
    }

    @GetMapping("/form/{id}")
    public String editar(@PathVariable(value = "id") Long id, Model model) {
        Cliente cliente = null;

        if (id > 0) {
            cliente = clienteService.findOne(id);

        } else {
            return "redirect:/listar";
        }
        model.addAttribute("cliente", cliente);
        model.addAttribute("titulo", "Editar Cliente");
        return "form";
    }

    @RequestMapping(value = "/eliminar/{id}", method = RequestMethod.GET)
    public String eliminar(@PathVariable(value = "id") Long id) {
        if (id > 0) {
            clienteService.delete(id);
        }
        return "redirect:/listar";
    }

}
