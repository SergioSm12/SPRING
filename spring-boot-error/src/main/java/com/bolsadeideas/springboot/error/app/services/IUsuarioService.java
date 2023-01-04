package com.bolsadeideas.springboot.error.app.services;

import com.bolsadeideas.springboot.error.app.models.domain.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    public List<Usuario> listar();
    public Usuario obtenerPorId(Integer id);

    public Optional<Usuario> obtenerPorIdOptional(Integer id);
}
