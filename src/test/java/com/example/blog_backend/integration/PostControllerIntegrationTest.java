package com.example.blog_backend.integration;

import com.example.blog_backend.config.SecurityConfig;
import com.example.blog_backend.modules.login.LoginService;
import com.example.blog_backend.modules.post.Post;
import com.example.blog_backend.modules.post.PostController;
import com.example.blog_backend.modules.post.PostService;
import com.example.blog_backend.modules.post.enums.PostStatusEnum;
import com.example.blog_backend.modules.user.User;
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

import java.math.BigInteger;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
@Import(SecurityConfig.class)
class PostControllerIntegrationTest {

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
    private PostService postService;

    @MockitoBean
    private LoginService loginService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void findAll_WithoutAuthentication_ShouldBeAllowed() throws Exception {
        when(postService.findAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/posts/"))
                .andExpect(status().isOk());
    }


    @Test
    void findById_WithoutAuthentication_ShouldBeAllowed() throws Exception {
        UUID postId = UUID.randomUUID();
        User author = new User(UUID.randomUUID(), "Author", "author@test.com", UserEnum.AUTHOR, "password", new Date(), new Date());
        Post post = new Post(postId, "Title", "Short Desc", "Content", author, Collections.singletonList("tag"), "Category", "slug", PostStatusEnum.PUBLISHED, BigInteger.ZERO, new Date(), new Date());
        when(postService.findById(postId)).thenReturn(post);

        mockMvc.perform(get("/posts/" + postId))
                .andExpect(status().isOk());
    }

    @Test
    void create_WithoutAuthentication_ShouldBeForbidden() throws Exception {
        User author = new User(UUID.randomUUID(), "Author", "author@test.com", UserEnum.AUTHOR, "password", new Date(), new Date());
        Post post = new Post(null, "Title", "Short Desc", "Content", author, Collections.singletonList("tag"), "Category", "slug", PostStatusEnum.PUBLISHED, BigInteger.ZERO, null, null);

        mockMvc.perform(post("/posts/")
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(post)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "READER")
    void create_WithReaderRole_ShouldBeForbidden() throws Exception {
        User author = new User(UUID.randomUUID(), "Author", "author@test.com", UserEnum.AUTHOR, "password", new Date(), new Date());
        Post post = new Post(null, "Title", "Short Desc", "Content", author, Collections.singletonList("tag"), "Category", "slug", PostStatusEnum.PUBLISHED, BigInteger.ZERO, null, null);

        mockMvc.perform(post("/posts/")
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(post)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void create_WithAdminRole_ShouldBeAllowed() throws Exception {
        UUID postId = UUID.randomUUID();
        User author = new User(UUID.randomUUID(), "Author", "author@test.com", UserEnum.AUTHOR, "password", new Date(), new Date());
        Post post = new Post(null, "Title", "Short Desc", "Content", author, Collections.singletonList("tag"), "Category", "slug", PostStatusEnum.PUBLISHED, BigInteger.ZERO, null, null);
        Post createdPost = new Post(postId, "Title", "Short Desc", "Content", author, Collections.singletonList("tag"), "Category", "slug", PostStatusEnum.PUBLISHED, BigInteger.ZERO, new Date(), new Date());
        when(postService.create(any(Post.class))).thenReturn(createdPost);

        mockMvc.perform(post("/posts/")
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(post)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "AUTHOR")
    void create_WithAuthorRole_ShouldBeAllowed() throws Exception {
        UUID postId = UUID.randomUUID();
        User author = new User(UUID.randomUUID(), "Author", "author@test.com", UserEnum.AUTHOR, "password", new Date(), new Date());
        Post post = new Post(null, "Title", "Short Desc", "Content", author, Collections.singletonList("tag"), "Category", "slug", PostStatusEnum.PUBLISHED, BigInteger.ZERO, null, null);
        Post createdPost = new Post(postId, "Title", "Short Desc", "Content", author, Collections.singletonList("tag"), "Category", "slug", PostStatusEnum.PUBLISHED, BigInteger.ZERO, new Date(), new Date());
        when(postService.create(any(Post.class))).thenReturn(createdPost);

        mockMvc.perform(post("/posts/")
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(post)))
                .andExpect(status().isCreated());
    }
}
