package nl.tudelft.serg.slrcrawler.library.scholar;

import com.google.gson.Gson;
import nl.tudelft.serg.slrcrawler.HtmlPage;
import nl.tudelft.serg.slrcrawler.PaperEntry;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GoogleScholarParserTest {

    @Test void
    parse_google_page() throws IOException {
        HtmlPage htmlPage = new Gson().fromJson(readFile("scholar-2020-jun-7.json"), HtmlPage.class);

        List<PaperEntry> entries = new GoogleScholarParser().parse(htmlPage);

        PaperEntry entry1 = new PaperEntry("Software systems as cities: A controlled experiment", "https://dl.acm.org/doi/abs/10.1145/1985793.1985868","R Wettel", 2011, 228);
        PaperEntry entry2 = new PaperEntry("A controlled experiment quantitatively comparing software development approaches", "https://ieeexplore.ieee.org/abstract/document/1702844/","VR Basili", 1981, 105);
        PaperEntry entry3 = new PaperEntry("Evaluating advantages of test driven development: a controlled experiment with professionals", "https://dl.acm.org/doi/abs/10.1145/1159733.1159788","G Canfora", 2006, 105);
        PaperEntry entry10 = new PaperEntry("Assessing the changeability of two object-oriented design alternatives--A controlled experiment", "https://link.springer.com/article/10.1023/A:1011439416657","E Arisholm", 2001, 56);

        assertThat(entries)
                .hasSize(10)
                .contains(entry1, entry2, entry3, entry10);
    }

    private static String readFile(String fileName) throws IOException {
        String fullFilePath = GoogleScholarParserTest.class.getResource("/" + fileName).getPath();
        return new String(Files.readAllBytes(Paths.get(fullFilePath)));
    }

}
