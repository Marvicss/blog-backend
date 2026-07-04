package com.example.blog_backend.unit;

import com.example.blog_backend.modules.user.User;
import com.example.blog_backend.modules.user.UserController;
import com.example.blog_backend.modules.user.UserService;
import com.example.blog_backend.modules.user.dto.UserResponseDTO;
import com.example.blog_backend.modules.user.enums.UserEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class UserControllerUnitTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_ShouldReturnUserList() {
        UUID id = UUID.randomUUID();
        User user = new User(id, "Test User", "test@test.com", UserEnum.READER, "password", new Date(), new Date());
        when(userService.findAll()).thenReturn(Collections.singletonList(user));

        ResponseEntity<List<UserResponseDTO>> response = userController.findAll();

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
        assertEquals("Test User", response.getBody().get(0).name());
        verify(userService, times(1)).findAll();
    }

    @Test
    void findById_ShouldReturnUser() {
        UUID id = UUID.randomUUID();
        User user = new User(id, "Test User", "test@test.com", UserEnum.READER, "password", new Date(), new Date());
        when(userService.findById(id)).thenReturn(user);

        ResponseEntity<UserResponseDTO> response = userController.findById(id);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Test User", response.getBody().name());
        verify(userService, times(1)).findById(id);
    }

    @Test
    void create_ShouldReturnCreatedUser() {
        UUID id = UUID.randomUUID();
        User user = new User(null, "Test User", "test@test.com", UserEnum.READER, "password", null, null);
        User createdUser = new User(id, "Test User", "test@test.com", UserEnum.READER, "encoded_password", new Date(), new Date());
        when(userService.create(user)).thenReturn(createdUser);

        ResponseEntity<UserResponseDTO> response = userController.create(user);

        assertNotNull(response);
        assertEquals(201, response.getStatusCode().value());
        assertEquals("Test User", response.getBody().name());
        verify(userService, times(1)).create(user);
    }
}
