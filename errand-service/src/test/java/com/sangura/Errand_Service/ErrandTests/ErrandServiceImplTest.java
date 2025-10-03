package com.sangura.Errand_Service.ErrandTests;

import com.sangura.Errand_Service.Dtos.ErrandDto;
import com.sangura.Errand_Service.Dtos.ErrandSavedDto;
import com.sangura.Errand_Service.entities.Errand;
import com.sangura.Errand_Service.mappers.ErrandMapper;
import com.sangura.Errand_Service.repos.ErrandsRepo;
import com.sangura.Errand_Service.services.ErrandEventProducer;
import com.sangura.Errand_Service.services.ErrandServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ErrandServiceImplTest {

    @Mock private ErrandsRepo errandsRepo;
    @Mock private ErrandMapper errandMapper;
    @Mock private ErrandEventProducer errandEventProducer;

    @InjectMocks private ErrandServiceImpl errandService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createErrand_success() {
        ErrandDto dto = new ErrandDto("Deliver package", "Nairobi", "Handle with care");
        Errand entity = new Errand(
                1L,
                "Deliver package",                 // description
                "Handle with care",                // specialInstructions
                LocalDateTime.now(),               // timeCreated
                LocalDateTime.now().plusHours(2),  // deadLine
                "Nairobi",                         // location
                false,                             // isDone
                0L,                                // timeTaken
                123L,                              // assignedTasker
                false                              // isLate
        );
        Errand savedEntity = new Errand(
                1L,
                "Deliver package",                 // description
                "Handle with care",                // specialInstructions
                LocalDateTime.now(),               // timeCreated
                LocalDateTime.now().plusHours(2),  // deadLine
                "Nairobi",                         // location
                false,                             // isDone
                0L,                                // timeTaken
                123L,                              // assignedTasker
                false                              // isLate
        );

        ErrandSavedDto savedDto = new ErrandSavedDto(
                1L,
                "Deliver package",                 // description
                "Handle with care",                // specialInstructions
                LocalDateTime.now(),               // timeCreated
                LocalDateTime.now().plusHours(2),  // deadLine
                "Nairobi",                         // location
                false,                             // isDone
                0L,                                // timeTaken
                123L,                              // assignedTasker
                false                              // isLate
        );


        when(errandMapper.toEntity(dto)).thenReturn(entity);
        when(errandsRepo.save(any(Errand.class))).thenReturn(savedEntity);
        when(errandMapper.toDto(savedEntity)).thenReturn(savedDto);

        ErrandSavedDto result = errandService.createErrand(dto);

        assertEquals("Deliver package", result.getDescription());
        assertNotNull(result.getId());
        verify(errandsRepo, times(1)).save(entity);
        verify(errandEventProducer, times(1)).publishErrandCreated(any());
    }

    @Test
    void getErrandById_found() {
        Errand entity = new Errand(
                1L,
                "Deliver package",                 // description
                "Handle with care",                // specialInstructions
                LocalDateTime.now(),               // timeCreated
                LocalDateTime.now().plusHours(2),  // deadLine
                "Nairobi",                         // location
                false,                             // isDone
                0L,                                // timeTaken
                123L,                              // assignedTasker
                false                              // isLate
        );

        ErrandSavedDto savedDto = new ErrandSavedDto(
                1L,
                "Deliver package",                 // description
                "Handle with care",                // specialInstructions
                LocalDateTime.now(),               // timeCreated
                LocalDateTime.now().plusHours(2),  // deadLine
                "Nairobi",                         // location
                false,                             // isDone
                0L,                                // timeTaken
                123L,                              // assignedTasker
                false                              // isLate
        );

        when(errandsRepo.findById(1L)).thenReturn(Optional.of(entity));
        when(errandMapper.toDto(entity)).thenReturn(savedDto);

        var result = errandService.getErrandById(1L);

        assertEquals("Deliver package", result.getDescription());
        assertNotNull(result.getId());
    }

    @Test
    void getErrandById_notFound() {
        when(errandsRepo.findById(1L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(RuntimeException.class, () -> errandService.getErrandById(1L));
        assertEquals("Please Enter Valid Errand ID", ex.getMessage());
    }
}
