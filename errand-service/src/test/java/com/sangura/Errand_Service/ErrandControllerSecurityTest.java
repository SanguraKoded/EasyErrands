package com.sangura.Errand_Service;

import com.sangura.Errand_Service.config.SecurityConfig;
import com.sangura.Errand_Service.controllers.ErrandController;
import com.sangura.Errand_Service.services.ErrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ErrandController.class)
@AutoConfigureMockMvc(addFilters = true) // enables Spring Security filters
@Import(SecurityConfig.class)
class ErrandControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ErrandService errandService;

    // ✅ USER should access /errands/user/{id}
    @Test
    @WithMockUser(roles = "USER")
    void getErrandById_withUserRole_shouldReturn200() throws Exception {
        when(errandService.getErrandById(1L))
                .thenReturn(new com.sangura.Errand_Service.Dtos.ErrandSavedDto(
                        1L, "Buy milk", "Handle with care",
                        LocalDateTime.now(), LocalDateTime.now().plusHours(1),
                        "Nairobi", false, 0, 0, false
                ));

        mockMvc.perform(get("/errands/user/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Buy milk"))
                .andExpect(jsonPath("$.location").value("Nairobi"));
    }

    // ❌ ADMIN should be forbidden on USER-only endpoint
    @Test
    @WithMockUser(roles = "ADMIN")
    void getErrandById_withAdminRole_shouldReturn403() throws Exception {
        mockMvc.perform(get("/errands/user/{id}", 1L))
                .andExpect(status().isForbidden());
    }

    // ❌ No authentication should return 401
    @Test
    void getErrandById_withoutAuth_shouldReturn401() throws Exception {
        mockMvc.perform(get("/errands/user/{id}", 1L))
                .andExpect(status().isUnauthorized());
    }

    // ✅ ADMIN should access /errands/admin/all
    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllErrands_withAdminRole_shouldReturn200() throws Exception {
        when(errandService.getAllActiveErrands()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/errands/admin/all"))
                .andExpect(status().isOk());
    }

    // ❌ USER should be forbidden on ADMIN-only endpoint
    @Test
    @WithMockUser(roles = "USER")
    void getAllErrands_withUserRole_shouldReturn403() throws Exception {
        mockMvc.perform(get("/errands/admin/all"))
                .andExpect(status().isForbidden());
    }

    // ❌ No authentication should return 401 on ADMIN endpoint
    @Test
    void getAllErrands_withoutAuth_shouldReturn401() throws Exception {
        mockMvc.perform(get("/errands/admin/all"))
                .andExpect(status().isUnauthorized());
    }
}
