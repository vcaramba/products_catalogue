package ee.products_catalogue.restful.controller;

import ee.products_catalogue.restful.exceptions.ProductNotFoundException;
import ee.products_catalogue.restful.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/products")
    public List<Product> retrieveAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/products/{id}")
    public Product retrieveProduct(@PathVariable long id) {
        Optional<Product> product = productRepository.findById(id);

        if (!product.isPresent())
            throw new ProductNotFoundException("Product with id " + id + " not found");

        return product.get();
    }

    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable long id) {
        productRepository.deleteById(id);
    }

    @PostMapping("/products")
    public ResponseEntity<Object> createProduct(@RequestBody Product product) {
        Product savedProduct = productRepository.save(product);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedProduct.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProduct(@RequestBody Product product, @PathVariable long id) {

        Optional<Product> productOptional = productRepository.findById(id);

        if (!productOptional.isPresent())
            return ResponseEntity.notFound().build();

        product.setId(id);

        productRepository.save(product);

        return ResponseEntity.noContent().build();
    }
}
