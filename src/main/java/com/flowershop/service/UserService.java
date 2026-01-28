package com.flowershop.service;

import com.flowershop.model.dto.UserDTO;

public interface UserService {
    UserDTO login(String username, String password);
}