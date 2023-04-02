package com.prueba.crud.app.models.service;

import com.prueba.crud.app.dao.IProductDao;
import com.prueba.crud.app.dao.IUserDao;
import com.prueba.crud.app.dao.IUserPagination;
import com.prueba.crud.app.models.entity.Product;
import com.prueba.crud.app.models.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserDao userDao;

    @Autowired
    private IUserPagination pagination;

    @Autowired
    private IProductDao productDao;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return (List<User>) userDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> findAll(Pageable pageable) {
        return pagination.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findByName(String term) {
        return productDao.findByName(term);
    }

    @Override
    @Transactional
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    @Transactional
    public User findById(Long id) {
        return userDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userDao.deleteById(id);
    }
}
