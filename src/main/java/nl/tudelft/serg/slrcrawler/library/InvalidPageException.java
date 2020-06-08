package nl.tudelft.serg.slrcrawler.library;

public class InvalidPageException extends RuntimeException {
    public InvalidPageException(Exception e) {
        super(e);
    }
}
