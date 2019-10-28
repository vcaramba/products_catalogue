package products_catalogue.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import products_catalogue.exceptions.CategoryNotFoundException;
import products_catalogue.persistence.Category;
import products_catalogue.dao.CategoryRepository;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/categories")
public class CategoryController {
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping(produces = "application/json")
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public Category getCategory(@PathVariable long id) {
        Optional<Category> category = categoryRepository.findById(id);

        if (!category.isPresent())
            throw new CategoryNotFoundException("Category with id " + id + " not found");

        return category.get();
    }

    @DeleteMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public void deleteCategory(@PathVariable long id) {
        categoryRepository.deleteById(id);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> createCategory(@RequestBody Category category) {
        Category savedCategory = categoryRepository.save(category);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedCategory.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> updateCategory(@RequestBody Category category, @PathVariable long id) {

        Optional<Category> categoryOptional = categoryRepository.findById(id);

        if (!categoryOptional.isPresent())
            return ResponseEntity.notFound().build();

        category.setId(id);

        categoryRepository.save(category);

        return ResponseEntity.noContent().build();
    }
}
