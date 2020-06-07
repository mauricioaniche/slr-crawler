package nl.tudelft.serg.slrcrawler;

import java.util.List;

public interface Crawler {
    List<PaperEntry> crawl(String keywords, int noOfPages);
}
