package com.bolsadeideas.springboot.app.springbootdi.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bolsadeideas.springboot.app.springbootdi.Model.service.IService;

@Controller
public class indexController {

    @Autowired // Busca el componente y lo inyecta en este caso el service
    @Qualifier("miServicioSimple")//Busca el nombre del bean par diferenciar del que implemnta.
    private IService servicio;

    @GetMapping({ "/index", "/", "" })
    public String index(Model model) {
        model.addAttribute("objeto", servicio.operacion());
        return "index";
    }

}
