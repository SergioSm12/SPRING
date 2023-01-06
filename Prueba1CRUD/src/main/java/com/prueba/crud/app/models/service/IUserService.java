package com.prueba.crud.app.models.service;

import com.prueba.crud.app.models.entity.User;

import java.util.List;

public interface IUserService {

    public List<User> findAll();

    public void save(User user);

    public User findById(Long id);

    public void delete(Long id);

}
