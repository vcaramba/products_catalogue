package ee.products_catalogue.restful.exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String exception) {
        super(exception);
    }
}
