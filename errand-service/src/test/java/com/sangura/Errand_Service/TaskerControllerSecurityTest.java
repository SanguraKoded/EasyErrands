package com.sangura.Errand_Service;

import com.sangura.Errand_Service.controllers.TaskerController;
import com.sangura.Errand_Service.config.SecurityConfig;
import com.sangura.Errand_Service.services.TaskerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskerController.class)
@AutoConfigureMockMvc(addFilters = true) // enables Spring Security filters
@Import(SecurityConfig.class)
class TaskerControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskerService taskerService;

    @Test
    @WithMockUser(roles = "USER")
    void getTaskerById_withUserRole_shouldReturn200() throws Exception {
        when(taskerService.getTaskerById(1L))
                .thenReturn(new com.sangura.Errand_Service.Dtos.TaskerSavedDto(
                        "peter", "peter@example.com", "Nakuru", 1, java.util.Collections.emptyList(), 3
                ));

        mockMvc.perform(get("/tasker/user/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("peter"))
                .andExpect(jsonPath("$.ratings").value(3));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getTaskerById_withAdminRole_shouldReturn403() throws Exception {
        mockMvc.perform(get("/tasker/user/{id}", 1L)) // fixed path
                .andExpect(status().isForbidden());
    }

    @Test
    void getTaskerById_withoutAuth_shouldReturn401() throws Exception {
        mockMvc.perform(get("/tasker/user/{id}", 1L)) // fixed path
                .andExpect(status().isUnauthorized());
    }
}
