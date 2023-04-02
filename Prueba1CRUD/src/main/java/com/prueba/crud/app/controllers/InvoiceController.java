package com.prueba.crud.app.controllers;


import com.prueba.crud.app.models.entity.Invoice;
import com.prueba.crud.app.models.entity.Product;
import com.prueba.crud.app.models.entity.User;
import com.prueba.crud.app.models.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/invoice")
@SessionAttributes("invoice")
public class InvoiceController {

    @Autowired
    private IUserService userService;

    @GetMapping("/form/{userId}")
    public String create(@PathVariable(value = "userId") Long userId, Model model, RedirectAttributes flash) {
        User user = userService.findById(userId);
        if (user == null) {
            flash.addFlashAttribute("error", "The user does not exist ");
            return "redirect:list";
        }

        Invoice invoice = new Invoice();
        invoice.setUser(user);

        model.addAttribute("invoice", invoice);
        model.addAttribute("title", " Create Invoice");

        return "invoice/form";
    }
    @GetMapping(value = "/upload-products/{term}", produces = {"application/json"})
    public @ResponseBody List<Product> uploadProducts(@PathVariable String term) {
        return userService.findByName(term);
    }

}
