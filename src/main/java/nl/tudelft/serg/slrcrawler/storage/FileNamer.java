package nl.tudelft.serg.slrcrawler.storage;

import nl.tudelft.serg.slrcrawler.HtmlPage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileNamer {

    private final boolean timeStampInFileName;

    public FileNamer() {
        this(true);
    }

    /**
     * In many situations, passing a boolean as a parameter to
     * identify a specific configuration might not be so elegant.
     * An interface with multiple implementations is better.
     * Here, because it's too simple, we keep it this way.
     */
    public FileNamer(boolean timeStampInFileName) {
        this.timeStampInFileName = timeStampInFileName;
    }

    public String name(HtmlPage htmlPage, String extension) {
        return String.format("%s-p%d%s.%s",
                replaceWhiteSpaces(htmlPage.getLibrary()),htmlPage.getPageNumber(),
                timeStampInFileName ? "-t" + timeToString(htmlPage.getTimeOfCollection()) : "",
                extension);
    }

    private String replaceWhiteSpaces(String library) {
        return library.replaceAll("\\s+","-");
    }

    private String timeToString(LocalDateTime timeOfCollection) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        return timeOfCollection.format(formatter);
    }

}
