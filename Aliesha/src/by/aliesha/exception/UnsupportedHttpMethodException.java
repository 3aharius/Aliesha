package by.aliesha.exception;

public class UnsupportedHttpMethodException extends Exception {

    private static final long serialVersionUID = 9138577122303196666L;
    private String message;

    public UnsupportedHttpMethodException(String message) {
        this.message = message;
    }
    
    @Override
    public String getMessage() {
        return message;
    }
    
}
