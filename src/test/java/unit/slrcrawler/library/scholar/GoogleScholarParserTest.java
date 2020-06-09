package unit.slrcrawler.library.scholar;

import nl.tudelft.serg.slrcrawler.HtmlPage;
import nl.tudelft.serg.slrcrawler.PaperEntry;
import nl.tudelft.serg.slrcrawler.PaperEntryBuilder;
import nl.tudelft.serg.slrcrawler.library.InvalidPageException;
import unit.slrcrawler.library.ParserBaseTest;
import nl.tudelft.serg.slrcrawler.library.scholar.GoogleScholarParser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class GoogleScholarParserTest extends ParserBaseTest {

    final GoogleScholarParser parser = new GoogleScholarParser();
    @Test void
    parse_google_page() {

        HtmlPage htmlPage = htmlFrom("scholar-2020-jun-7.html");

        List<PaperEntry> entries = parser.parse(htmlPage);

        PaperEntry entry1 = new PaperEntryBuilder()
                .fromScholar()
                .withTitle("Software systems as cities: A controlled experiment")
                .downloadableFrom("https://dl.acm.org/doi/abs/10.1145/1985793.1985868")
                .fromAuthor("R Wettel")
                .inYear(2011)
                .withCitations(228)
            .build();

        PaperEntry entry2 = new PaperEntryBuilder()
                .fromScholar()
                .withTitle("A controlled experiment quantitatively comparing software development approaches")
                .downloadableFrom("https://ieeexplore.ieee.org/abstract/document/1702844/")
                .fromAuthor("VR Basili")
                .inYear(1981)
                .withCitations(105)
                .build();

        PaperEntry entry10 = new PaperEntryBuilder()
                .fromScholar()
                .withTitle("Assessing the changeability of two object-oriented design alternatives--A controlled experiment")
                .downloadableFrom("https://link.springer.com/article/10.1023/A:1011439416657")
                .fromAuthor("E Arisholm")
                .inYear(2001)
                .withCitations(56)
                .build();

        // TODO: make the assertions stronger by adding all the paper in the HTML

        assertThat(entries)
                .hasSize(10)
                .contains(entry1, entry2, entry10);
    }

    /**
     * This page has no results. In other words, all .gs_ri elements
     * were removed.
     */
    @Test void
    parse_page_without_results() {
        HtmlPage htmlPage = htmlFrom("scholar-no-results.html");

        List<PaperEntry> entries = parser.parse(htmlPage);
        assertThat(entries).isEmpty();
    }

    /**
     * Note that the page has more mistakes, but the parser
     * stops in the first one, which is the missing citation.
     */
    @Test void
    parse_page_with_missing_information() {
        assertThatThrownBy(() -> parser.parse(htmlFrom("scholar-missing-info.html")))
                .isInstanceOf(InvalidPageException.class)
                .hasMessageContaining("citations");
    }

}
