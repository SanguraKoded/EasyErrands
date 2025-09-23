package com.sangura.Errand_Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sangura.Errand_Service.Dtos.TaskerDto;
import com.sangura.Errand_Service.Dtos.TaskerSavedDto;
import com.sangura.Errand_Service.controllers.TaskerController;
import com.sangura.Errand_Service.services.TaskerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TaskerController.class,
        excludeAutoConfiguration = {
                org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration.class
        })
class TaskerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskerService taskerService;

    @Autowired
    private ObjectMapper objectMapper;

    // ✅ Test: Create Tasker
    @Test
    void createTasker_shouldReturn201AndSavedDto() throws Exception {
        TaskerDto taskerDto = new TaskerDto("john", "john@example.com", "Nairobi");
        TaskerSavedDto saved = new TaskerSavedDto("john", "john@example.com", "Nairobi", 0, Collections.emptyList(), 0);

        when(taskerService.createTasker(any(TaskerDto.class))).thenReturn(saved);

        mockMvc.perform(post("/tasker/admin/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskerDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("john"));
    }

    // ✅ Test: Delete Tasker
    @Test
    void deleteTasker_shouldReturn200AndMessage() throws Exception {
        when(taskerService.deleteTasker(1L)).thenReturn("Tasker deleted");

        mockMvc.perform(delete("/tasker/admin/delete/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Tasker deleted"));
    }

    // ✅ Test: Update Tasker
    @Test
    void updateTasker_shouldReturn200AndUpdatedDto() throws Exception {
        TaskerDto updateDto = new TaskerDto("updated", "updated@example.com", "Mombasa");
        TaskerSavedDto updated = new TaskerSavedDto("updated", "updated@example.com", "Mombasa", 3, Collections.emptyList(), 5);

        when(taskerService.updateTasker(eq(1L), any(TaskerDto.class))).thenReturn(updated);

        mockMvc.perform(put("/tasker/admin/update/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("updated"))
                .andExpect(jsonPath("$.ratings").value(5));
    }

    // ✅ Test: Get All Taskers
    @Test
    void getAllTaskers_shouldReturn200AndList() throws Exception {
        TaskerSavedDto tasker = new TaskerSavedDto("jane", "jane@example.com", "Kisumu", 2, Collections.emptyList(), 4);

        when(taskerService.getAllTaskers()).thenReturn(List.of(tasker));

        mockMvc.perform(get("/tasker/admin/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("jane"))
                .andExpect(jsonPath("$[0].ratings").value(4));
    }

    // ✅ Test: Get Tasker By ID
    @Test
    void getTaskerById_shouldReturn200AndTasker() throws Exception {
        TaskerSavedDto tasker = new TaskerSavedDto("peter", "peter@example.com", "Nakuru", 1, Collections.emptyList(), 3);

        when(taskerService.getTaskerById(1L)).thenReturn(tasker);

        mockMvc.perform(get("/tasker/user/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("peter"))
                .andExpect(jsonPath("$.ratings").value(3));
    }
}