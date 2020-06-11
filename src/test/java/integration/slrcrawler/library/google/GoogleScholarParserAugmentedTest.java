package integration.slrcrawler.library.google;

import nl.tudelft.serg.slrcrawler.HtmlPage;
import nl.tudelft.serg.slrcrawler.PaperEntry;
import nl.tudelft.serg.slrcrawler.library.scholar.GoogleScholarParserAugmented;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.mockito.Mockito.mock;

@Tag("integration")
public class GoogleScholarParserAugmentedTest {

    private SafariDriver safariDriver;
    private GoogleScholarParserAugmented parser;

    @BeforeEach
    void setup() {
        safariDriver = new SafariDriver();
        safariDriver.get("https://scholar.google.com/scholar?hl=en&q=software+engineering+code+smells");

        // wait for google to open up the page
        new WebDriverWait(safariDriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("gs_res_ccl")));

        parser = new GoogleScholarParserAugmented(safariDriver);
    }

    @AfterEach
    void close() {
        safariDriver.close();
    }

    @Test void
    parse_mla() {
        List<PaperEntry> entries = parser.parse(mock(HtmlPage.class));

        System.out.println("generated entries");
        entries.forEach(entry -> System.out.println(entry));
    }
}
