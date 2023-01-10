package com.prueba.crud.app.dao;

import com.prueba.crud.app.models.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


public interface IUserPagination extends PagingAndSortingRepository<User,Long> {
}
