package nl.tudelft.serg.slrcrawler.library.scholar;

import nl.tudelft.serg.slrcrawler.HtmlPage;
import nl.tudelft.serg.slrcrawler.library.LibraryCrawler;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static nl.tudelft.serg.slrcrawler.library.scholar.GoogleScholarLibrary.NAME;


public class GoogleScholarCrawler implements LibraryCrawler {

    private final WebDriver driver;

    public GoogleScholarCrawler(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public HtmlPage downloadPage(String keywords, int zeroBasedPageNumber) {
        try {
            String url = url(keywords, zeroBasedPageNumber);

            driver.get(url);

            // we wait for the async calls to happen.
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("gs_res_ccl")));

            return new HtmlPage(NAME, zeroBasedPageNumber+1, url, driver.getPageSource());
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String url(String keywords, int pageNumber) {
        return String.format("https://scholar.google.com/scholar?start=%d&q=%s&hl=en",
                (pageNumber)*10, /* not about pages, but number of the first element to show */
                urlify(keywords));
    }

    private String urlify(String keywords) {
        return keywords.replace(" ", "+");
    }
}
