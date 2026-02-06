package com.dauphine.blogger.controllers;

import com.dauphine.blogger.dto.CategoryResponse;
import com.dauphine.blogger.dto.PostCreateRequest;
import com.dauphine.blogger.dto.PostResponse;
import com.dauphine.blogger.dto.PostUpdateRequest;
import com.dauphine.blogger.models.Post;
import com.dauphine.blogger.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // ● Retrieve all posts ordered by creation date
    @GetMapping
    public List<PostResponse> getAllPosts() {
        return postService.getAllOrderedByCreationDate().stream()
                .map(this::toResponse)
                .toList();
    }

    // ● Create a new post
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public PostResponse create(@RequestBody PostCreateRequest req) {
        return toResponse(
                postService.create(req.title(), req.content(), req.categoryId())
        );
    }

    // ● Update an existing post
    @PutMapping("/{id}")
    public PostResponse update(
            @PathVariable UUID id,
            @RequestBody PostUpdateRequest req
    ) {
        return toResponse(
                postService.update(id, req.title(), req.content(), req.categoryId())
        );
    }

    // ● Delete an existing post
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        postService.delete(id);
    }

    // ● Retrieve all posts per a category
    @GetMapping("/by-category/{categoryId}")
    public List<PostResponse> getByCategory(@PathVariable UUID categoryId) {
        return postService.getAllByCategory(categoryId).stream()
                .map(this::toResponse)
                .toList();
    }

    private PostResponse toResponse(Post p) {
        return new PostResponse(
                p.getId(),
                p.getTitle(),
                p.getContent(),
                p.getCreatedDate(),
                new CategoryResponse(
                        p.getCategory().getId(),
                        p.getCategory().getName()
                )
        );
    }
}