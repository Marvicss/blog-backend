package com.example.blog_backend.modules.post;

import com.example.blog_backend.modules.post.dto.PostResponseDTO;
import com.example.blog_backend.modules.post.enums.PostStatusEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.example.blog_backend.modules.post.mapper.PostMapper.mapPostListToPostResponseDTO;
import static com.example.blog_backend.modules.post.mapper.PostMapper.mapPostToPostResponseDTO;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
    }

    @GetMapping("/")
    public ResponseEntity<List<PostResponseDTO>> findAll(){
        List<Post> posts = postService.findAll();

        List<PostResponseDTO> postsResponse = mapPostListToPostResponseDTO(posts);

        return ResponseEntity.ok(postsResponse);
    }

    @PostMapping("/")
    public ResponseEntity<PostResponseDTO> create(@RequestBody Post post){
        Post createdPost =  postService.create(post);

        PostResponseDTO postResponse = mapPostToPostResponseDTO(post);

        return ResponseEntity.status(201).body(postResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDTO> findById(@PathVariable UUID id){
        Post post = postService.findById(id);

        PostResponseDTO postResponse = mapPostToPostResponseDTO(post);

        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<PostResponseDTO> findBySlug(@PathVariable String slug){
        Post post = postService.findBySlug(slug);

        PostResponseDTO postResponse = mapPostToPostResponseDTO(post);

        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("/{id}/author/{authorId}")
    public ResponseEntity<List<PostResponseDTO>> findByIdAndAuthorId(@PathVariable UUID id, @PathVariable UUID authorId){
        List<Post> posts = postService.findByIdAndAuthorId(id, authorId);

        List<PostResponseDTO> postsResponse = mapPostListToPostResponseDTO(posts);

        return ResponseEntity.ok(postsResponse);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<PostResponseDTO>> findByStatus(@PathVariable PostStatusEnum status){
        List<Post> posts = postService.findByStatus(status);

        List<PostResponseDTO> postsResponse = mapPostListToPostResponseDTO(posts);

        return ResponseEntity.ok(postsResponse);
    }

    @GetMapping("/status/{status}/category/{category}")
    public ResponseEntity<List<PostResponseDTO>> findByStatusAndCategory(@PathVariable PostStatusEnum status, @PathVariable String category){
        List<Post> posts = postService.findByStatusAndCategory(status, category);

        List<PostResponseDTO> postsResponse = mapPostListToPostResponseDTO(posts);

        return ResponseEntity.ok(postsResponse);
    }
}
