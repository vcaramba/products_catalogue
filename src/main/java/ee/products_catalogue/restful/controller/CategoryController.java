package ee.products_catalogue.restful.controller;

import ee.products_catalogue.restful.exceptions.CategoryNotFoundException;
import ee.products_catalogue.restful.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/categories")
    public List<Category> retrieveAllCategories() {
        return categoryRepository.findAll();
    }

    @GetMapping("/categories/{id}")
    public Category retrieveCategory(@PathVariable long id) {
        Optional<Category> category = categoryRepository.findById(id);

        if (!category.isPresent())
            throw new CategoryNotFoundException("Category with id " + id + " not found");

        return category.get();
    }

    @DeleteMapping("/categories/{id}")
    public void deleteCategory(@PathVariable long id) {
        categoryRepository.deleteById(id);
    }

    @PostMapping("/categories")
    public ResponseEntity<Object> createCategory(@RequestBody Category category) {
        Category savedCategory = categoryRepository.save(category);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedCategory.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<Object> updateCategory(@RequestBody Category category, @PathVariable long id) {

        Optional<Category> categoryOptional = categoryRepository.findById(id);

        if (!categoryOptional.isPresent())
            return ResponseEntity.notFound().build();

        category.setId(id);

        categoryRepository.save(category);

        return ResponseEntity.noContent().build();
    }
}
