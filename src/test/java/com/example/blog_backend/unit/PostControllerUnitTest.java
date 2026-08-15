package com.example.blog_backend.unit;

import com.example.blog_backend.modules.post.Post;
import com.example.blog_backend.modules.post.PostController;
import com.example.blog_backend.modules.post.PostService;
import com.example.blog_backend.modules.post.dto.PostResponseDTO;
import com.example.blog_backend.modules.post.enums.PostStatusEnum;
import com.example.blog_backend.modules.user.User;
import com.example.blog_backend.modules.user.enums.UserEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigInteger;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class PostControllerUnitTest {

    @Mock
    private PostService postService;

    @InjectMocks
    private PostController postController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_ShouldReturnPostList() {
        UUID authorId = UUID.randomUUID();
        User author = new User(authorId, "Author Name", "author@test.com", UserEnum.AUTHOR, "password", new Date(), new Date());
        UUID postId = UUID.randomUUID();
        Post post = new Post(postId, "Title", "Short Desc", "Content", author, Collections.singletonList("tag"), "Category", "slug", PostStatusEnum.PUBLISHED, BigInteger.ZERO, new Date(), new Date());

        when(postService.findAll()).thenReturn(Collections.singletonList(post));

        ResponseEntity<List<PostResponseDTO>> response = postController.findAll();

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
        assertEquals("Title", response.getBody().get(0).title());
        verify(postService, times(1)).findAll();
    }


    @Test
    void create_ShouldReturnCreatedPost() {
        UUID authorId = UUID.randomUUID();
        User author = new User(authorId, "Author Name", "author@test.com", UserEnum.AUTHOR, "password", new Date(), new Date());
        UUID postId = UUID.randomUUID();
        Post post = new Post(null, "Title", "Short Desc", "Content", author, Collections.singletonList("tag"), "Category", "slug", PostStatusEnum.PUBLISHED, BigInteger.ZERO, null, null);
        Post createdPost = new Post(postId, "Title", "Short Desc", "Content", author, Collections.singletonList("tag"), "Category", "slug", PostStatusEnum.PUBLISHED, BigInteger.ZERO, new Date(), new Date());

        when(postService.create(post)).thenReturn(createdPost);

        ResponseEntity<PostResponseDTO> response = postController.create(post);

        assertNotNull(response);
        assertEquals(201, response.getStatusCode().value());
        assertEquals("Title", response.getBody().title());
        verify(postService, times(1)).create(post);
    }

    @Test
    void findById_ShouldReturnPost() {
        UUID authorId = UUID.randomUUID();
        User author = new User(authorId, "Author Name", "author@test.com", UserEnum.AUTHOR, "password", new Date(), new Date());
        UUID postId = UUID.randomUUID();
        Post post = new Post(postId, "Title", "Short Desc", "Content", author, Collections.singletonList("tag"), "Category", "slug", PostStatusEnum.PUBLISHED, BigInteger.ZERO, new Date(), new Date());

        when(postService.findById(postId)).thenReturn(post);

        ResponseEntity<PostResponseDTO> response = postController.findById(postId);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Title", response.getBody().title());
        verify(postService, times(1)).findById(postId);
    }

    @Test
    void findBySlug_ShouldReturnPost() {
        UUID authorId = UUID.randomUUID();
        User author = new User(authorId, "Author Name", "author@test.com", UserEnum.AUTHOR, "password", new Date(), new Date());
        UUID postId = UUID.randomUUID();
        Post post = new Post(postId, "Title", "Short Desc", "Content", author, Collections.singletonList("tag"), "Category", "slug", PostStatusEnum.PUBLISHED, BigInteger.ZERO, new Date(), new Date());

        when(postService.findBySlug("slug")).thenReturn(post);

        ResponseEntity<PostResponseDTO> response = postController.findBySlug("slug");

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Title", response.getBody().title());
        verify(postService, times(1)).findBySlug("slug");
    }

    @Test
    void findByIdAndAuthorId_ShouldReturnPostList() {
        UUID authorId = UUID.randomUUID();
        User author = new User(authorId, "Author Name", "author@test.com", UserEnum.AUTHOR, "password", new Date(), new Date());
        UUID postId = UUID.randomUUID();
        Post post = new Post(postId, "Title", "Short Desc", "Content", author, Collections.singletonList("tag"), "Category", "slug", PostStatusEnum.PUBLISHED, BigInteger.ZERO, new Date(), new Date());

        when(postService.findByIdAndAuthorId(postId, authorId)).thenReturn(Collections.singletonList(post));

        ResponseEntity<List<PostResponseDTO>> response = postController.findByIdAndAuthorId(postId, authorId);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
        assertEquals("Title", response.getBody().get(0).title());
        verify(postService, times(1)).findByIdAndAuthorId(postId, authorId);
    }

    @Test
    void findByStatus_ShouldReturnPostList() {
        UUID authorId = UUID.randomUUID();
        User author = new User(authorId, "Author Name", "author@test.com", UserEnum.AUTHOR, "password", new Date(), new Date());
        UUID postId = UUID.randomUUID();
        Post post = new Post(postId, "Title", "Short Desc", "Content", author, Collections.singletonList("tag"), "Category", "slug", PostStatusEnum.PUBLISHED, BigInteger.ZERO, new Date(), new Date());

        when(postService.findByStatus(PostStatusEnum.PUBLISHED)).thenReturn(Collections.singletonList(post));

        ResponseEntity<List<PostResponseDTO>> response = postController.findByStatus(PostStatusEnum.PUBLISHED);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
        assertEquals("Title", response.getBody().get(0).title());
        verify(postService, times(1)).findByStatus(PostStatusEnum.PUBLISHED);
    }

    @Test
    void findByStatusAndCategory_ShouldReturnPostList() {
        UUID authorId = UUID.randomUUID();
        User author = new User(authorId, "Author Name", "author@test.com", UserEnum.AUTHOR, "password", new Date(), new Date());
        UUID postId = UUID.randomUUID();
        Post post = new Post(postId, "Title", "Short Desc", "Content", author, Collections.singletonList("tag"), "Category", "slug", PostStatusEnum.PUBLISHED, BigInteger.ZERO, new Date(), new Date());

        when(postService.findByStatusAndCategory(PostStatusEnum.PUBLISHED, "Category")).thenReturn(Collections.singletonList(post));

        ResponseEntity<List<PostResponseDTO>> response = postController.findByStatusAndCategory(PostStatusEnum.PUBLISHED, "Category");

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
        assertEquals("Title", response.getBody().get(0).title());
        verify(postService, times(1)).findByStatusAndCategory(PostStatusEnum.PUBLISHED, "Category");
    }
}
