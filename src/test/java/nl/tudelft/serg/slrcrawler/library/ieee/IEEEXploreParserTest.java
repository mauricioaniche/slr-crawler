package nl.tudelft.serg.slrcrawler.library.ieee;

import nl.tudelft.serg.slrcrawler.HtmlPage;
import nl.tudelft.serg.slrcrawler.PaperEntry;
import nl.tudelft.serg.slrcrawler.PaperEntryBuilder;
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

        PaperEntry entry1 = new PaperEntryBuilder()
                .ieee()
                .title("Analyzing Software Engineering Experiments: Everything You Always Wanted to Know but Were Afraid to Ask")
                .conference("2018 IEEE/ACM 40th International Conference on Software Engineering: Companion (ICSE-Companion)")
                .url("https://ieeexplore.ieee.org/document/8449649/")
                .author("Sira Vegas")
                .year(2018)
                .build();

        PaperEntry entry10 = new PaperEntryBuilder()
                .ieee()
                .title("More efficient software testing through the application of design of experiments (DOE)")
                .conference("Proceedings of 1994 IEEE International Symposium on Software Reliability Engineering")
                .url("https://ieeexplore.ieee.org/document/341395/")
                .author("T. Raske ; M. Marietta")
                .year(1994)
                .citations(1)
                .build();

        assertThat(entries)
                .hasSize(10)
                .contains(entry1, entry10);
    }


}
