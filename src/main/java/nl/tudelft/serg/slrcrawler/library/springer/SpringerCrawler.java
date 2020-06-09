package nl.tudelft.serg.slrcrawler.library.springer;

import nl.tudelft.serg.slrcrawler.HtmlPage;
import nl.tudelft.serg.slrcrawler.library.LibraryCrawler;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static nl.tudelft.serg.slrcrawler.library.ieee.IEEEXploreLibrary.NAME;

public class SpringerCrawler implements LibraryCrawler {

    private final WebDriver driver;

    public SpringerCrawler(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public HtmlPage downloadPage(String keywords, int zeroBasedPageNumber) {
        try {
            String url = url(keywords, zeroBasedPageNumber);
            driver.get(url);

            // we wait for the async calls to happen.
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("results-list")));

            return new HtmlPage(NAME, zeroBasedPageNumber+1, url, driver.getPageSource());
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String url(String keywords, int zeroBasedPageNumber) {
        return String.format("https://link.springer.com/search/page/%d?query=%s",
                zeroBasedPageNumber + 1, /* page starts in one */
                urlify(keywords)
        );
    }

    private String urlify(String keywords) {
        return keywords.replace(" ", "+");
    }
}