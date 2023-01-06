package com.prueba.crud.app.dao;

import com.prueba.crud.app.models.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface IUserDao  extends CrudRepository<User,Long> {
}
