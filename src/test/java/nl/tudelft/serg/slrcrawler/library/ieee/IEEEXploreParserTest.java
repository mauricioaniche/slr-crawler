package nl.tudelft.serg.slrcrawler.library.ieee;

import nl.tudelft.serg.slrcrawler.HtmlPage;
import nl.tudelft.serg.slrcrawler.PaperEntry;
import nl.tudelft.serg.slrcrawler.library.ParserBaseTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class IEEEXploreParserTest extends ParserBaseTest {

    final IEEEXploreParser parser = new IEEEXploreParser();

    @Test void
    parse_ieee_page() {

        HtmlPage htmlPage = htmlFrom("ieee-xplore-2020-jun-8.html");

        List<PaperEntry> entries = parser.parse(htmlPage);

        PaperEntry entry1 = new PaperEntry(
                "Analyzing Software Engineering Experiments: Everything You Always Wanted to Know but Were Afraid to Ask",
                "2018 IEEE/ACM 40th International Conference on Software Engineering: Companion (ICSE-Companion)",
                "https://ieeexplore.ieee.org/document/8449649/",
                "Sira Vegas",
                2018,
                -1);
        PaperEntry entry10 = new PaperEntry(
                "More efficient software testing through the application of design of experiments (DOE)",
                "Proceedings of 1994 IEEE International Symposium on Software Reliability Engineering",
                "https://ieeexplore.ieee.org/document/341395/",
                "T. Raske ; M. Marietta",
                1994,
                1);

        assertThat(entries)
                .hasSize(10)
                .contains(entry1, entry10);
    }


}
