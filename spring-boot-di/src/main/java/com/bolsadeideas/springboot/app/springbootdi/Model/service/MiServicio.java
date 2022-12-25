package com.bolsadeideas.springboot.app.springbootdi.Model.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

//@Service("miServicioSimple")
//@Primary //para que sea el servicio predeterminado
public class MiServicio implements IService {

    @Override()
    public String operacion() {
        return "ejecutando algun proceso importante simple";
    }
}
