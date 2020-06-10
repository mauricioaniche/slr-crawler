package integration.slrcrawler.library;

import nl.tudelft.serg.slrcrawler.HtmlPage;
import nl.tudelft.serg.slrcrawler.PaperEntry;
import nl.tudelft.serg.slrcrawler.library.Library;
import nl.tudelft.serg.slrcrawler.library.acm.ACMLibrary;
import nl.tudelft.serg.slrcrawler.library.ieee.IEEEXploreLibrary;
import nl.tudelft.serg.slrcrawler.library.sciencedirect.ScienceDirectLibrary;
import nl.tudelft.serg.slrcrawler.library.springer.SpringerLibrary;
import nl.tudelft.serg.slrcrawler.processor.Sleeper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class LibraryCrawlerAndParserTest {

    private static WebDriver driver;
    private static String keywords;
    private Sleeper sleeper = new Sleeper(3);

    @BeforeAll
    static void setup() {
        keywords = generateRandomKeywords();
        driver = new SafariDriver();
    }

    @AfterAll
    static void close() {
        driver.close();
    }

    @ParameterizedTest
    @MethodSource("libraryGenerator")
    void
    smoke_test_crawlers_and_parsers(Library library) {

        for(int i = 0; i <= 2; i++) {
            HtmlPage html = library
                    .crawler()
                    .downloadPage(keywords, i);

            assertThat(html).isNotNull();
            assertThat(html.getHtml()).isNotEmpty();

            saveForLaterDebug(keywords, html);

            List<PaperEntry> entries = library.parser().parse(html);

            assertThat(entries)
                    .isNotNull()
                    .isNotEmpty();

            assertThat(entries)
                    .allMatch(entry -> !entry.getTitle().isEmpty());

            // to avoid blocks
            sleeper.sleep();
        }
    }

    private void saveForLaterDebug(String keywords, HtmlPage html) {
        String fileName = html.getLibrary() + keywords.replaceAll(" ", "-") + ".html";
        try(PrintWriter ps = new PrintWriter(fileName)) {
            ps.print(html.getHtml());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Very naive way to generate keywords, but good enough
     * for the smoke test.
     */
    private static String generateRandomKeywords() {
        String[] keywords = new String[] { "software", "machine learning",
        "refactoring", "testing", "requirements", "mining repositories", "empirical",
        "agile", "qualitative research"};

        Random random = new Random();
        int numberOfWords = random.nextInt(keywords.length) + 1;

        return IntStream.range(0, numberOfWords)
                .map(wordNo -> random.nextInt(numberOfWords))
                .mapToObj(wordNo -> keywords[wordNo])
                .collect(Collectors.joining(" "));

    }

    static Stream<Arguments> libraryGenerator() {
        /**
         * You could be here too, Google, but you are just
         * too smart for me...
         */
        return Stream.of(
                Arguments.of(new IEEEXploreLibrary(driver)),
                Arguments.of(new ACMLibrary(driver)),
                Arguments.of(new ScienceDirectLibrary(driver)),
                Arguments.of(new SpringerLibrary(driver))
        );
    }
}
