package nl.tudelft.serg.slrcrawler.library;

import nl.tudelft.serg.slrcrawler.HtmlPage;
import nl.tudelft.serg.slrcrawler.PaperEntry;

import java.util.List;

public interface LibraryParser {
    List<PaperEntry> parse(HtmlPage htmlPage);
}
