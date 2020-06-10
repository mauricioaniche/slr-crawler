package nl.tudelft.serg.slrcrawler.library.acm;

import nl.tudelft.serg.slrcrawler.library.SeleniumLibraryCrawlerTemplate;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static nl.tudelft.serg.slrcrawler.util.UrlEncoder.encode;

public class ACMCrawler extends SeleniumLibraryCrawlerTemplate {

    public ACMCrawler(WebDriver driver) {
        super(driver);
    }

    @Override
    protected String name() {
        return ACMLibrary.NAME;
    }

    @Override
    protected By elementToAppear() {
        return By.className("items-results");
    }

    protected String url(String keywords, int zeroBasedPageNumber) {
        return String.format("https://dl.acm.org/action/doSearch?AllField=%s&pageSize=20&startPage=%d",
                encode(keywords),
                zeroBasedPageNumber /* page starts in zero */
        );
    }

}
