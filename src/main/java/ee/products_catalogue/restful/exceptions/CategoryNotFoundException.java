package ee.products_catalogue.restful.exceptions;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String exception) {
        super(exception);
    }
}
