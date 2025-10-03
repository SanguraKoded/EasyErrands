package com.sangura.Errand_Service.ErrandTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sangura.Errand_Service.Dtos.ErrandDto;
import com.sangura.Errand_Service.Dtos.ErrandSavedDto;
import com.sangura.Errand_Service.controllers.ErrandController;
import com.sangura.Errand_Service.services.ErrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ErrandController.class,
        excludeAutoConfiguration = {
                org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration.class
        })
class ErrandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ErrandService errandService;

    @Autowired
    private ObjectMapper objectMapper;

    private ErrandSavedDto buildSavedDto() {
        return new ErrandSavedDto(
                1L,
                "Buy groceries",
                "Handle with care",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(2),
                "Nairobi",
                false,
                0,
                0,
                false
        );
    }

    // ✅ CREATE
    @Test
    void createErrand_shouldReturn201AndSavedDto() throws Exception {
        ErrandDto errandDto = new ErrandDto("Buy groceries", "Nairobi", "Handle with care");
        ErrandSavedDto saved = buildSavedDto();

        when(errandService.createErrand(any(ErrandDto.class))).thenReturn(saved);

        mockMvc.perform(post("/errands/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(errandDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value("Buy groceries"))
                .andExpect(jsonPath("$.location").value("Nairobi"));
    }

    // ✅ DELETE
    @Test
    void deleteErrand_shouldReturn200AndMessage() throws Exception {
        when(errandService.deleteErrand(1L)).thenReturn("Errand deleted");

        mockMvc.perform(delete("/errands/admin/delete/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Errand deleted"));
    }

    // ✅ UPDATE
    @Test
    void updateErrand_shouldReturn200AndUpdatedDto() throws Exception {
        ErrandDto updateDto = new ErrandDto("Updated task", "Mombasa", "Be fast");
        ErrandSavedDto updated = new ErrandSavedDto(
                1L, "Updated task", "Be fast",
                LocalDateTime.now(), LocalDateTime.now().plusDays(1),
                "Mombasa", true, 3, 2, false
        );

        when(errandService.updateErrand(eq(1L), any(ErrandDto.class))).thenReturn(updated);

        mockMvc.perform(put("/errands/user/update/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Updated task"))
                .andExpect(jsonPath("$.location").value("Mombasa"))
                .andExpect(jsonPath("$.done").value(true));
    }

    // ✅ GET ALL
    @Test
    void getAllErrands_shouldReturn200AndList() throws Exception {
        ErrandSavedDto errand = buildSavedDto();

        when(errandService.getAllActiveErrands()).thenReturn(List.of(errand));

        mockMvc.perform(get("/errands/admin/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value("Buy groceries"))
                .andExpect(jsonPath("$[0].location").value("Nairobi"));
    }

    // ✅ GET BY ID
    @Test
    void getErrandById_shouldReturn200AndErrand() throws Exception {
        ErrandSavedDto errand = buildSavedDto();

        when(errandService.getErrandById(1L)).thenReturn(errand);

        mockMvc.perform(get("/errands/user/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Buy groceries"))
                .andExpect(jsonPath("$.location").value("Nairobi"));
    }
}
