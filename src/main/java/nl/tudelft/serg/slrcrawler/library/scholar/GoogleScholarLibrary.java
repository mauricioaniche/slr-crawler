package nl.tudelft.serg.slrcrawler.library.scholar;

import nl.tudelft.serg.slrcrawler.library.Library;
import nl.tudelft.serg.slrcrawler.library.LibraryCrawler;
import nl.tudelft.serg.slrcrawler.library.LibraryParser;

public class GoogleScholarLibrary implements Library {

    public static final String NAME = "scholar";

    @Override
    public LibraryCrawler crawler() {
        return new GoogleScholarCrawler();
    }

    @Override
    public LibraryParser parser() {
        return new GoogleScholarParser();
    }

    @Override
    public String name() {
        return NAME;
    }
}
