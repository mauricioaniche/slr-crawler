package nl.tudelft.serg.slrcrawler.library.scholar;

import nl.tudelft.serg.slrcrawler.library.Library;
import nl.tudelft.serg.slrcrawler.library.LibraryCrawler;
import nl.tudelft.serg.slrcrawler.library.LibraryParser;
import org.openqa.selenium.WebDriver;

public class GoogleScholarLibrary implements Library {

    public static final String NAME = "scholar";
    private final WebDriver driver;

    public GoogleScholarLibrary(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public LibraryCrawler crawler() {
        return new GoogleScholarCrawler(driver);
    }

    @Override
    public LibraryParser parser() {
        return new GoogleScholarParser();
    }

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public int elementsPerPage() {
        return 10;
    }

}
