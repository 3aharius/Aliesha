package by.aliesha.exception;

public class PageNotFoundException extends Exception {
    
    private static final long serialVersionUID = 3445926291119146115L;
    private String message;
    
    public PageNotFoundException(String message) {
        this.message = message;
    }
    
    @Override
    public String getMessage() {
        return message;
    }

}
