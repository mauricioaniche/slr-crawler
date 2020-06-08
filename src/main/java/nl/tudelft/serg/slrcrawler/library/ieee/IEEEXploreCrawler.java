package nl.tudelft.serg.slrcrawler.library.ieee;

import nl.tudelft.serg.slrcrawler.HtmlPage;
import nl.tudelft.serg.slrcrawler.library.LibraryCrawler;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static nl.tudelft.serg.slrcrawler.library.ieee.IEEEXploreLibrary.NAME;

public class IEEEXploreCrawler implements LibraryCrawler {

    private final WebDriver driver;

    public IEEEXploreCrawler(WebDriver driver) {
        this.driver = driver;
    }
    
    @Override
    public HtmlPage downloadPage(String keywords, int zeroBasedPageNumber) {
        try {
            String url = url(keywords, zeroBasedPageNumber);
            driver.get(url);

            // we wait for the async calls to happen.
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.className("results-actions")));

            return new HtmlPage(NAME, zeroBasedPageNumber+1, url, driver.getPageSource());
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String url(String keywords, int pageNumber) {
        return String.format("https://ieeexplore.ieee.org/search/searchresult.jsp?newsearch=true&queryText=%s&returnFacets=ALL&returnType=SEARCH&rowsPerPage=25&pageNumber=%d",
                urlify(keywords),
                (pageNumber+1)
                );
    }

    private String urlify(String keywords) {
        return keywords.replace(" ", "%20");
    }
}
