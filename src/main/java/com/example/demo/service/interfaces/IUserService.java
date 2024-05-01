package com.example.demo.service.interfaces;

import com.example.demo.presentation.dto.UserDTO;

import java.util.List;

public interface IUserService {

    List<UserDTO> findAll();
    UserDTO findById(Long id);

    String createUser(UserDTO userDTO);
    String updateUser(Long userId, UserDTO userDTO);

    String deleteById(Long id);
}
