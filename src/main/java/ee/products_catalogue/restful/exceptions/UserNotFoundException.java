package ee.products_catalogue.restful.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String exception) {
        super(exception);
    }
}
