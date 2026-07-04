package com.example.blog_backend.integration;

import com.example.blog_backend.config.SecurityConfig;
import com.example.blog_backend.modules.login.LoginService;
import com.example.blog_backend.modules.user.User;
import com.example.blog_backend.modules.user.UserController;
import com.example.blog_backend.modules.user.UserService;
import com.example.blog_backend.modules.user.enums.UserEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Date;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
class UserControllerIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private org.springframework.web.context.WebApplicationContext context;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        this.mockMvc = org.springframework.test.web.servlet.setup.MockMvcBuilders
                .webAppContextSetup(context)
                .apply(org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private LoginService loginService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void findAll_WithoutAuthentication_ShouldBeAllowed() throws Exception {
        when(userService.findAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/user/"))
                .andExpect(status().isOk());
    }

    @Test
    void findById_WithoutAuthentication_ShouldBeAllowed() throws Exception {
        UUID id = UUID.randomUUID();
        User user = new User(id, "Test User", "test@test.com", UserEnum.READER, "password", new Date(), new Date());
        when(userService.findById(id)).thenReturn(user);

        mockMvc.perform(get("/user/" + id))
                .andExpect(status().isOk());
    }

    @Test
    void create_WithoutAuthentication_ShouldBeForbidden() throws Exception {
        User user = new User(null, "Test User", "test@test.com", UserEnum.READER, "password", null, null);
        mockMvc.perform(post("/user/")
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "READER")
    void create_WithReaderRole_ShouldBeForbidden() throws Exception {
        User user = new User(null, "Test User", "test@test.com", UserEnum.READER, "password", null, null);
        mockMvc.perform(post("/user/")
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void create_WithAdminRole_ShouldBeAllowed() throws Exception {
        UUID id = UUID.randomUUID();
        User user = new User(null, "Test User", "test@test.com", UserEnum.READER, "password", null, null);
        User createdUser = new User(id, "Test User", "test@test.com", UserEnum.READER, "password", new Date(), new Date());
        when(userService.create(any(User.class))).thenReturn(createdUser);

        mockMvc.perform(post("/user/")
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "AUTHOR")
    void create_WithAuthorRole_ShouldBeAllowed() throws Exception {
        UUID id = UUID.randomUUID();
        User user = new User(null, "Test User", "test@test.com", UserEnum.READER, "password", null, null);
        User createdUser = new User(id, "Test User", "test@test.com", UserEnum.READER, "password", new Date(), new Date());
        when(userService.create(any(User.class))).thenReturn(createdUser);

        mockMvc.perform(post("/user/")
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());
    }
}
