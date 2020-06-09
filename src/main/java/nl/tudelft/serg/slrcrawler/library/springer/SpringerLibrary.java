package nl.tudelft.serg.slrcrawler.library.springer;

import nl.tudelft.serg.slrcrawler.library.Library;
import nl.tudelft.serg.slrcrawler.library.LibraryCrawler;
import nl.tudelft.serg.slrcrawler.library.LibraryParser;

public class SpringerLibrary implements Library {

    public static final String NAME = "springer";

    @Override
    public LibraryCrawler crawler() {
        return null;
    }

    @Override
    public LibraryParser parser() {
        return null;
    }

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public int elementsPerPage() {
        return 20;
    }
}
