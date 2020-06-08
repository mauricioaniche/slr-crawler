package nl.tudelft.serg.slrcrawler.library.ieee;

import nl.tudelft.serg.slrcrawler.library.Library;
import nl.tudelft.serg.slrcrawler.library.LibraryCrawler;
import nl.tudelft.serg.slrcrawler.library.LibraryParser;
import org.openqa.selenium.WebDriver;

public class IEEEXploreLibrary implements Library {

    public static final String NAME = "ieee";
    private final WebDriver driver;

    public IEEEXploreLibrary(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public LibraryCrawler crawler() {
        return new IEEEXploreCrawler(driver);
    }

    @Override
    public LibraryParser parser() {
        return new IEEEXploreParser();
    }

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public int elementsPerPage() {
        return 25;
    }
}
