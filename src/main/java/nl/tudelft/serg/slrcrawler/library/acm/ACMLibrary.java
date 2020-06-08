package nl.tudelft.serg.slrcrawler.library.acm;

import nl.tudelft.serg.slrcrawler.library.Library;
import nl.tudelft.serg.slrcrawler.library.LibraryCrawler;
import nl.tudelft.serg.slrcrawler.library.LibraryParser;
import org.openqa.selenium.WebDriver;

public class ACMLibrary implements Library {

    public static final String NAME = "acm";
    private final WebDriver driver;

    public ACMLibrary(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public LibraryCrawler crawler() {
        return new ACMCrawler(driver);
    }

    @Override
    public LibraryParser parser() {
        return new ACMParser();
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
