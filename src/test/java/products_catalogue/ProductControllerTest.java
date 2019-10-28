package products_catalogue;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import products_catalogue.controller.ProductController;
import products_catalogue.dao.ProductRepository;
import products_catalogue.persistence.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {

    @Mock
    ProductRepository productRepository;
    @InjectMocks
    private ProductController productController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        productController = new ProductController(productRepository);
    }


    @Test
    public void testAddProduct() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Product product = new Product();
        product.setId(1L);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product productToAdd = new Product("Dress");
        ResponseEntity<Object> responseEntity = productController.createProduct(productToAdd);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/1");
    }

    @Test
    public void testGetProduct() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Product product = new Product();
        product.setId(1L);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product productToAdd = new Product("Dress");
        productController.createProduct(productToAdd);
        List<Product> list = new ArrayList<>(Collections.singletonList(product));

        when(productRepository.findAll()).thenReturn(list);

        List<Product> result = productController.getAllProducts();
        assertThat(result.size()).isEqualTo(1);

        assertThat(productRepository.findById(result.get(0).getId()).isPresent());
    }

    @Test
    public void testDeleteProduct() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Product productToDelete = new Product();
        productToDelete.setId(1L);
        productRepository.save(productToDelete);
        productController.deleteProduct(1L);
        assertThat(!productRepository.findById(1L).isPresent());
    }

    @Test
    public void testUpdateProduct() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Product productBefore = new Product("Before update");
        productBefore.setId(1L);
        Product productAfter = new Product("After update");

        List<Product> list = new ArrayList<>(Arrays.asList(productBefore, productAfter));
        when(productRepository.findAll()).thenReturn(list);

        List<Product> result = productController.getAllProducts();

        assertThat(productRepository.findById(result.get(0).getId()).isPresent());
        assertThat(productRepository.findById(result.get(1).getId()).isPresent());


        productController.updateProduct(productAfter, productBefore.getId());
//        assertThat(productRepository.findById(result.get(0).getId()).get().getName().equals(productAfter.getName()));
    }

    @Test
    public void testGetAllProducts() {
        Product product1 = new Product("Scarf");
        Product product2 = new Product("T-shirt");
        List<Product> list = new ArrayList<>(Arrays.asList(product1, product2));

        when(productRepository.findAll()).thenReturn(list);

        List<Product> result = productController.getAllProducts();
        assertThat(result.size()).isEqualTo(2);

        assertThat(result.get(0).getName()).isEqualTo(product1.getName());
        assertThat(result.get(1).getName()).isEqualTo(product2.getName());

        assertThat(productRepository.findById(result.get(0).getId()).isPresent());
        assertThat(productRepository.findById(result.get(1).getId()).isPresent());
    }

}
