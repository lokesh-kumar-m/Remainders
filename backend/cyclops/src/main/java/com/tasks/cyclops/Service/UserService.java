package com.tasks.cyclops.Service;

import java.util.Optional;

import com.tasks.cyclops.dto.UserDto;
import com.tasks.cyclops.model.UserEntity;

public interface UserService {
    String createUser(UserDto userDto);

    Optional<UserEntity> getUser(String name);

    boolean existingUser(String name);

    String isAuthentic(String name, String password);
}
