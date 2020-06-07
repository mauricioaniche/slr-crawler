package nl.tudelft.serg.slrcrawler.library;

public interface Library {

    LibraryCrawler crawler();
    LibraryParser parser();
    String name();
}
