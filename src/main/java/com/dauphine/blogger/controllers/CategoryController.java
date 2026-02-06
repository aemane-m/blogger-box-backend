package com.dauphine.blogger.controllers;

import com.dauphine.blogger.dto.CategoryCreateRequest;
import com.dauphine.blogger.dto.CategoryPatchRequest;
import com.dauphine.blogger.dto.CategoryResponse;
import com.dauphine.blogger.dto.CategoryUpdateRequest;
import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // GET /v1/categories?name=foo
    @GetMapping
    public List<CategoryResponse> getAll(@RequestParam(required = false) String name) {
        return categoryService.getAll(Optional.ofNullable(name)).stream()
                .map(c -> new CategoryResponse(c.getId(), c.getName()))
                .toList();
    }

    // GET /v1/categories/{id}
    @GetMapping("/{id}")
    public CategoryResponse getById(@PathVariable UUID id) {
        Category c = categoryService.getById(id);
        return new CategoryResponse(c.getId(), c.getName());
    }

    // POST /v1/categories
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CategoryResponse create(@RequestBody CategoryCreateRequest req) {
        Category c = categoryService.create(req.name());
        return new CategoryResponse(c.getId(), c.getName());
    }

    // PUT /v1/categories/{id}
    @PutMapping("/{id}")
    public CategoryResponse update(@PathVariable UUID id, @RequestBody CategoryUpdateRequest req) {
        Category c = categoryService.update(id, req.name());
        return new CategoryResponse(c.getId(), c.getName());
    }

    // PATCH /v1/categories/{id}
    @PatchMapping("/{id}")
    public CategoryResponse patch(@PathVariable UUID id, @RequestBody CategoryPatchRequest req) {
        Category c = categoryService.patch(id, req.name());
        return new CategoryResponse(c.getId(), c.getName());
    }

    // DELETE /v1/categories/{id}
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        categoryService.delete(id);
    }
}