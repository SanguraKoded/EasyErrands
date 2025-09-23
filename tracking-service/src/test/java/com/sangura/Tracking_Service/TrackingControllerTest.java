package com.sangura.Tracking_Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sangura.Tracking_Service.controllers.TrackingController;
import com.sangura.Tracking_Service.dtos.TrackingCreateDto;
import com.sangura.Tracking_Service.dtos.TrackingDto;
import com.sangura.Tracking_Service.services.TrackingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TrackingControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TrackingService trackingService;

    @InjectMocks
    private TrackingController trackingController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(trackingController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testCreateTracking() throws Exception {
        TrackingCreateDto createDto = new TrackingCreateDto(LocalDateTime.now().plusDays(2));
        TrackingDto savedDto = new TrackingDto( 10L, false, false, 0, LocalDateTime.now(), createDto.getDeadLine());

        when(trackingService.createTracking(any(TrackingCreateDto.class))).thenReturn(savedDto);

        mockMvc.perform(post("/tracking/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.assignmentId").value(savedDto.getAssignmentId()));
    }

    @Test
    void testCompleteTracking() throws Exception {
        when(trackingService.completeTracking(1L)).thenReturn("Tracking completed successfully");

        mockMvc.perform(put("/tracking/complete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Tracking completed successfully"));
    }

    @Test
    void testUpdateTracking() throws Exception {
        TrackingCreateDto updateDto = new TrackingCreateDto(LocalDateTime.now().plusDays(5));
        TrackingDto updatedDto = new TrackingDto( 20L, true, false, 0, LocalDateTime.now(), updateDto.getDeadLine());

        when(trackingService.updateTracking(eq(1L), any(TrackingCreateDto.class))).thenReturn(updatedDto);

        mockMvc.perform(put("/tracking/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.assignmentId").value(updatedDto.getAssignmentId()))
                .andExpect(jsonPath("$.isComplete").value(true));
    }

    @Test
    void testFindTrackingById() throws Exception {
        TrackingDto dto = new TrackingDto( 15L, false, true, 30, LocalDateTime.now(), LocalDateTime.now().plusDays(3));

        when(trackingService.findTrackingById(1L)).thenReturn(dto);

        mockMvc.perform(get("/tracking/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.assignmentId").value(15))
                .andExpect(jsonPath("$.isDelayed").value(true));
    }
}
