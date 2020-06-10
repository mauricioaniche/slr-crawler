package nl.tudelft.serg.slrcrawler.library.sciencedirect;

import nl.tudelft.serg.slrcrawler.library.SeleniumLibraryCrawlerTemplate;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static nl.tudelft.serg.slrcrawler.library.sciencedirect.ScienceDirectLibrary.NAME;

public class ScienceDirectCrawler extends SeleniumLibraryCrawlerTemplate {

    public ScienceDirectCrawler(WebDriver driver) {
        super(driver);
    }

    @Override
    protected String name() {
        return NAME;
    }

    @Override
    protected By elementToAppear() {
        return By.className("SearchBody");
    }

    protected String url(String keywords, int zeroBasedPageNumber) {
        return String.format("https://www.sciencedirect.com/search?qs=%s&articleTypes=FLA&offset=%d",
                (zeroBasedPageNumber)*25, /* not about pages, but number of the first element to show */
                urlify(keywords));
    }

}
