package nl.tudelft.serg.slrcrawler.library;

import nl.tudelft.serg.slrcrawler.HtmlPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class SeleniumLibraryCrawlerTemplate implements LibraryCrawler {
    private final WebDriver driver;

    public SeleniumLibraryCrawlerTemplate(WebDriver driver) {
        this.driver = driver;
    }

    public HtmlPage downloadPage(String keywords, int zeroBasedPageNumber) {
        try {
            String url = url(keywords, zeroBasedPageNumber);
            driver.get(url);

            // we wait for the async calls to happen.
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(elementToAppear()));

            return new HtmlPage(name(), zeroBasedPageNumber+1, url, driver.getPageSource());
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract String name();

    protected abstract By elementToAppear();

    protected abstract String url(String keywords, int zeroBasedPageNumber);

}
