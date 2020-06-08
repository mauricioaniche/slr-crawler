package nl.tudelft.serg.slrcrawler.library;

public class InvalidPageException extends RuntimeException {
    public InvalidPageException(String message, Exception e) {
        super(message, e);
    }
}
