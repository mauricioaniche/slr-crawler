package nl.tudelft.serg.slrcrawler.library;

import nl.tudelft.serg.slrcrawler.HtmlPage;
import nl.tudelft.serg.slrcrawler.PageNotFoundException;

public interface LibraryCrawler {
    HtmlPage downloadPage(String keywords, int pageNumber) throws PageNotFoundException;
}
