package nl.tudelft.serg.slrcrawler.storage;

import nl.tudelft.serg.slrcrawler.HtmlPage;

public interface HtmlPageStorage {
    void store(HtmlPage htmlPage);
}
