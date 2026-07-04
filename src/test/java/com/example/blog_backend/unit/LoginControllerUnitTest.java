package com.example.blog_backend.unit;

import com.example.blog_backend.modules.login.LoginController;
import com.example.blog_backend.modules.login.LoginService;
import com.example.blog_backend.modules.login.dto.LoginRequestDTO;
import com.example.blog_backend.modules.login.dto.LoginResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class LoginControllerUnitTest {

    @Mock
    private LoginService loginService;

    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_ShouldReturnLoginResponse() {
        LoginRequestDTO request = new LoginRequestDTO("test@test.com", "password");
        LoginResponseDTO responseDTO = new LoginResponseDTO("jwt_token", "user_id");

        when(loginService.login("test@test.com", "password")).thenReturn(responseDTO);

        ResponseEntity<LoginResponseDTO> response = loginController.login(request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals("jwt_token", response.getBody().token());
        assertEquals("user_id", response.getBody().userId());
        verify(loginService, times(1)).login("test@test.com", "password");
    }
}
