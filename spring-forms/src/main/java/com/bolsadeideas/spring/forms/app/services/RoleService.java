package com.bolsadeideas.spring.forms.app.services;

import com.bolsadeideas.spring.forms.app.models.domain.Role;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService implements IRoleService {
    private List<Role> roles;

    public RoleService() {
        this.roles = new ArrayList<>();
        roles.add(new Role(1, "Administrador", "ROLE_ADMIN"));
        roles.add(new Role(2, "Usuario", "ROLE_USER"));
        roles.add(new Role(3, "Moderador", "ROLE_MODERATOR"));


    }

    @Override
    public List<Role> listar() {
        return roles;
    }

    @Override
    public Role obtenerporId(Integer id) {
        Role resultado = null;
        for(Role role :this.roles){
            if(id==role.getId()){
                resultado=role;
                break;
            }
        }
        return resultado;
    }
}
