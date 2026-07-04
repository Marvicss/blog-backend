package com.example.blog_backend.modules.post;

import java.util.List;
import java.util.UUID;

import com.example.blog_backend.modules.post.enums.PostStatusEnum;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    Post findBySlug(String slug);

    List<Post> findByIdAndAuthorId(UUID id, UUID authorId);

    List<Post> findByStatus(PostStatusEnum status);

    List<Post> findByStatusAndCategory(PostStatusEnum status, String category);

    @Transactional
    @Modifying
    @Query("UPDATE Post p SET p.views = p.views + 1 WHERE p.id = :postId")
    void incrementViews(@Param("postId") UUID postId);
    
}
