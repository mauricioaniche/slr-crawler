package unit.slrcrawler.library.ieee;

import nl.tudelft.serg.slrcrawler.HtmlPage;
import nl.tudelft.serg.slrcrawler.PaperEntry;
import nl.tudelft.serg.slrcrawler.PaperEntryBuilder;
import unit.slrcrawler.library.ParserBaseTest;
import nl.tudelft.serg.slrcrawler.library.ieee.IEEEXploreParser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class IEEEXploreParserTest extends ParserBaseTest {

    final IEEEXploreParser parser = new IEEEXploreParser();

    @Test void
    parse_ieee_page() {

        HtmlPage htmlPage = htmlFrom("ieee-xplore-2020-jun-8.html");

        List<PaperEntry> entries = parser.parse(htmlPage);

        PaperEntry entry1 = new PaperEntryBuilder()
                .fromIEEE()
                .withTitle("Analyzing Software Engineering Experiments: Everything You Always Wanted to Know but Were Afraid to Ask")
                .publishedAt("2018 IEEE/ACM 40th International Conference on Software Engineering: Companion (ICSE-Companion)")
                .downloadableFrom("https://ieeexplore.ieee.org/document/8449649/")
                .fromAuthor("Sira Vegas")
                .inYear(2018)
                .build();

        PaperEntry entry10 = new PaperEntryBuilder()
                .fromIEEE()
                .withTitle("More efficient software testing through the application of design of experiments (DOE)")
                .publishedAt("Proceedings of 1994 IEEE International Symposium on Software Reliability Engineering")
                .downloadableFrom("https://ieeexplore.ieee.org/document/341395/")
                .fromAuthor("T. Raske ; M. Marietta")
                .inYear(1994)
                .withCitations(1)
                .build();

        // TODO: make the assertions stronger by adding all the paper in the HTML

        assertThat(entries)
                .hasSize(10)
                .contains(entry1, entry10);
    }


}
