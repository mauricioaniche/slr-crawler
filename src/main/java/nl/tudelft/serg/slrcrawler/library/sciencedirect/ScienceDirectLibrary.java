package nl.tudelft.serg.slrcrawler.library.sciencedirect;

import nl.tudelft.serg.slrcrawler.library.Library;
import nl.tudelft.serg.slrcrawler.library.LibraryCrawler;
import nl.tudelft.serg.slrcrawler.library.LibraryParser;
import org.openqa.selenium.WebDriver;

public class ScienceDirectLibrary implements Library {
    public static final String NAME = "science-direct";
    private final WebDriver driver;

    public ScienceDirectLibrary(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public LibraryCrawler crawler() {
        return new ScienceDirectCrawler(driver);
    }

    @Override
    public LibraryParser parser() {
        return null;
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
