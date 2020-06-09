package unit.slrcrawler.library.sciencedirect;

import nl.tudelft.serg.slrcrawler.HtmlPage;
import nl.tudelft.serg.slrcrawler.PaperEntry;
import nl.tudelft.serg.slrcrawler.PaperEntryBuilder;
import nl.tudelft.serg.slrcrawler.library.sciencedirect.ScienceDirectParser;
import org.junit.jupiter.api.Test;
import unit.slrcrawler.library.ParserBaseTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ScienceDirectParserTest extends ParserBaseTest {

    final ScienceDirectParser parser = new ScienceDirectParser();

    @Test void
    parse_ieee_page() {

        HtmlPage htmlPage = htmlFrom("sciencedirect-2020-jun-9.html");

        List<PaperEntry> entries = parser.parse(htmlPage);

        PaperEntry entry1 = new PaperEntryBuilder()
                .fromScienceDirect()
                .withTitle("Software-testing education: A systematic literature mapping")
                .publishedAt("Journal of Systems and Software")
                .downloadableFrom("https://www.sciencedirect.com/science/article/pii/S0164121220300510")
                .fromAuthor("Vahid Garousi, Austen Rainer, Per Lauvås, Andrea Arcuri")
                .inYear(2020)
                .build();

        // TODO: make the assertions stronger by adding all the paper in the HTML

        assertThat(entries)
                .hasSize(25)
                .contains(entry1);
    }



}
