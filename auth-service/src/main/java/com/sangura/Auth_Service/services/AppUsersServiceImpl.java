package com.sangura.Auth_Service.services;

import com.sangura.Auth_Service.Repos.AppUsersRepo;
import com.sangura.Auth_Service.RolesEnum.Roles;
import com.sangura.Auth_Service.dtos.UserCreateDto;
import com.sangura.Auth_Service.dtos.UserDto;
import com.sangura.Auth_Service.entities.AppUser;
import com.sangura.Auth_Service.mappers.AppUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.ArrayList;
import java.util.List;
@Service
@RefreshScope
public class AppUsersServiceImpl implements AppUsersService{

    private static final Logger log = LoggerFactory.getLogger(AppUsersServiceImpl.class);
    private final AppUserMapper appUserMapper;

    private final AppUsersRepo appUsersRepo;

    public AppUsersServiceImpl(AppUserMapper appUserMapper, AppUsersRepo appUsersRepo) {
        this.appUserMapper = appUserMapper;
        this.appUsersRepo = appUsersRepo;
    }

    @Override
    @Transactional
    public UserDto addUser(UserCreateDto userCreateDto) {
        AppUser appUser = appUserMapper.toEntity(userCreateDto);
        appUser.setRole(Roles.USER);
        log.info("Mapped entity: {}", appUser);

        AppUser savedUser = appUsersRepo.save(appUser);
        log.info("Saved entity: {}", savedUser);

        UserDto userDto = appUserMapper.toDto(savedUser);
        log.info("Mapped DTO: {}", userDto);

        return userDto;
    }

    @Override
    @Transactional
    @Cacheable(value="users", key="#id")
    public List<UserDto> getAllUsers() {


        List <AppUser> allUsersEntities =  appUsersRepo.findAll();
        List<UserDto> allUsersDtos = new ArrayList<>();
        for(AppUser user : allUsersEntities){
            allUsersDtos.add(appUserMapper.toDto(user));
        }
        return allUsersDtos;
    }

    @Override
    @Transactional
    @Cacheable(value="user", key="#id")
    public UserDto findUserById(Long id) {
        AppUser foundUser =  appUsersRepo.findById(id).orElseThrow(() -> new RuntimeException("Please Enter Valid ID"));
        return appUserMapper.toDto(foundUser);
    }

    @Override
    @Transactional
    @CachePut(value="users", key="#id")
    public UserDto updateUser(Long id, UserCreateDto userCreateDto) {
        AppUser user = appUsersRepo.findById(id).orElseThrow(() -> new RuntimeException("Please Enter Valid ID"));

        AppUser savedUser =  appUsersRepo.save(user);
        return appUserMapper.toDto(savedUser);
    }

    @Override
    @Transactional
    @CacheEvict(value="users", key="#id")
    public String deleteUser(Long id) {
        appUsersRepo.findById(id).orElseThrow(() -> new RuntimeException("Please Enter Valid ID"));
        appUsersRepo.deleteById(id);
        return "Successfully Deleted";
    }
}
