package com.prueba.crud.app.controllers;

import com.prueba.crud.app.models.entity.User;
import com.prueba.crud.app.models.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes("user")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("titulo", "list of Users");
        model.addAttribute("users", userService.findAll());
        return "list";
    }

    @GetMapping("/form")
    public String create(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("title", "Form of User");
        return "form";
    }

    @PostMapping("/form")
    public String save(@Valid User user, BindingResult bindingResult, Model model, SessionStatus status) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Form of User");
            return "form";
        }
        userService.save(user);
        status.setComplete();
        return "redirect:list";
    }

    @GetMapping("/form/{id}")
    public String edit(@PathVariable(value = "id") Long id, Model model) {
        User user = null;
        if (id > 0) {
            user = userService.findById(id);
        } else {
            return "redirect:/list";
        }
        model.addAttribute("user", user);
        model.addAttribute("title", "Edit User");
        return "form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(value = "id") Long id) {
        if (id > 0) {
            userService.delete(id);
        }
        return "redirect:/list";
    }
}
