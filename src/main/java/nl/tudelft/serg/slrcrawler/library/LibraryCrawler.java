package nl.tudelft.serg.slrcrawler.library;

import nl.tudelft.serg.slrcrawler.HtmlPage;

public interface LibraryCrawler {
    /**
     * Page number is zero-based.
     */
    HtmlPage downloadPage(String keywords, int zeroBasedPageNumber);
}
