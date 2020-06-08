package nl.tudelft.serg.slrcrawler.library;

public interface Library {

    LibraryCrawler crawler();
    LibraryParser parser();
    String name();

    int elementsPerPage();

    default int pagesForMaxNumberOfElements(int maxNoOfElements) {
        return maxNoOfElements / elementsPerPage() + (maxNoOfElements % elementsPerPage() > 0 ? 1 : 0);
    }

}
