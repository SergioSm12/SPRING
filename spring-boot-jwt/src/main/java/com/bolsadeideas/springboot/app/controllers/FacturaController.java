package com.bolsadeideas.springboot.app.controllers;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.entity.Factura;
import com.bolsadeideas.springboot.app.models.entity.ItemFactura;
import com.bolsadeideas.springboot.app.models.entity.Producto;
import com.bolsadeideas.springboot.app.models.service.ClienteService;
import com.bolsadeideas.springboot.app.models.service.IClienteService;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Locale;
import java.util.Map;
//Anotacion para habilitar sguridad
@Secured("ROLE_ADMIN")
@Controller
@RequestMapping("/factura")//ruta de primer nivel
@SessionAttributes("factura")
public class FacturaController {
    @Autowired
    private IClienteService clienteService;

    @Autowired
    private MessageSource messageSource;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable Long id, Model model, RedirectAttributes flash, Locale locale) {
        // Factura factura = clienteService.findFacturaById(id);

        //Consulta obtimizada

        Factura factura = clienteService.fetchFacturaByIdWithClienteWithItemFacturaWithProducto(id);
        if (factura == null) {
            flash.addFlashAttribute("error", messageSource.getMessage("text.factura.flash.db.error", null, locale));
            return "redirect:/listar";
        }
        model.addAttribute("factura", factura);
        model.addAttribute("titulo", messageSource.getMessage("text.factura.ver.titulo",null,locale).concat(factura.getDescripcion()));


        return "factura/ver";
    }

    @GetMapping("/form/{clienteId}")
    public String crear(@PathVariable(value = "clienteId") Long clienteId, Map<String, Object> model, RedirectAttributes flash, Locale locale) {
        Cliente cliente = clienteService.findOne(clienteId);
        if (cliente == null) {
            flash.addFlashAttribute("error", messageSource.getMessage("text.cliente.flash.db.error", null, locale));
            return "redirect:/listar";
        }

        Factura factura = new Factura();
        factura.setCliente(cliente);

        model.put("factura", factura);
        model.put("titulo", messageSource.getMessage("text.factura.form.titulo",null,locale));

        return "factura/form";
    }

    //produces especifica la salida en este caso es de tipo json
    @GetMapping(value = "/cargar-productos/{term}", produces = {"application/json"})
    //Response body transaforma la salida en json sobreescribe la salida a thymeleaf
    public @ResponseBody List<Producto> cargarProductos(@PathVariable String term) {
        return clienteService.findByNombre(term);
    }

    //Persistir Factura (Guardar)
    @PostMapping("/form")
    public String guardar(@Valid Factura factura, BindingResult result, Model model,
                          @RequestParam(name = "item_id[]", required = false) Long[] itemId,
                          @RequestParam(name = "cantidad[]", required = false) Integer[] cantidad,
                          RedirectAttributes flash,
                          SessionStatus status, Locale locale) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", messageSource.getMessage("text.factura.form.titulo",null,locale));
            return "factura/form";
        }

        if (itemId == null || itemId.length == 0) {
            model.addAttribute("titulo", messageSource.getMessage("text.factura.form.titulo",null,locale));
            model.addAttribute("error", messageSource.getMessage("text.factura.flash.lineas.error",null,locale));
            return "factura/form";
        }

        for (int i = 0; i < itemId.length; i++) {
            Producto producto = clienteService.findProductoById(itemId[i]);

            //Despues de recibir los parametros los recibimos y creamos el objeto item factura que esta compuesto por la cantidad y el producto.
            ItemFactura linea = new ItemFactura();
            linea.setCantidad(cantidad[i]);
            linea.setProducto(producto);
            //Agregamos el item dentro del arreglo items facturas
            factura.addItemFactura(linea);

            //logger para ver el resultado en consola
            log.info("ID: " + itemId[i].toString() + ", cantidad: " + cantidad[i].toString());
        }

        clienteService.saveFactura(factura);
        status.setComplete();
        flash.addFlashAttribute("success", messageSource.getMessage("text.factura.flash.crear.success",null,locale));
        return "redirect:/ver/" + factura.getCliente().getId();

    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash, Locale locale) {
        Factura factura = clienteService.findFacturaById(id);

        if (factura != null) {
            clienteService.deleteFactura(id);
            flash.addFlashAttribute("success", messageSource.getMessage("text.factura.flash.eliminar.success", null,locale));
            return "redirect:/ver/" + factura.getCliente().getId();
        }
        flash.addFlashAttribute(messageSource.getMessage("text.factura.flash.db.error",null,locale));
        return "redirect:/listar";
    }
}
