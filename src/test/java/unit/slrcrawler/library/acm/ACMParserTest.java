package unit.slrcrawler.library.acm;

import nl.tudelft.serg.slrcrawler.HtmlPage;
import nl.tudelft.serg.slrcrawler.PaperEntry;
import nl.tudelft.serg.slrcrawler.PaperEntryBuilder;
import unit.slrcrawler.library.ParserBaseTest;
import nl.tudelft.serg.slrcrawler.library.acm.ACMParser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ACMParserTest extends ParserBaseTest {

    final ACMParser parser = new ACMParser();

    @Test void
    parse_acm_page() {

        HtmlPage htmlPage = htmlFrom("acm-2020-jun-8.html");

        List<PaperEntry> entries = parser.parse(htmlPage);

        PaperEntry entry1 = new PaperEntryBuilder()
                .fromACM()
                .withTitle("Describing what experimental software engineering experts do when they design their experiments: a qualitative study")
                .publishedAt("ESEM '17: Proceedings of the 11th ACM/IEEE International Symposium on Empirical Software Engineering and Measurement")
                .downloadableFrom("https://dl.acm.org/doi/abs/10.1109/ESEM.2017.63")
                .fromAuthor("Liliane Fonseca, Sergio Soares, Carolyn Seaman")
                .inYear(2017)
                .withCitations(0)
                .build();

        // TODO: make the assertions stronger by adding all the paper in the HTML

        assertThat(entries)
                .hasSize(20)
                .contains(entry1);
    }

    @Test
    void ignore_propaganda() {
        HtmlPage htmlPage = htmlFrom("acm-with-propaganda-2020-jun-10.html");

        List<PaperEntry> entries = parser.parse(htmlPage);

        assertThat(entries)
                .hasSize(19)
                .noneMatch(entry -> entry.getTitle().equals("MLMI2018: Proceedings of the 2018 International Conference on Machine Learning and Machine Intelligence"));
    }


}
