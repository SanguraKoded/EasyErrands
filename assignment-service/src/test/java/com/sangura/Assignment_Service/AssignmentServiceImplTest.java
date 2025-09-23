package com.sangura.Assignment_Service;

import com.sangura.Assignment_Service.client.ErrandServiceClient;
import com.sangura.Assignment_Service.client.TaskerServiceClient;
import com.sangura.Assignment_Service.dtos.AssignmentCreateDto;
import com.sangura.Assignment_Service.dtos.AssignmentDto;
import com.sangura.Assignment_Service.dtos.ErrandSavedDto;
import com.sangura.Assignment_Service.dtos.TaskerSavedDto;
import com.sangura.Assignment_Service.entities.Assignment;
import com.sangura.Errand_Service.entities.Tasker;
import com.sangura.Assignment_Service.mappers.AssignmentMapper;
import com.sangura.Assignment_Service.repos.AssignmentRepo;
import com.sangura.Assignment_Service.services.AssignmentEventProducer;
import com.sangura.Assignment_Service.services.AssignmentServiceImpl;
import com.sangura.common.events.DTOs.AssignmentEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AssignmentServiceImplTest {

    @Mock
    private AssignmentRepo assignmentRepo;

    @Mock
    private AssignmentMapper assignmentMapper;

    @Mock
    private AssignmentEventProducer assignmentEventProducer;

    @Mock
    private ErrandServiceClient errandClient;

    @Mock
    private TaskerServiceClient taskerClient;

    @InjectMocks
    private AssignmentServiceImpl assignmentService;

    private Assignment assignment;
    private AssignmentDto dto;
    private AssignmentCreateDto createDto;
    private ErrandSavedDto errand;
    private TaskerSavedDto tasker;
    private Tasker taskerEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        createDto = new AssignmentCreateDto();
        createDto.setErrandId(100L);
        createDto.setTaskerId(200L);

        assignment = new Assignment(1L, 100L, 200L, LocalDateTime.now());

        dto = new AssignmentDto(1L, 100L, 200L);
        dto.setId(1L);
        dto.setErrandId(100L);
        dto.setTaskerId(200L);

        errand = new ErrandSavedDto();
        errand.setId(100L);
        errand.setDescription("Buy groceries at supermarket");
        errand.setSpecialInstructions("Get low-fat milk and whole wheat bread");
        errand.setTimeCreated(LocalDateTime.now());
        errand.setDeadLine(LocalDateTime.now().plusHours(3));
        errand.setLocation("Downtown Market Street");
        errand.setDone(false);
        errand.setTimeTaken(0L);
        errand.setAssignedTasker(101L);
        errand.setLate(false);

        // Create dummy TaskerSavedDto
        tasker = new TaskerSavedDto();
        tasker.setUsername("john_doe");
        tasker.setEmail("john.doe@example.com");
        tasker.setLocation("Nairobi West");
        tasker.setErrandsCompleted(25);
        tasker.setActiveErrandIds(Arrays.asList(1L, 2L, 3L));
        tasker.setRatings(5);

        taskerEntity = new Tasker();
        taskerEntity.setUsername("john_doe");
        taskerEntity.setEmail("john.doe@example.com");
        taskerEntity.setLocation("Nairobi West");
        taskerEntity.setErrandsCompleted(25);
        taskerEntity.setActiveErrands(Arrays.asList(1L, 2L, 3L));
        taskerEntity.setRatings(5);
        taskerEntity.setErrandsCompleted(0);
        taskerEntity.setId(200L);


    }

    @Test
    void testCreateAssignment_Success() {
        when(assignmentMapper.toEntity(createDto)).thenReturn(assignment);
        when(assignmentRepo.save(any(Assignment.class))).thenReturn(assignment);
        when(assignmentMapper.toDto(assignment)).thenReturn(dto);

        when(errandClient.findErrandById(100L)).thenReturn(errand);
        when(taskerClient.findTaskerById(200L)).thenReturn(tasker);

        AssignmentDto result = assignmentService.createAssignment(createDto);

        assertNotNull(result);
        assertEquals(100L, result.getErrandId());
        assertEquals(200L, result.getTaskerId());

        verify(assignmentRepo, times(1)).save(any(Assignment.class));
        verify(assignmentEventProducer, times(1)).publishAssignmentCreated(any(AssignmentEvent.class));
    }

    @Test
    void testCreateAssignment_ErrandNotFound() {
        when(assignmentMapper.toEntity(createDto)).thenReturn(assignment);
        doThrow(new RuntimeException("Errand not found"))
                .when(errandClient).findErrandById(100L);

        assertThrows(RuntimeException.class, () -> assignmentService.createAssignment(createDto));
    }

    @Test
    void testCreateAssignment_TaskerNotFound() {
        when(assignmentMapper.toEntity(createDto)).thenReturn(assignment);
        when(errandClient.findErrandById(100L)).thenReturn(errand);
        doThrow(new RuntimeException("Tasker not found"))
                .when(taskerClient).findTaskerById(100L);

        assertThrows(RuntimeException.class, () -> assignmentService.createAssignment(createDto));
    }

    @Test
    void testGetAllAssignment() {
        List<Assignment> entities = Arrays.asList(assignment);
        List<AssignmentDto> dtos = Arrays.asList(dto);

        when(assignmentRepo.findAll()).thenReturn(entities);
        when(assignmentMapper.toDto(assignment)).thenReturn(dto);

        List<AssignmentDto> result = assignmentService.getAllAssignment();

        assertEquals(1, result.size());
        assertEquals(dto.getId(), result.get(0).getId());
    }

    @Test
    void testDeleteAssignment_Success() {
        when(assignmentRepo.findById(1L)).thenReturn(Optional.of(assignment));

        String result = assignmentService.deleteAssignment(1L);

        assertEquals("Successfully Deleted", result);
        verify(assignmentRepo, times(1)).delete(assignment);
        verify(assignmentEventProducer, times(1))
                .publishAssignmentDeleted(any(AssignmentEvent.class));
    }

    @Test
    void testDeleteAssignment_NotFound() {
        when(assignmentRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> assignmentService.deleteAssignment(1L));
    }

    @Test
    void testFindAssignmentById_Success() {
        when(assignmentRepo.findById(1L)).thenReturn(Optional.of(assignment));
        when(assignmentMapper.toDto(assignment)).thenReturn(dto);

        AssignmentDto result = assignmentService.findAssignmentById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testFindAssignmentById_NotFound() {
        when(assignmentRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> assignmentService.findAssignmentById(1L));
    }

    @Test
    void testUpdateAssignment_Success() {
        when(assignmentRepo.findById(1L)).thenReturn(Optional.of(assignment));
        when(assignmentMapper.toEntity(createDto)).thenReturn(assignment);
        when(assignmentRepo.save(any(Assignment.class))).thenReturn(assignment);
        when(assignmentMapper.toDto(assignment)).thenReturn(dto);

        when(errandClient.findErrandById(100L)).thenReturn(errand);
        when(taskerClient.findTaskerById(200L)).thenReturn(tasker);

        AssignmentDto result = assignmentService.updateAssignment(1L, createDto);

        assertNotNull(result);
        assertEquals(dto.getId(), result.getId());
        verify(assignmentEventProducer, times(1)).publishAssignmentUpdated(any(AssignmentEvent.class));
    }

    @Test
    void testUpdateAssignment_NotFound() {
        when(assignmentRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> assignmentService.updateAssignment(1L, createDto));
    }

    @Test
    void testUpdateAssignment_ErrandNotFound() {
        when(assignmentRepo.findById(1L)).thenReturn(Optional.of(assignment));
        doThrow(new RuntimeException("Errand not found"))
                .when(errandClient).findErrandById(100L);

        assertThrows(RuntimeException.class,
                () -> assignmentService.updateAssignment(1L, createDto));
    }

    @Test
    void testUpdateAssignment_TaskerNotFound() {
        when(assignmentRepo.findById(1L)).thenReturn(Optional.of(assignment));
        when(errandClient.findErrandById(100L)).thenReturn(errand);
        doThrow(new RuntimeException("Tasker not found"))
                .when(taskerClient).findTaskerById(200L);

        assertThrows(RuntimeException.class,
                () -> assignmentService.updateAssignment(1L, createDto));
    }
}
