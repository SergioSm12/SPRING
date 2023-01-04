package com.bolsadeideas.spring.forms.app.controllers;

import com.bolsadeideas.spring.forms.app.editors.NombreMayusculaEditor;
import com.bolsadeideas.spring.forms.app.editors.PaisPropertyEditor;
import com.bolsadeideas.spring.forms.app.editors.RolesEditor;
import com.bolsadeideas.spring.forms.app.models.domain.Pais;
import com.bolsadeideas.spring.forms.app.models.domain.Role;
import com.bolsadeideas.spring.forms.app.models.domain.Usuario;
import com.bolsadeideas.spring.forms.app.services.PaisService;
import com.bolsadeideas.spring.forms.app.services.RoleService;
import com.bolsadeideas.spring.forms.app.validation.UsuarioValidador;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@SessionAttributes("usuario") // crea una sesion que perdura esto para persistir datos
public class FormController {
    @Autowired
    private UsuarioValidador validador;

    @Autowired
    private PaisService paisService;

    @Autowired
    private PaisPropertyEditor paisEditor;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RolesEditor rolesEditor;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(validador);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        binder.registerCustomEditor(String.class, "nombre", new NombreMayusculaEditor());

        //Matricular pais properti editor esto para pasar un objeto completo a un campo
        binder.registerCustomEditor(Pais.class, "pais", paisEditor);
        //registrar Roles
        binder.registerCustomEditor(Role.class, "roles", rolesEditor);
    }

    //genero dinamico con un solo input
    @ModelAttribute("genero")
    public List<String> genero() {
        return Arrays.asList("Hombre", "Mujer");
    }

    //lista de paises con objetos
    @ModelAttribute("listaPaises")
    public List<Pais> ListaPaises() {
        return paisService.listar();
    }

    //Checkbox de roles
    @ModelAttribute("listaRoles")
    public List<Role> listaRoles() {
        return this.roleService.listar();
    }

    //lista para checkbox
    @ModelAttribute("listaRolesString")//CON ESE NOMBRE SE ENVIA A LA LISTA
    public List<String> listaRolesString() {
        //se puede tan bien con Arrays.aslist , pero se hace de la otra forma pra ver como es.
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_ADMIN");
        roles.add("ROLE_USER");
        roles.add("ROLE_MODERATOR");
        return roles;
    }

    //Lista checkbox con un map
    @ModelAttribute("listaRolesMap")
    public Map<String, String> listaRolesMap() {
        Map<String, String> roles = new HashMap<String, String>();
        roles.put("ROLE_ADMIN", "Administrador");
        roles.put("ROLE_USER", "Usuario");
        roles.put("ROLE_MODERATOR", "Moderador");


        return roles;
    }

    @ModelAttribute("paises")
    public List<String> paises() {
        return Arrays.asList("España", "Mexico", "Chile", "Argentina", "Peru", "Colombia", "Venezula");
    }

    @ModelAttribute("paisesMap")
    public Map<String, String> paisesMap() {
        Map<String, String> paises = new HashMap<String, String>();
        paises.put("ES", "España");
        paises.put("MX", "Mexico");
        paises.put("CL", "Chile");
        paises.put("AR", "Argentina");
        paises.put("PE", "Peru");
        paises.put("CO", "Colombia");
        paises.put("VE", "Venezula");

        return paises;
    }

    @GetMapping("/form")
    public String form(Model model) {
        Usuario usuario = new Usuario();
        //Pasar datos por defecto
        usuario.setNombre("John");
        usuario.setApellido("Doe");
        //no lo requerimos en el formulario pero queremos mantener la informacion

        usuario.setIdentificador("12.456.789-K");
        usuario.setHabilitar(true);
        usuario.setValorSecreto("Algun valor secreto ****");
        usuario.setPais(new Pais(6, "CO", "Colombia"));
        usuario.setRoles(Arrays.asList(new Role(2, "Usuario", "ROLE_USER")));

        model.addAttribute("titulo", "Formulario de usuarios");
        model.addAttribute("usuario", usuario);
        return "form";
    }

    @PostMapping("/form")
    public String procesar(@Valid Usuario usuario, BindingResult bindingResult, Model model) //sesion estatus se usa para limpiar los datos enviados durante la sesion session
    {
        //validador.validate(usuario, bindingResult);
       /* if (bindingResult.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(err ->{
                errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
            });
            model.addAttribute("error", errores);
            return "form";
        }*/
        //Manejo de errores mas limpio :
        if (bindingResult.hasErrors()) {
            model.addAttribute("titulo", "Resultado form");
            return "form";
        }
        model.addAttribute("usuario", usuario);
        return "redirect:/ver";
    }

    @GetMapping("/ver")
    public String ver(@SessionAttribute(name="usuario", required = false) Usuario usuario, Model model, SessionStatus status) {
        if (usuario == null) {
            return "redirect:/form";
        }
        model.addAttribute("titulo", "Resultado del formulario");
        // completa y limplia los objetos que se pasaron como session para persistir
        status.setComplete();
        return "resultado";
    }
}
