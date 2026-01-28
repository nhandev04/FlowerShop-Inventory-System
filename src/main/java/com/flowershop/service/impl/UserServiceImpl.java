package com.flowershop.service.impl;

import com.flowershop.dao.UserDAO;
import com.flowershop.dao.impl.UserDAOImpl;
import com.flowershop.model.dto.UserDTO;
import com.flowershop.service.UserService;

public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    public UserServiceImpl() {
        this.userDAO = new UserDAOImpl();
    }

    @Override
    public UserDTO login(String username, String password) {
        return userDAO.checkLogin(username, password);
    }
}