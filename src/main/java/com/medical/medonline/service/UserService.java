package com.medical.medonline.service;

import com.medical.medonline.entity.UserEntity;
import com.medical.medonline.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public UserService(ModelMapper modelMapper, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    public UserEntity createUser(String email, String name, String phone, String secondName, String surname) {
        if (email != null) {
            UserEntity userEntity = userRepository.findUserEntityByEmail(email);
            if (userEntity != null) {
                return userEntity;
            }
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setName(name);
        userEntity.setEmail(email);
        userEntity.setSurname(surname);
        userEntity.setSecondName(secondName);
        userEntity.setPhone(phone);
        userRepository.save(userEntity);

        return userEntity;
    }
}
