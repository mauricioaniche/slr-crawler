package nl.tudelft.serg.slrcrawler.library.springer;

import nl.tudelft.serg.slrcrawler.library.Library;
import nl.tudelft.serg.slrcrawler.library.LibraryCrawler;
import nl.tudelft.serg.slrcrawler.library.LibraryParser;
import org.openqa.selenium.WebDriver;

public class SpringerLibrary implements Library {

    public static final String NAME = "springer";
    private final WebDriver driver;
    private final SpringerConfig config;

    public SpringerLibrary(WebDriver driver, SpringerConfig config) {
        this.driver = driver;
        this.config = config;
    }

    @Override
    public LibraryCrawler crawler() {
        return new SpringerCrawler(driver, config);
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
        return 20;
    }
}
