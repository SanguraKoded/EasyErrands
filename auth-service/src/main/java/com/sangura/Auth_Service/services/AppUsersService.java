package com.sangura.Auth_Service.services;

import com.sangura.Auth_Service.dtos.UserCreateDto;
import com.sangura.Auth_Service.dtos.UserDto;
import com.sangura.Auth_Service.entities.AppUser;

import java.util.List;

public interface AppUsersService {

    UserDto addUser(UserCreateDto userCreateDto);
    List<UserDto> getAllUsers();

    UserDto findUserById(Long id);
    UserDto updateUser(Long id, UserCreateDto userCreateDto);

    String deleteUser(Long id);

    Boolean authenticate(String username, String password);
}
