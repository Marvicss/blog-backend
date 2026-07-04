package com.example.blog_backend.integration;

import com.example.blog_backend.config.SecurityConfig;
import com.example.blog_backend.modules.login.LoginController;
import com.example.blog_backend.modules.login.LoginService;
import com.example.blog_backend.modules.login.dto.LoginRequestDTO;
import com.example.blog_backend.modules.login.dto.LoginResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoginController.class)
@Import(SecurityConfig.class)
class LoginControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LoginService loginService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void login_ShouldBeAllowedForEveryone() throws Exception {
        LoginRequestDTO request = new LoginRequestDTO("test@test.com", "password");
        LoginResponseDTO response = new LoginResponseDTO("jwt_token", "user-id");

        when(loginService.login("test@test.com", "password")).thenReturn(response);

        mockMvc.perform(post("/login/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt_token"))
                .andExpect(jsonPath("$.userId").value("user-id"));
    }
}
