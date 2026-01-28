package com.flowershop.dao;

import com.flowershop.model.dto.UserDTO;

public interface UserDAO {
    UserDTO checkLogin(String username, String password);
}