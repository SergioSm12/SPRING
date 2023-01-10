package com.prueba.crud.app.controllers;

import com.prueba.crud.app.models.entity.User;
import com.prueba.crud.app.models.service.IUploadFileService;
import com.prueba.crud.app.models.service.IUserService;
import com.prueba.crud.app.models.util.paginator.PageRender;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.MalformedURLException;

@Controller
@SessionAttributes("user")
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IUploadFileService uploadFileService;

    @GetMapping(value = "/uploads/{filename:.+}")
    public ResponseEntity<Resource> showFoto(@PathVariable String filename){
        Resource recurso = null;

        try {
            recurso=uploadFileService.load(filename);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return  ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+ recurso.getFilename()+ "\"")
                .body(recurso);
    }
    @GetMapping("/ver/{id}")
    public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
        User user = userService.findById(id);
        if (user == null) {
            flash.addFlashAttribute("error", "The user does not exist in the database.");
            return "redirect:/list";
        }
        model.addAttribute("user", user);
        model.addAttribute("title", "Detail User: " + user.getName());
        return "ver";
    }

    @GetMapping("/list")
    public String list(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Pageable pageRequest = PageRequest.of(page, 8);
        Page<User> users = userService.findAll(pageRequest);

        PageRender<User> pageRender = new PageRender<>("/list", users);

        model.addAttribute("page", pageRender);
        model.addAttribute("title", "Users");
        model.addAttribute("users", users);
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
    public String save(@Valid User user, BindingResult bindingResult, Model model, @RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Form of User");
            return "form";
        }
        if (!foto.isEmpty()) {
            if (user.getId() != null && user.getId() > 0 && user.getFoto() != null && user.getFoto().length() > 0) {
                uploadFileService.delete(user.getFoto());
            }
            String uniqueFilename = null;

            try {
                uniqueFilename = uploadFileService.copy(foto);
            } catch (IOException e) {
                e.printStackTrace();
            }

            flash.addFlashAttribute("info", "Has subido correctamente '" + uniqueFilename + "'");
            user.setFoto(uniqueFilename);
        }

        String messageFlash = (user.getId() != null) ? "User Successfully edited." : "User successfully created";
        userService.save(user);
        status.setComplete();
        flash.addFlashAttribute("success", messageFlash);
        return "redirect:list";
    }

    @GetMapping("/form/{id}")
    public String edit(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
        User user = null;
        if (id > 0) {
            user = userService.findById(id);
            if (user == null) {
                flash.addFlashAttribute("error", "The user id is not in the database.");
                return "redirect:/list";
            }
        } else {
            flash.addFlashAttribute("error", "The user id cannot be zero.");
            return "redirect:/list";
        }
        model.addAttribute("user", user);
        model.addAttribute("title", "Edit User");
        return "form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
        if (id > 0) {
            User user = userService.findById(id);
            userService.delete(id);
            flash.addFlashAttribute("success", "user successfully deleted.");

            if(uploadFileService.delete(user.getFoto())){
                flash.addFlashAttribute("info", "Foto "+ user.getFoto() + "Successfully eliminated");
            }
        }
        return "redirect:/list";
    }
}
