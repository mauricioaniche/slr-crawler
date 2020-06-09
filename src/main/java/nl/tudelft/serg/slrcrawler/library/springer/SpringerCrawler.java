package nl.tudelft.serg.slrcrawler.library.springer;

import nl.tudelft.serg.slrcrawler.library.SeleniumLibraryCrawlerTemplate;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static nl.tudelft.serg.slrcrawler.library.springer.SpringerLibrary.NAME;

public class SpringerCrawler extends SeleniumLibraryCrawlerTemplate {

    public SpringerCrawler(WebDriver driver) {
        super(driver);
    }

    @Override
    protected String name() {
        return NAME;
    }

    @Override
    protected By elementToAppear() {
        return By.id("results-list");
    }

    protected String url(String keywords, int zeroBasedPageNumber) {
        return String.format("https://link.springer.com/search/page/%d?query=%s",
                zeroBasedPageNumber + 1, /* page starts in one */
                urlify(keywords, "+")
        );
    }

}
