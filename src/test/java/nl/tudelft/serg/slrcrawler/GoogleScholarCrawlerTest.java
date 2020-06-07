package nl.tudelft.serg.slrcrawler;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GoogleScholarCrawlerTest {

    private WebDriver driver;
    private GoogleScholarCrawler googleScholarCrawler;

    @BeforeEach
    void openDriver() {
        this.driver = new WebDriverFactory().build();
        googleScholarCrawler = new GoogleScholarCrawler(driver);
    }

    @AfterEach
    void closeDriver() {
        driver.close();
    }

    @Test void
    get_list_of_papers() {
        List<PaperEntry> firstPageOnly = googleScholarCrawler.crawl("controlled experiment software engineering", 1);
        assertThat(firstPageOnly)
                .isNotNull()
                .hasSize(10);

        List<PaperEntry> twoPages = googleScholarCrawler.crawl("controlled experiment software engineering", 2);
        assertThat(twoPages)
                .isNotNull()
                .hasSize(20)
                .contains(firstPageOnly.toArray(new PaperEntry[firstPageOnly.size()]));
    }
}
