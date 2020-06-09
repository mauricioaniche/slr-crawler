package integration.slrcrawler.library.scholar;

import nl.tudelft.serg.slrcrawler.HtmlPage;
import nl.tudelft.serg.slrcrawler.library.scholar.GoogleScholarCrawler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.safari.SafariDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class GoogleScholarCrawlerTest {

    final SafariDriver driver = new SafariDriver();
    final GoogleScholarCrawler crawler = new GoogleScholarCrawler(driver);

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
        assertThat(html.getHtml()).contains("gs_res_ccl");

        System.out.println(html.getHtml());
    }
}
