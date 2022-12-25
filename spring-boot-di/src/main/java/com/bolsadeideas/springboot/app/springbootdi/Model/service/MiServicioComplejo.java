package com.bolsadeideas.springboot.app.springbootdi.Model.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

//@Service("miServicioComplejo")
public class MiServicioComplejo implements IService {

    @Override
    public String operacion() {
        return "ejecutando algun proceso importante complicado";
    }
}
