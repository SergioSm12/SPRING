package com.bolsadeideas.springboot.app.controllers;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.service.ClienteService;
import com.bolsadeideas.springboot.app.models.service.IClienteService;
import com.bolsadeideas.springboot.app.models.service.IUploadFileService;
import com.bolsadeideas.springboot.app.util.paginator.PageRender;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.SecurityContextDsl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.Map;


@Controller
@SessionAttributes("cliente")//envia el id al momento del post.
public class ClienteController {

    //Enviar el name de autenticacion a las vistas, pero tambien hay que poner la clase Authentication en el metodo ver listar
    protected final Log logger = LogFactory.getLog(this.getClass());
    @Autowired
    private IClienteService clienteService;
    @Autowired
    private IUploadFileService uploadFileService;

    //Anotacion para dar permisos en lugar de darolos en la clase
    @Secured("ROLE_USER")
    @GetMapping(value = "/uploads/{filename:.+}")//.+quita la extencion
    public ResponseEntity<Resource> verFoto(@PathVariable String filename) {

        Resource recurso = null;

        try {
            recurso = uploadFileService.load(filename);
        } catch (MalformedURLException e) {

            e.printStackTrace();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
                .body(recurso);
    }

    //@Secured("ROLE_USER")
    //Tambien se puede usar preauthorize
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/ver/{id}")
    public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
        //Cliente cliente = clienteService.findOne(id);
        //Consulta optimisada
        Cliente cliente = clienteService.fetchByIdWithFacturas(id);
        if (cliente == null) {
            flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
            return "redirect:/listar";
        }

        model.addAttribute("cliente", cliente);
        model.addAttribute("titulo", "Detalle cliente: " + cliente.getNombre());
        return "ver";
    }

    @RequestMapping(value = {"/listar", "/"}, method = RequestMethod.GET)
    public String listar(@RequestParam(name = "page", defaultValue = "0") int page,
                         Model model,
                         Authentication authentication,
                         HttpServletRequest request) {

        if (authentication != null) {
            //Envia el name a consola
            logger.info("Hola usuario autenticado, tu username es: ".concat(authentication.getName()));
        }

        //Traer datos de autenticaicon de manera estatica
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            //Envia el name a consola
            logger.info("Hola usuario autenticado desde la varibale estatica, tu username es: ".concat(auth.getName()));
        }
        //Uso del metodo para ver roles dentro de un controllar¿dor
        if (hasRole("ROLE_ADMIN")) {
            //Envia un mensaje a la consola del usuario
            logger.info("Hola ".concat(auth.getName()).concat(" tienes acceso!"));
        } else {

            logger.info("Hola ".concat(auth.getName()).concat(" No tienes acceso!"));

        }

        //Chequear autorizacion de otra manera
        SecurityContextHolderAwareRequestWrapper securityContext = new SecurityContextHolderAwareRequestWrapper(request, "ROLE_");
        if (securityContext.isUserInRole("ADMIN")) {
            logger.info("Forma usando SecurityContextHolderAwareRequestWrapper: Hola ".concat(auth.getName()).concat(" tienes acceso!"));
        } else {
            logger.info("Hola ".concat(auth.getName()).concat("Forma usando SecurityContextHolderAwareRequestWrapper: No tienes acceso!"));

        }

        //Chequear autorizacion(role) desde el metodo request
        if (request.isUserInRole("ROLE_ADMIN")) {
            logger.info("Forma usando HttpServletRequest: Hola ".concat(auth.getName()).concat(" tienes acceso!"));
        } else {
            logger.info("Hola ".concat(auth.getName()).concat("Forma usando HttpServletRequest: No tienes acceso!"));

        }

        //Enviar paginacion a la vista
        Pageable pageRequest = PageRequest.of(page, 4);
        Page<Cliente> clientes = clienteService.findAll(pageRequest);
        PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes);

        model.addAttribute("page", pageRender);
        model.addAttribute("titulo", "listado de clientes");
        model.addAttribute("clientes", clientes);
        return "listar";
    }
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String crear(Map<String, Object> model) {
        Cliente cliente = new Cliente();
        model.put("cliente", cliente);
        model.put("titulo", "Formulario de cliente");
        return "form";
    }
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/form", method = RequestMethod.POST)
    //bindingResult siempre debe estar al aldo de la tabla en este caso cliente
    public String guardar(@Valid Cliente cliente, BindingResult bindingResult, Model model, @RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("titulo", "Formulario del Cliente");
            return "form";
        }
        if (!foto.isEmpty()) {
            if (cliente.getId() != null && cliente.getId() > 0 && cliente.getFoto() != null && cliente.getFoto().length() > 0) {
                uploadFileService.delete(cliente.getFoto());
            }
            String uniqueFilename = null;
            try {
                uniqueFilename = uploadFileService.copy(foto);
            } catch (IOException e) {
                e.printStackTrace();
            }

            flash.addFlashAttribute("info", "Has subido correctamente '" + uniqueFilename + "'");

            cliente.setFoto(uniqueFilename);
        }
        String mensajeFlash = (cliente.getId() != null) ? "Cliente editado con exito!" : "Cliente creado con exito!";
        clienteService.save(cliente);
        status.setComplete();
        flash.addFlashAttribute("success", mensajeFlash);
        return "redirect:listar";
    }
    @Secured("ROLE_ADMIN")
    @GetMapping("/form/{id}")
    public String editar(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
        Cliente cliente = null;

        if (id > 0) {
            cliente = clienteService.findOne(id);
            if (cliente == null) {
                flash.addFlashAttribute("error", "El ID del cliente no existe en la base de datos");
                return "redirect:/listar";
            }
        } else {
            flash.addFlashAttribute("error", "El id del cliente no puede ser cero!");
            return "redirect:/listar";
        }
        model.addAttribute("cliente", cliente);
        model.addAttribute("titulo", "Editar");
        return "form";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/eliminar/{id}", method = RequestMethod.GET)
    public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
        if (id > 0) {
            Cliente cliente = clienteService.findOne(id);

            clienteService.delete(id);
            flash.addFlashAttribute("success", "Cliente eliminado con exito!");


            if (uploadFileService.delete(cliente.getFoto())) {
                flash.addFlashAttribute("info", "Foto " + cliente.getFoto() + "eliminada con exito!");
            }
        }

        return "redirect:/listar";
    }

    //Metodo para obtener el rol del usuario
    private boolean hasRole(String role) {
        SecurityContext context = SecurityContextHolder.getContext();

        if (context == null) {
            return false;
        }

        Authentication auth = context.getAuthentication();

        if (auth == null) {
            return false;
        }

        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

        return authorities.contains(new SimpleGrantedAuthority(role));
       /* for (GrantedAuthority authority : authorities) {
            if (role.equals(authority.getAuthority())) {
                //Manda un mensaje pa la consola con un mensaje para ver el rol
                logger.info("Hola usuario ".concat(auth.getName()).concat(" tu role es: ".concat(authority.getAuthority())));
                return true;
            }
        }
        return false;*/


    }

}
