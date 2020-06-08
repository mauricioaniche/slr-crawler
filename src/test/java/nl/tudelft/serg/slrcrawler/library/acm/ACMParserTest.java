package nl.tudelft.serg.slrcrawler.library.acm;

import nl.tudelft.serg.slrcrawler.HtmlPage;
import nl.tudelft.serg.slrcrawler.PaperEntry;
import nl.tudelft.serg.slrcrawler.PaperEntryBuilder;
import nl.tudelft.serg.slrcrawler.library.ParserBaseTest;
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
                .acm()
                .title("Describing what experimental software engineering experts do when they design their experiments: a qualitative study")
                .conference("ESEM '17: Proceedings of the 11th ACM/IEEE International Symposium on Empirical Software Engineering and Measurement")
                .url("https://dl.acm.org/doi/abs/10.1109/ESEM.2017.63")
                .author("Liliane Fonseca, Sergio Soares, Carolyn Seaman")
                .year(2017)
                .citations(0)
                .build();

        assertThat(entries)
                .hasSize(20)
                .contains(entry1);
    }


}
