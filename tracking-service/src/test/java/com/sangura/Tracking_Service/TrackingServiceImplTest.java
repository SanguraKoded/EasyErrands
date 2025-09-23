package com.sangura.Tracking_Service;

import com.sangura.Tracking_Service.client.AssignmentServiceClient;
import com.sangura.Tracking_Service.dtos.AssignmentDto;
import com.sangura.Tracking_Service.dtos.TrackingCreateDto;
import com.sangura.Tracking_Service.dtos.TrackingDto;
import com.sangura.Tracking_Service.entities.Tracking;
import com.sangura.Tracking_Service.mapper.TrackingMapper;
import com.sangura.Tracking_Service.repos.TrackingRepo;
import com.sangura.Tracking_Service.services.TrackingEventProducer;
import com.sangura.Tracking_Service.services.TrackingServiceImpl;
import com.sangura.common.events.DTOs.TrackingEvent;
import com.sangura.common.events.enums.TrackingEventType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrackingServiceImplTest {

    @Mock
    private TrackingMapper trackingMapper;

    @Mock
    private TrackingEventProducer trackingEventProducer;

    @Mock
    private TrackingRepo trackingRepo;

    @Mock
    private AssignmentServiceClient assignmentClient;

    @InjectMocks
    private TrackingServiceImpl trackingService;

    private TrackingCreateDto createDto;
    private Tracking entity;
    private TrackingDto dto;
    private AssignmentDto assignmentDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        createDto = new TrackingCreateDto(LocalDateTime.now().plusDays(1));

        entity = new Tracking();
        entity.setId(1L);
        entity.setAssignmentId(101L);
        entity.setComplete(false);
        entity.setDelayed(false);
        entity.setDelayedTime(0);
        entity.setTimeCreated(LocalDateTime.now());
        entity.setDeadLine(createDto.getDeadLine());

        dto = new TrackingDto();
        dto.setAssignmentId(entity.getAssignmentId());
        dto.setComplete(entity.getComplete());
        dto.setDelayed(entity.getDelayed());
        dto.setDelayedTime(entity.getDelayedTime());
        dto.setTimeCreated(entity.getTimeCreated());
        dto.setDeadLine(entity.getDeadLine());

        assignmentDto = new AssignmentDto();
        assignmentDto.setId(101L);
        assignmentDto.setCreatedAt(entity.getTimeCreated().minusMinutes(5));
        assignmentDto.setErrandId(100L);
        assignmentDto.setTaskerId(103L);
    }

    @Test
    void testCreateTracking_Success() {
        // Mock mapping and repository
        when(trackingMapper.toEntity(createDto)).thenReturn(entity);
        when(trackingRepo.save(any(Tracking.class))).thenReturn(entity);
        when(trackingMapper.toDto(entity)).thenReturn(dto);

        // Mock assignmentClient
        when(assignmentClient.findAssignmentById(101L)).thenReturn(assignmentDto);

        TrackingDto result = trackingService.createTracking(createDto);

        assertNotNull(result);
        assertEquals(dto.getAssignmentId(), result.getAssignmentId());
        verify(trackingRepo, times(1)).save(any(Tracking.class));
        verify(trackingEventProducer, times(1))
                .publishTrackingCreated(any(TrackingEvent.class));
    }

    @Test
    void testCreateTracking_AssignmentNotFound() {
        when(trackingMapper.toEntity(createDto)).thenReturn(entity);
        doThrow(new RuntimeException("Not found"))
                .when(assignmentClient).findAssignmentById(101L);

        assertThrows(RuntimeException.class, () -> trackingService.createTracking(createDto));
    }

    @Test
    void testUpdateTracking_Success() {
        when(trackingRepo.findById(1L)).thenReturn(Optional.of(entity));
        when(trackingMapper.toEntity(createDto)).thenReturn(entity);
        when(trackingRepo.save(entity)).thenReturn(entity);
        when(trackingMapper.toDto(entity)).thenReturn(dto);

        TrackingDto result = trackingService.updateTracking(1L, createDto);

        assertNotNull(result);
        assertEquals(dto.getAssignmentId(), result.getAssignmentId());
        verify(trackingEventProducer, times(1))
                .publishTrackingUpdated(any(TrackingEvent.class));
    }

    @Test
    void testUpdateTracking_IdNotFound() {
        when(trackingRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> trackingService.updateTracking(1L, createDto));
    }

    @Test
    void testCompleteTracking_Success() {
        entity.setComplete(false);

        when(trackingRepo.findById(1L)).thenReturn(Optional.of(entity));
        when(trackingRepo.save(any(Tracking.class))).thenReturn(entity);

        String result = trackingService.completeTracking(1L);

        assertEquals("Tracking is successfully completed", result);
        assertTrue(entity.getComplete());
        verify(trackingEventProducer, times(1))
                .publishTrackingCompleted(any(TrackingEvent.class));
    }

    @Test
    void testCompleteTracking_IdNotFound() {
        when(trackingRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> trackingService.completeTracking(1L));
    }

    @Test
    void testFindTrackingById_Success() {
        when(trackingRepo.findById(1L)).thenReturn(Optional.of(entity));
        when(trackingMapper.toDto(entity)).thenReturn(dto);

        TrackingDto result = trackingService.findTrackingById(1L);

        assertNotNull(result);
        assertEquals(dto.getAssignmentId(), result.getAssignmentId());
    }

    @Test
    void testFindTrackingById_NotFound() {
        when(trackingRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> trackingService.findTrackingById(1L));
    }
}
