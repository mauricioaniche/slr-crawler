package nl.tudelft.serg.slrcrawler.library;

import nl.tudelft.serg.slrcrawler.HtmlPage;

public interface LibraryCrawler {
    HtmlPage downloadPage(String keywords, int pageNumber);
}
