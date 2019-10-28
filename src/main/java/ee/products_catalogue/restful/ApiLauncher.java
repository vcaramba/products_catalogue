package ee.products_catalogue.restful;

import ee.products_catalogue.restful.controller.CategoryRepository;
import ee.products_catalogue.restful.controller.ProductRepository;
import ee.products_catalogue.restful.model.Category;
import ee.products_catalogue.restful.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//public class ApiLauncher implements CommandLineRunner {
public class ApiLauncher {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public static void main(String[] args) {
        SpringApplication.run(ApiLauncher.class, args);
    }

//    @Override
//    public void run(String... args) {
//        productRepository.deleteAllInBatch();
//        categoryRepository.deleteAllInBatch();
//
//
//        Product product = new Product("Hoody");
//
//        Category category1 = new Category("Women");
//        Category category2 = new Category("Men");
//
//        product.addCategory(category1);
//        product.addCategory(category2);
//
//        productRepository.save(product);
//
//    }

}
