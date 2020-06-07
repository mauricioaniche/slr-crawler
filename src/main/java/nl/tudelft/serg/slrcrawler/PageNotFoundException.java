package nl.tudelft.serg.slrcrawler;

public class PageNotFoundException extends Exception {

    public PageNotFoundException(Exception e, String library, String keyword, int page) {
        super(String.format("Page not found: library=%s, keyword=%s, page=%d",
                library,
                keyword,
                page),
            e);
    }

}
