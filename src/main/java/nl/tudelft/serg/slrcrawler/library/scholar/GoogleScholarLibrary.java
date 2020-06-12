package nl.tudelft.serg.slrcrawler.library.scholar;

import nl.tudelft.serg.slrcrawler.library.Library;
import nl.tudelft.serg.slrcrawler.library.LibraryCrawler;
import nl.tudelft.serg.slrcrawler.library.LibraryParser;
import org.openqa.selenium.WebDriver;

public class GoogleScholarLibrary implements Library {

    public static final String NAME = "scholar";
    private final WebDriver driver;
    private final GoogleScholarConfig config;

    public GoogleScholarLibrary(WebDriver driver, GoogleScholarConfig config) {
        this.driver = driver;
        this.config = config;
    }

    @Override
    public LibraryCrawler crawler() {
        return new GoogleScholarCrawler(driver, config);
    }

    @Override
    public LibraryParser parser() {
        return config.isAugmented() ?
                new GoogleScholarParserAugmented(driver) :
                new GoogleScholarParser();
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
