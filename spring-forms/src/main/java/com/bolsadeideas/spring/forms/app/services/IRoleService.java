package com.bolsadeideas.spring.forms.app.services;

import com.bolsadeideas.spring.forms.app.models.domain.Role;

import java.util.List;

public interface IRoleService {

    public List<Role> listar();
    public  Role obtenerporId(Integer id);
}
