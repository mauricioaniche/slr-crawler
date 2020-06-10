package nl.tudelft.serg.slrcrawler.library.springer;

import nl.tudelft.serg.slrcrawler.library.SeleniumLibraryCrawlerTemplate;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static nl.tudelft.serg.slrcrawler.library.springer.SpringerLibrary.NAME;
import static nl.tudelft.serg.slrcrawler.library.springer.SpringerUrlBuilder.buildUrl;

public class SpringerCrawler extends SeleniumLibraryCrawlerTemplate {

    private final SpringerConfig config;

    public SpringerCrawler(WebDriver driver, SpringerConfig config) {
        super(driver);
        this.config = config;
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
        return buildUrl(config, keywords, zeroBasedPageNumber);
    }

}
