package com.bolsadeideas.spring.forms.app.services;

import com.bolsadeideas.spring.forms.app.models.domain.Pais;

import java.util.List;

public interface PaisService {

    public List<Pais> listar();
    public Pais obtenerPorId(Integer id);
}
