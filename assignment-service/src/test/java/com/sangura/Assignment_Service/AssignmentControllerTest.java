package com.sangura.Assignment_Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sangura.Assignment_Service.controllers.AssignmentController;
import com.sangura.Assignment_Service.dtos.AssignmentCreateDto;
import com.sangura.Assignment_Service.dtos.AssignmentDto;
import com.sangura.Assignment_Service.services.AssignmentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
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
 * Controller slice test for AssignmentController.
 * Uses @WebMvcTest to load only the web layer (controller + JSON mapping).
 */
@WebMvcTest(controllers = AssignmentController.class,
        excludeAutoConfiguration = {
                org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration.class
        })
class AssignmentControllerTest {

    @Autowired
    private MockMvc mockMvc;  // lets us simulate HTTP requests

    @Autowired
    private ObjectMapper objectMapper; // converts objects to JSON and back

    @MockBean
    private AssignmentService assignmentService; // mock the service layer

    @Test
    void createAssignment_shouldReturn201AndSavedDto() throws Exception {
        AssignmentCreateDto createDto = new AssignmentCreateDto(1L, 2L);
        AssignmentDto savedDto = new AssignmentDto(10L, 1L, 2L);

        when(assignmentService.createAssignment(any(AssignmentCreateDto.class))).thenReturn(savedDto);
        mockMvc.perform(post("/assignment/admin/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDto)))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10L));

    }

    @Test
    void getAllAssignments_shouldReturn200AndListOfAssignments() throws Exception {
        AssignmentDto savedDto1 = new AssignmentDto(10L, 1L, 2L);

        when(assignmentService.getAllAssignment()).thenReturn(List.of(savedDto1));

        mockMvc.perform(get("/assignment/admin/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(10L))
                .andExpect(jsonPath("$[0].errandId").value(1L));

    }

    @Test
    void getAssignmentById_shouldReturn200AndAssignmentDto() throws Exception {
        AssignmentDto savedDto2 = new AssignmentDto(10L, 1L,2L);

        when(assignmentService.findAssignmentById(10L)).thenReturn(savedDto2);

        mockMvc.perform(get("/assignment/admin/get/" + 10L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.errandId").value(1L));


    }

    @Test
    void deleteAssignment_shouldReturn200AndMessage() throws Exception {
        Mockito.when(assignmentService.deleteAssignment(10L)).thenReturn("Assignment deleted");

        mockMvc.perform(delete("/assignment/admin/delete/" + 10L))
                .andExpect(status().isOk())
                .andExpect(content().string("Assignment deleted"));
    }

    @Test
    void updateAssignment_shouldReturn200AndUpdatedDto() throws Exception {
        AssignmentCreateDto updateDto = new AssignmentCreateDto(3L, 4L);
        AssignmentDto updatedDto = new AssignmentDto(10L, 3L, 4L);

        when(assignmentService.updateAssignment(Mockito.eq(10L), any(AssignmentCreateDto.class))).thenReturn(updatedDto);

        mockMvc.perform(put("/assignment/admin/update/" + 10L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.errandId").value(3L));
    }
}
