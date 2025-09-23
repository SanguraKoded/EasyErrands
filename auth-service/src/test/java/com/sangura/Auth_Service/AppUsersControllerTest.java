package com.sangura.Auth_Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sangura.Auth_Service.RolesEnum.Roles;
import com.sangura.Auth_Service.controllers.AppUsersController;
import com.sangura.Auth_Service.dtos.UserCreateDto;
import com.sangura.Auth_Service.dtos.UserDto;
import com.sangura.Auth_Service.services.AppUsersService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Controller slice tests for AppUsersController.
 * We disable Spring Security filters for simplicity.
 */
@WebMvcTest(AppUsersController.class)
@AutoConfigureMockMvc(addFilters = false)
class AppUsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AppUsersService appUsersService;

    /**
     * Test POST /users/add
     * Expect 201 Created and JSON user response.
     */
    @Test
    void testAddUser() throws Exception {
        UserCreateDto createDto = new UserCreateDto("john@example.com", "john_doe", "password123");
        UserDto savedDto = new UserDto(1L,  "john@example.com", "john_doe", "pass123", Roles.USER);

        when(appUsersService.addUser(any(UserCreateDto.class))).thenReturn(savedDto);

        mockMvc.perform(post("/users/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("john_doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    /**
     * Test GET /users/all
     * Expect list of users in JSON.
     */
    @Test
    void testGetAllUsers() throws Exception {
        List<UserDto> users = Arrays.asList(
                new UserDto(1L,  "john@example.com", "john_doe", "pass_123", Roles.USER),
                new UserDto(2L,  "jane@example.com", "jane_doe", "pass_123", Roles.USER)
        );

        when(appUsersService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/users/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].username").value("john_doe"))
                .andExpect(jsonPath("$[1].email").value("jane@example.com"));
    }

    /**
     * Test PUT /users/update/{id}
     * Expect 201 Created with updated user JSON.
     */
    @Test
    void testUpdateUser() throws Exception {
        UserCreateDto updateDto = new UserCreateDto("updated_user", "updated@example.com", "newPass123");
        UserDto updatedDto = new UserDto(1L,  "updated@example.com", "updated_user", "newPass123", Roles.USER);

        when(appUsersService.updateUser(Mockito.eq(1L), any(UserCreateDto.class))).thenReturn(updatedDto);

        mockMvc.perform(put("/users/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("updated_user"))
                .andExpect(jsonPath("$.email").value("updated@example.com"));
    }

    /**
     * Test GET /users/{id}
     * Expect single user JSON.
     */
    @Test
    void testFindUserById() throws Exception {
        UserDto dto = new UserDto(1L,  "john@example.com", "john_doe", "pass_123", Roles.USER);

        when(appUsersService.findUserById(1L)).thenReturn(dto);

        mockMvc.perform(get("/users/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("john_doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    /**
     * Test DELETE /users/delete/{id}
     * Expect success message.
     */
    @Test
    void testDeleteUser() throws Exception {
        when(appUsersService.deleteUser(1L)).thenReturn("User deleted successfully");

        mockMvc.perform(delete("/users/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted successfully"));
    }
}
