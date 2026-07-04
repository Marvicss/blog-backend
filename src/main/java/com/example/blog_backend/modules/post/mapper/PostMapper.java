package com.example.blog_backend.modules.post.mapper;

import com.example.blog_backend.modules.post.Post;
import com.example.blog_backend.modules.post.dto.PostResponseDTO;

import java.util.List;

public class PostMapper {

    public static PostResponseDTO mapPostToPostResponseDTO(Post post){
        return new PostResponseDTO(post.getId(), post.getTitle(), post.getShortDescription(), post.getContent(), post.getAuthor().getId(), post.getTags(), post.getCategory(), post.getSlug(), post.getStatus().name(), post.getViews().intValue(), post.getCreatedAt(), post.getUpdatedAt());
    }

    public static List<PostResponseDTO> mapPostListToPostResponseDTO(List<Post> posts){
        return posts.stream().map((post -> new PostResponseDTO(post.getId(), post.getTitle(), post.getShortDescription(), post.getContent(), post.getAuthor().getId(), post.getTags(), post.getCategory(), post.getSlug(), post.getStatus().name(), post.getViews().intValue(), post.getCreatedAt(), post.getUpdatedAt()))).toList();
    }
}
