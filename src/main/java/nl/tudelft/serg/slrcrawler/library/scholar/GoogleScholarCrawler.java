package nl.tudelft.serg.slrcrawler.library.scholar;

import nl.tudelft.serg.slrcrawler.library.SeleniumLibraryCrawlerTemplate;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static nl.tudelft.serg.slrcrawler.library.scholar.GoogleScholarLibrary.NAME;
import static nl.tudelft.serg.slrcrawler.library.scholar.GoogleScholarUrlBuilder.buildUrl;

public class GoogleScholarCrawler extends SeleniumLibraryCrawlerTemplate {

    private final GoogleScholarConfig config;

    public GoogleScholarCrawler(WebDriver driver, GoogleScholarConfig config) {
        super(driver);
        this.config = config;
    }

    @Override
    protected String name() {
        return NAME;
    }

    @Override
    protected By elementToAppear() {
        return By.id("gs_res_ccl");
    }

    protected String url(String keywords, int zeroBasedPageNumber) {
        return buildUrl(config, keywords, zeroBasedPageNumber);
    }

}
