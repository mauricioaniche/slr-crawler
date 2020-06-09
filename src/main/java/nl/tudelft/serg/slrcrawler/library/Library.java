package nl.tudelft.serg.slrcrawler.library;

import static java.lang.Math.max;

public interface Library {

    LibraryCrawler crawler();
    LibraryParser parser();
    String name();

    int elementsPerPage();

    default int lastPageInclusive(int zeroBasedLastElement) {
        return max(0, zeroBasedLastElement) / elementsPerPage();
    }

    default int firstPageInclusive(int zeroBasedFirstElement) {
        return max(0, zeroBasedFirstElement) / elementsPerPage();
    }
}
