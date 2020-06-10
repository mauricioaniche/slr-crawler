package nl.tudelft.serg.slrcrawler.library.springer;

import nl.tudelft.serg.slrcrawler.library.Library;
import nl.tudelft.serg.slrcrawler.library.LibraryCrawler;
import nl.tudelft.serg.slrcrawler.library.LibraryParser;
import org.openqa.selenium.WebDriver;

public class SpringerLibrary implements Library {

    public static final String NAME = "springer";
    private final WebDriver driver;

    public SpringerLibrary(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public LibraryCrawler crawler() {
        return new SpringerCrawler(driver);
    }

    @Override
    public LibraryParser parser() {
        return new SpringerParser();
    }

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public int elementsPerPage() {
        return 18;
    }
}
