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
import products_catalogue.controller.CategoryController;
import products_catalogue.dao.CategoryRepository;
import products_catalogue.persistence.Category;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CategoryControllerTest {

    @Mock
    CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryController categoryController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        categoryController = new CategoryController(categoryRepository);
    }


    @Test
    public void testAddCategory() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Category category = new Category();
        category.setId(1L);
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        Category categoryToAdd = new Category("Men");
        ResponseEntity<Object> responseEntity = categoryController.createCategory(categoryToAdd);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/1");
    }

    @Test
    public void testGetCategory() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Category category = new Category();
        category.setId(1L);
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        Category categoryToAdd = new Category("Women");
        categoryController.createCategory(categoryToAdd);
        List<Category> list = new ArrayList<>(Collections.singletonList(category));

        when(categoryRepository.findAll()).thenReturn(list);

        List<Category> result = categoryController.getAllCategories();
        assertThat(result.size()).isEqualTo(1);

        assertThat(categoryRepository.findById(result.get(0).getId()).isPresent());
    }

    @Test
    public void testDeleteCategory() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Category categoryToDelete = new Category();
        categoryToDelete.setId(1L);
        categoryRepository.save(categoryToDelete);
        categoryController.deleteCategory(1L);
        assertThat(!categoryRepository.findById(1L).isPresent());
    }

    @Test
    public void testUpdateCategory() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Category categoryBefore = new Category("Before update");
        categoryBefore.setId(1L);
        Category categoryAfter = new Category("After update");

        List<Category> list = new ArrayList<>(Arrays.asList(categoryBefore, categoryAfter));
        when(categoryRepository.findAll()).thenReturn(list);

        List<Category> result = categoryController.getAllCategories();

        assertThat(categoryRepository.findById(result.get(0).getId()).isPresent());
        assertThat(categoryRepository.findById(result.get(1).getId()).isPresent());


        categoryController.updateCategory(categoryAfter, categoryBefore.getId());
//        assertThat(productRepository.findById(result.get(0).getId()).get().getName().equals(productAfter.getName()));
    }

    @Test
    public void testGetAllCategories() {
        Category category1 = new Category("Men");
        Category category2 = new Category("Women");
        List<Category> list = new ArrayList<>(Arrays.asList(category1, category2));

        when(categoryRepository.findAll()).thenReturn(list);

        List<Category> result = categoryController.getAllCategories();
        assertThat(result.size()).isEqualTo(2);

        assertThat(result.get(0).getName()).isEqualTo(category1.getName());
        assertThat(result.get(1).getName()).isEqualTo(category2.getName());

        assertThat(categoryRepository.findById(result.get(0).getId()).isPresent());
        assertThat(categoryRepository.findById(result.get(1).getId()).isPresent());
    }
}
