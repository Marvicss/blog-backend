package com.example.blog_backend.modules.post;


import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.example.blog_backend.modules.post.enums.PostStatusEnum;
import com.example.blog_backend.modules.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "posts", indexes = {
    @Index(name = "idx_post_slug", columnList = "slug"),
    @Index(name = "idx_post_author_status", columnList = "author_id, status"),
    @Index(name = "idx_post_category_status", columnList = "category, status")
})
public class Post {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String shortDescription;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id", nullable = false, foreignKey = @ForeignKey(name = "fk_post_author"))
    private User author;

    @Column(nullable = false)
    private List<String> tags;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(nullable = false)
    private PostStatusEnum status;

    @Column(nullable = false)
    private BigInteger views = BigInteger.ZERO;

    @Column(nullable = false)
    private Date createdAt;

    @Column(nullable = false)
    private Date updatedAt;

    @PrePersist
    private void prePersist() {
        createdAt = new Date();
        updatedAt = new Date();
    }


}
