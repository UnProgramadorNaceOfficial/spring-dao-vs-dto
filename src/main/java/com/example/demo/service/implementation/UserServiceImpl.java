package com.example.demo.service.implementation;

import com.example.demo.persistence.dao.interfaces.IUserDAO;
import com.example.demo.persistence.entity.UserEntity;
import com.example.demo.presentation.dto.UserDTO;
import com.example.demo.service.interfaces.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDAO userDAO;

    @Override
    public List<UserDTO> findAll() {

        ModelMapper modelMapper = new ModelMapper();

        List<UserDTO> userEntityList = userDAO.findAll()
                .stream()
                .map(userEntity -> modelMapper.map(userEntity, UserDTO.class))
                .collect(Collectors.toList());

        return userEntityList;
    }

    @Override
    public UserDTO findById(Long id) {

        Optional<UserEntity> userEntity = this.userDAO.findById(id);

        if (userEntity.isPresent()) {
            ModelMapper modelMapper = new ModelMapper();
            UserEntity currentUserEntity = userEntity.get();

            UserDTO userDTO = modelMapper.map(currentUserEntity, UserDTO.class);
            return userDTO;
        } else {
            return new UserDTO();
        }
    }

    @Override
    public String createUser(UserDTO userDTO) {
        try {
            // Convertir DTO a Entity
            ModelMapper mapper = new ModelMapper();
            UserEntity userEntity = mapper.map(userDTO, UserEntity.class);

            this.userDAO.saveUser(userEntity);

            return "Usuario creado correctamente";
        } catch (Exception exception) {
            return "Error al crear el User: " + exception.getMessage();
        }
    }

    @Override
    public String updateUser(Long userId, UserDTO userDTO) {
        // Buscar si el registro existe
        Optional<UserEntity> currentUserOptional = this.userDAO.findById(userId);

        if (currentUserOptional.isPresent()) {
            // Convertir DTO a Entity
            UserEntity userEntity = currentUserOptional.get();
            userEntity.setName(userDTO.getName());
            userEntity.setLastName(userDTO.getLastName());
            userEntity.setEmail(userDTO.getEmail());
            userEntity.setAge(userDTO.getAge());

            this.userDAO.updateUser(userEntity);
            return "Usuario actualizado correctamente";

        } else {
            return "El usuario con ID " + userId + " no existe.";
        }
    }

    @Override
    public String deleteById(Long id) {
        Optional<UserEntity> currentUserOptional = this.userDAO.findById(id);

        if(currentUserOptional.isPresent()){
            UserEntity currentUserEntity = currentUserOptional.get();
            this.userDAO.deleteUser(currentUserEntity);
            return "Usuario con ID " + id + " eliminado correctamente";
        } else {
            return "El usuario con " + id + " no existe";
        }
    }
}
