package nl.tudelft.serg.slrcrawler.library.scholar;

import nl.tudelft.serg.slrcrawler.library.SeleniumLibraryCrawlerTemplate;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static nl.tudelft.serg.slrcrawler.library.scholar.GoogleScholarLibrary.NAME;

public class GoogleScholarCrawler extends SeleniumLibraryCrawlerTemplate {

    public GoogleScholarCrawler(WebDriver driver) {
        super(driver);
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
        return String.format("https://scholar.google.com/scholar?start=%d&q=%s&hl=en",
                (zeroBasedPageNumber)*10, /* not about pages, but number of the first element to show */
                urlify(keywords, "+")
        );
    }

}
