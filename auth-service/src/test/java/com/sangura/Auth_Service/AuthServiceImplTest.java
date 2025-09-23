package com.sangura.Auth_Service;

import com.sangura.Auth_Service.RolesEnum.Roles;
import com.sangura.Auth_Service.dtos.UserCreateDto;
import com.sangura.Auth_Service.dtos.UserDto;
import com.sangura.Auth_Service.entities.AppUser;
import com.sangura.Auth_Service.Repos.AppUsersRepo;
import com.sangura.Auth_Service.mappers.AppUserMapper;
import com.sangura.Auth_Service.services.AppUsersServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AuthServiceImplTest {

    @Mock private AppUsersRepo userRepo;
    @Mock private AppUserMapper appUserMapper;   // ✅ Added mock for mapper

    @InjectMocks private AppUsersServiceImpl authService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUser_success() {
        UserCreateDto userDto = new UserCreateDto("john12@gmail.com", "john", "pass123");

        AppUser entity = new AppUser(null, "john12@gmail.com", "john", "pass123", Roles.USER);
        AppUser savedEntity = new AppUser(1L, "john12@gmail.com", "john", "pass123", Roles.USER);
        UserDto mappedDto = new UserDto(1L, "john12@gmail.com", "john", "pass_123",Roles.USER);

        // mock mapper + repo
        when(appUserMapper.toEntity(userDto)).thenReturn(entity);
        when(userRepo.save(entity)).thenReturn(savedEntity);
        when(appUserMapper.toDto(savedEntity)).thenReturn(mappedDto);

        var result = authService.addUser(userDto);

        assertNotNull(result);
        assertEquals("john", result.getUsername());
        assertEquals("john12@gmail.com", result.getEmail());

        // verify save was called
        verify(userRepo, times(1)).save(entity);
    }

    @Test
    void authenticate_success() {
        UserCreateDto userDto = new UserCreateDto("john@gmail.com", "john", "pass123");

        AppUser entity = new AppUser(null, "john@gmail.com", "john", "pass123", Roles.USER);
        AppUser savedEntity = new AppUser(1L, "john@gmail.com", "john", "pass123", Roles.USER);
        UserDto mappedDto = new UserDto(1L, "john@gmail.com", "john", "pass_123", Roles.USER);

        when(appUserMapper.toEntity(userDto)).thenReturn(entity);
        when(userRepo.save(entity)).thenReturn(savedEntity);
        when(appUserMapper.toDto(savedEntity)).thenReturn(mappedDto);

        // register user
        authService.addUser(userDto);

        // simulate repo lookup
        when(userRepo.findByUsername("john")).thenReturn(Optional.of(savedEntity));

        boolean result = authService.authenticate("john", "pass123");

        assertTrue(result);
    }

    @Test
    void authenticate_fail_wrongPassword() {
        AppUser user = new AppUser(1L, "john@gmail.com", "john", "pass123", Roles.USER);

        when(userRepo.findByUsername("john")).thenReturn(Optional.of(user));

        boolean result = authService.authenticate("john", "wrong");

        assertFalse(result);
    }
}
