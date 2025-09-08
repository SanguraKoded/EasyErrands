package com.sangura.Auth_Service.mappers;

import com.sangura.Auth_Service.dtos.UserCreateDto;
import com.sangura.Auth_Service.dtos.UserDto;
import com.sangura.Auth_Service.entities.AppUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppUserMapper {

    UserDto toDto(AppUser appUser);
    AppUser toEntity(UserCreateDto userCreateDto);
}
