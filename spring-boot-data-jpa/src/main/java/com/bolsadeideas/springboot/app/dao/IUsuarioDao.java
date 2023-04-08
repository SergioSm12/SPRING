package com.bolsadeideas.springboot.app.dao;

import com.bolsadeideas.springboot.app.models.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface IUsuarioDao extends CrudRepository<Usuario, Long> {

    //Consulta personalizada aparte de las que trae CrudRepository con Query Method name
    public Usuario findByUsername(String username);
}
