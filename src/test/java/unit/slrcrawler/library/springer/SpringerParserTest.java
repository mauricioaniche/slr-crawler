package unit.slrcrawler.library.springer;

import nl.tudelft.serg.slrcrawler.HtmlPage;
import nl.tudelft.serg.slrcrawler.PaperEntry;
import nl.tudelft.serg.slrcrawler.PaperEntryBuilder;
import nl.tudelft.serg.slrcrawler.library.springer.SpringerParser;
import org.junit.jupiter.api.Test;
import unit.slrcrawler.library.ParserBaseTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SpringerParserTest extends ParserBaseTest {

    final SpringerParser parser = new SpringerParser();

    @Test void
    parse_springer_page() {

        HtmlPage htmlPage = htmlFrom("springer-2020-jun-9.html");

        List<PaperEntry> entries = parser.parse(htmlPage);

        PaperEntry entry1 = new PaperEntryBuilder()
                .fromSpringer()
                .withTitle("The Role of Controlled Experiments in Software Engineering Research")
                .publishedAt("Empirical Software Engineering Issues. Critical Assessment and Future Directions")
                .downloadableFrom("https://link.springer.com/chapter/10.1007/978-3-540-71301-2_10")
                .fromAuthor("Victor R. Basili")
                .inYear(2007)
                .build();

        PaperEntry entry16 = new PaperEntryBuilder()
                .fromSpringer()
                .withTitle("Empirical software engineering experts on the use of students and professionals in experiments")
                .publishedAt("Empirical Software Engineering")
                .downloadableFrom("https://link.springer.com/article/10.1007/s10664-017-9523-3")
                .fromAuthor("Davide Falessi, Natalia Juristo, Claes Wohlin, Burak Turhan, Jürgen Münch, Andreas Jedlitschka, Markku Oivo")
                .inYear(2018)
                .build();

        PaperEntry entry20 = new PaperEntryBuilder()
                .fromSpringer()
                .withTitle("Creating Real Value in Software Engineering Experiments")
                .publishedAt("Empirical Software Engineering Issues. Critical Assessment and Future Directions")
                .downloadableFrom("https://link.springer.com/chapter/10.1007/978-3-540-71301-2_11")
                .fromAuthor("James Miller")
                .inYear(2007)
                .build();

        // TODO: make the assertions stronger by adding all the paper in the HTML

        assertThat(entries)
                .hasSize(14)
                .contains(entry1, entry16, entry20);
    }

    @Test
    void ignore_propaganda_ie_entries_with_no_authors() {
        HtmlPage htmlPage = htmlFrom("springer-2020-jun-9.html");

        List<PaperEntry> entries = parser.parse(htmlPage);

        assertThat(entries)
            .noneMatch(x -> x.getTitle().equals("Proceedings of the World Molecular Imaging Congress 2015, Honolulu, Hawaii, September 2-5, 2015: General Abstracts"))
            .noneMatch(x -> x.getTitle().equals("Proceedings of the 2011 World Molecular Imaging Congress, San Diego, CA, USA, September 7-10, 2011"))
            .noneMatch(x -> x.getTitle().equals("Annual Meeting Technical Program"))
            .noneMatch(x -> x.getTitle().equals("Proceedings of the World Molecular Imaging Congress 2014, Seoul, Korea, September 17-20, 2014"))
            .noneMatch(x -> x.getTitle().equals("Proceedings of the World Molecular Imaging Congress 2013, Savannah, Georgia, September 18-21, 2013"));
    }


}
