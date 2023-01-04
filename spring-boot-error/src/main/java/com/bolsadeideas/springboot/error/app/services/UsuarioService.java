package com.bolsadeideas.springboot.error.app.services;

import com.bolsadeideas.springboot.error.app.models.domain.Usuario;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements IUsuarioService {
    private List<Usuario> lista;


    public UsuarioService() {
        this.lista= new ArrayList<>();
        this.lista.add(new Usuario(1, "Sergio", "Mesa"));
        this.lista.add(new Usuario(2, "David", "Buitrago"));
        this.lista.add(new Usuario(3, "Alejandro", "Mesa"));
        this.lista.add(new Usuario(4, "Aron", "Lopez"));
        this.lista.add(new Usuario(5, "Sneider", "Sanchez"));
        this.lista.add(new Usuario(6, "Dayron", "Hernandez"));
        this.lista.add(new Usuario(7, "Alejandra", "Perez"));

    }

    @Override
    public List<Usuario> listar() {
        return this.lista;
    }

    @Override
    public Usuario obtenerPorId(Integer id) {
        Usuario resultado = null;
        for(Usuario u : this.lista){
            if(u.getId().equals(id)){
                resultado=u;
                break;
            }
        }

        return resultado;
    }

    @Override
    public Optional<Usuario> obtenerPorIdOptional(Integer id) {
        Usuario usuario=  this.obtenerPorId(id);
        return Optional.ofNullable(usuario);
    }
}
