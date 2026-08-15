package com.example.blog_backend.modules.post;

import com.example.blog_backend.errors.PostNotFoundError;
import com.example.blog_backend.modules.post.enums.PostStatusEnum;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String CACHE_KEY_PREFIX = "post:";

    public PostService(PostRepository postRepository, RedisTemplate<String, Object> redisTemplate){
        this.postRepository = postRepository;
        this.redisTemplate = redisTemplate;
    }

    public List<Post> findAll(){
        String key = CACHE_KEY_PREFIX + "allPosts";

        try{
            Object cachedData = redisTemplate.opsForValue().get(key);

            if(cachedData != null){
                return (List<Post>) cachedData;
            }
        }catch (Exception e){
            System.err.println("Falha ao ler lista do Redis: " + e.getMessage());
        }

        List<Post> posts = postRepository.findAll();

        try{
            redisTemplate.opsForValue().set(key, posts, 1, TimeUnit.MINUTES);
        }catch (Exception e) {
            throw new RuntimeException("Falha ao salvar os dados no cache");
        }

        return posts;
    }

    public Post findById(UUID id){
        return postRepository.findById(id).orElseThrow();
    }

    public void incrementView(UUID postId, UUID userId){
        String key = CACHE_KEY_PREFIX + "IncrementView" + userId;

        try{
            Object cached = redisTemplate.opsForValue().get(key);

            if(cached != null){
                return;
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }

        postRepository.incrementViews(postId);

        try{
            redisTemplate.opsForValue().set(key, true, 5, TimeUnit.MINUTES);

        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Post create(Post post){
        return postRepository.save(post);
    }

    public Post findBySlug(String slug){
        Optional<Post> post =  postRepository.findBySlug(slug);

        if(post.isEmpty()){
            throw  new PostNotFoundError();
        }

        return post.get();
    }

    public List<Post> findByIdAndAuthorId(UUID id, UUID authorId){
        List<Post> posts = postRepository.findByIdAndAuthorId(id, authorId);

        if(posts.isEmpty()){
            throw new PostNotFoundError();
        }

        return posts;
    }

    public List<Post> findByStatus(PostStatusEnum status){
        List<Post> posts = postRepository.findByStatus(status);

        if(posts.isEmpty()){
            throw  new PostNotFoundError();
        }

        return posts;
    }

    public List<Post> findByStatusAndCategory(PostStatusEnum status, String category){
        List<Post> posts = postRepository.findByStatusAndCategory(status, category);

        if(posts.isEmpty()){
            throw new PostNotFoundError();
        }

        return posts;
    }


}
