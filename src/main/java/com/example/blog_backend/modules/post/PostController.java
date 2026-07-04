package com.example.blog_backend.modules.post;

import com.example.blog_backend.modules.post.dto.PostResponseDTO;
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
}
