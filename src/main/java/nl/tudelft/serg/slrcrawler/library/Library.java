package nl.tudelft.serg.slrcrawler.library;

public interface Library {

    LibraryCrawler crawler();
    LibraryParser parser();
    String name();

    int elementsPerPage();

    default int lastPage(int maxNoOfElements) {
        return maxNoOfElements / elementsPerPage() + (maxNoOfElements % elementsPerPage() > 0 ? 1 : 0);
    }

    default int firstPage(int startFrom) {
        return startFrom / elementsPerPage();
    }
}
