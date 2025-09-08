package com.sangura.Auth_Service.controllers;

import com.sangura.Auth_Service.dtos.UserCreateDto;
import com.sangura.Auth_Service.dtos.UserDto;
import com.sangura.Auth_Service.entities.AppUser;
import com.sangura.Auth_Service.services.AppUsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class AppUsersController {

    private final AppUsersService appUsersService;

    public AppUsersController(AppUsersService appUsersService) {
        this.appUsersService = appUsersService;
    }

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserDto> addUser(@RequestBody UserCreateDto userCreateDto){
        System.out.println("Received DTO: " + userCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(appUsersService.addUser(userCreateDto));

    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(appUsersService.getAllUsers());
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<UserDto> updateUser(@PathVariable Long id,  @RequestBody UserCreateDto userCreateDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(appUsersService.updateUser(id, userCreateDto));

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<UserDto> findUserById(@PathVariable Long id){
        return ResponseEntity.ok(appUsersService.findUserById(id));
    }
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<String> deleteUser(@PathVariable Long id){
        return ResponseEntity.ok(appUsersService.deleteUser(id));
    }
}
