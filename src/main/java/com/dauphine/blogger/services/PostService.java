package com.dauphine.blogger.services;

import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.models.Post;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PostService {

    private final Map<UUID, Post> store = new ConcurrentHashMap<>();
    private final CategoryService categoryService;

    public PostService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public Post create(String title, String content, UUID categoryId) {
        Category category = categoryService.getById(categoryId);

        Post post = new Post(
                UUID.randomUUID(),
                title,
                content,
                LocalDateTime.now(),
                category
        );

        store.put(post.getId(), post);
        return post;
    }

    public Post update(UUID id, String title, String content, UUID categoryId) {
        Post post = getById(id);
        post.setTitle(title);
        post.setContent(content);
        post.setCategory(categoryService.getById(categoryId));
        return post;
    }

    public void delete(UUID id) {
        if (store.remove(id) == null) {
            throw new NoSuchElementException("Post not found: " + id);
        }
    }

    public List<Post> getAllOrderedByCreationDate() {
        return store.values().stream()
                .sorted(Comparator.comparing(Post::getCreatedDate).reversed())
                .toList();
    }

    public List<Post> getAllByCategory(UUID categoryId) {
        return store.values().stream()
                .filter(p -> p.getCategory().getId().equals(categoryId))
                .sorted(Comparator.comparing(Post::getCreatedDate).reversed())
                .toList();
    }

    public Post getById(UUID id) {
        Post post = store.get(id);
        if (post == null) {
            throw new NoSuchElementException("Post not found: " + id);
        }
        return post;
    }
}