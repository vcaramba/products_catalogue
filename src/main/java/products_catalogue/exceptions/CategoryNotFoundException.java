package products_catalogue.exceptions;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String exception) {
        super(exception);
    }
}
