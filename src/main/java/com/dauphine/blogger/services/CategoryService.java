package com.dauphine.blogger.services;

import com.dauphine.blogger.models.Category;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CategoryService {

    private final Map<UUID, Category> store = new ConcurrentHashMap<>();

    public List<Category> getAll(Optional<String> nameFilter) {
        return store.values().stream()
                .filter(c -> nameFilter
                        .map(f -> c.getName() != null && c.getName().toLowerCase().contains(f.toLowerCase()))
                        .orElse(true))
                .sorted(Comparator.comparing(Category::getName, Comparator.nullsLast(String::compareToIgnoreCase)))
                .toList();
    }

    public Category getById(UUID id) {
        Category c = store.get(id);
        if (c == null) throw new NoSuchElementException("Category not found: " + id);
        return c;
    }

    public Category create(String name) {
        UUID id = UUID.randomUUID();
        Category c = new Category(id, name);
        store.put(id, c);
        return c;
    }

    // PUT = remplacement / update complet (ici juste name)
    public Category update(UUID id, String name) {
        Category c = getById(id);
        c.setName(name);
        store.put(id, c);
        return c;
    }

    // PATCH = update partiel (null => pas modifié)
    public Category patch(UUID id, String name) {
        Category c = getById(id);
        if (name != null) c.setName(name);
        store.put(id, c);
        return c;
    }

    public void delete(UUID id) {
        if (store.remove(id) == null) {
            throw new NoSuchElementException("Category not found: " + id);
        }
    }
}