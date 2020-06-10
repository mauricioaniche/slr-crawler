package integration.slrcrawler.library.ieee;

import nl.tudelft.serg.slrcrawler.HtmlPage;
import nl.tudelft.serg.slrcrawler.library.ieee.IEEEXploreCrawler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.safari.SafariDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class IEEEXploreCrawlerTest {

    final SafariDriver driver = new SafariDriver();
    final IEEEXploreCrawler crawler = new IEEEXploreCrawler(driver);

    @AfterEach
    void close() {
        driver.close();
    }

    @Test
    void just_crawl() {
        HtmlPage html = crawler.downloadPage("controlled experiment software engineering", 15);

        assertThat(html).isNotNull();
        assertThat(html.getHtml()).isNotEmpty();

        // look for specific CSS styles, in a very naive way
        assertThat(html.getHtml()).contains("List-results-items");
        assertThat(html.getHtml()).contains("main-section");
    }
}
