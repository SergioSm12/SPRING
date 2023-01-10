package com.prueba.crud.app.models.service;

import com.prueba.crud.app.models.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService {

    public List<User> findAll();

    public void save(User user);

    public User findById(Long id);

    public void delete(Long id);

    //Metodo para paginacion
    public Page<User> findAll(Pageable pageable);

}
