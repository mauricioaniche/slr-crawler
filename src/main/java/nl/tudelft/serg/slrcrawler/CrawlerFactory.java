package nl.tudelft.serg.slrcrawler;

import org.openqa.selenium.WebDriver;

import java.util.Arrays;
import java.util.List;

public class CrawlerFactory {

    public List<Crawler> build(WebDriver driver) {
        return Arrays.asList(new GoogleScholarCrawler(driver));
    }

}
