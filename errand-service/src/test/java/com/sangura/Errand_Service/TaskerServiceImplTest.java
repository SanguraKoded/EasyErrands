package com.sangura.Errand_Service;

import com.sangura.Errand_Service.Dtos.TaskerDto;
import com.sangura.Errand_Service.Dtos.TaskerSavedDto;
import com.sangura.Errand_Service.entities.Tasker;
import com.sangura.Errand_Service.mappers.TaskerMapper;
import com.sangura.Errand_Service.repos.TaskerRepo;
import com.sangura.Errand_Service.services.TaskerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskerServiceImplTest {

    @Mock
    private TaskerRepo taskerRepo;

    @Mock
    private TaskerMapper taskerMapper;

    @InjectMocks
    private TaskerServiceImpl taskerService;

    private Tasker taskerEntity;
    private TaskerDto taskerDto;
    private TaskerSavedDto taskerSavedDto;

    @BeforeEach
    void setup() {
        taskerEntity = new Tasker(
                1L,
                "john",
                "john@mail.com",
                "Nairobi",
                5,
                new ArrayList<>(List.of(101L, 102L)),
                4
        );

        taskerDto = new TaskerDto("john", "john@mail.com", "Nairobi");

        taskerSavedDto = new TaskerSavedDto();
        taskerSavedDto.setUsername("john");
        taskerSavedDto.setEmail("john@mail.com");
        taskerSavedDto.setLocation("Nairobi");
        taskerSavedDto.setErrandsCompleted(5);
        taskerSavedDto.setActiveErrandIds(new ArrayList<>(List.of(101L, 102L)));
        taskerSavedDto.setRatings(4);
    }

    @Test
    void createTasker_success() {
        when(taskerMapper.toEntity(taskerDto)).thenReturn(taskerEntity);
        when(taskerRepo.save(any(Tasker.class))).thenReturn(taskerEntity);
        when(taskerMapper.toDto(taskerEntity)).thenReturn(taskerSavedDto);

        TaskerSavedDto result = taskerService.createTasker(taskerDto);

        assertNotNull(result);
        assertEquals("john", result.getUsername());
        verify(taskerRepo, times(1)).save(any(Tasker.class));
    }

    @Test
    void getTaskerById_found() {
        when(taskerRepo.findById(1L)).thenReturn(Optional.of(taskerEntity));
        when(taskerMapper.toDto(taskerEntity)).thenReturn(taskerSavedDto);

        TaskerSavedDto result = taskerService.getTaskerById(1L);

        assertNotNull(result);
        assertEquals("john@mail.com", result.getEmail());
        verify(taskerRepo, times(1)).findById(1L);
    }

    @Test
    void getTaskerById_notFound() {
        when(taskerRepo.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                taskerService.getTaskerById(1L));

        assertEquals("Please Enter Valid Tasker ID", ex.getMessage());
        verify(taskerRepo, times(1)).findById(1L);
    }

    @Test
    void getAllTaskers_success() {
        when(taskerRepo.findAll()).thenReturn(List.of(taskerEntity));
        when(taskerMapper.toDto(taskerEntity)).thenReturn(taskerSavedDto);

        List<TaskerSavedDto> result = taskerService.getAllTaskers();

        assertEquals(1, result.size());
        assertEquals("john", result.get(0).getUsername());
        verify(taskerRepo, times(1)).findAll();
    }

    @Test
    void updateTasker_success() {
        TaskerDto updatedDto = new TaskerDto("johnny", "johnny@mail.com", "Mombasa");
        Tasker updatedEntity = new Tasker(
                1L, "johnny", "johnny@mail.com", "Mombasa",
                5, new ArrayList<>(List.of(101L, 102L)), 4
        );
        TaskerSavedDto updatedSavedDto = new TaskerSavedDto();
        updatedSavedDto.setUsername("johnny");
        updatedSavedDto.setEmail("johnny@mail.com");
        updatedSavedDto.setLocation("Mombasa");
        updatedSavedDto.setErrandsCompleted(5);
        updatedSavedDto.setActiveErrandIds(new ArrayList<>(List.of(101L, 102L)));
        updatedSavedDto.setRatings(4);

        when(taskerRepo.findById(1L)).thenReturn(Optional.of(taskerEntity));
        when(taskerMapper.toEntity(updatedDto)).thenReturn(updatedEntity);
        when(taskerRepo.save(updatedEntity)).thenReturn(updatedEntity);
        when(taskerMapper.toDto(updatedEntity)).thenReturn(updatedSavedDto);

        TaskerSavedDto result = taskerService.updateTasker(1L, updatedDto);

        assertNotNull(result);
        assertEquals("Mombasa", result.getLocation());
        verify(taskerRepo, times(1)).save(updatedEntity);
    }

    @Test
    void deleteTasker_success() {
        when(taskerRepo.findById(1L)).thenReturn(Optional.of(taskerEntity));

        String result = taskerService.deleteTasker(1L);

        assertEquals("Successfully Deleted Tasker", result);
        verify(taskerRepo, times(1)).deleteById(1L);
    }

    @Test
    void deleteTasker_notFound() {
        when(taskerRepo.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                taskerService.deleteTasker(1L));

        assertEquals("Please Enter Valid ID", ex.getMessage());
        verify(taskerRepo, never()).deleteById(anyLong());
    }
}
