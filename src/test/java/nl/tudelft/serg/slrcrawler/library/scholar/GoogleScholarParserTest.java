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

        assertThat(entries).hasSize(10);
        assertThat(entries.get(0).getTitle()).isEqualTo("Software systems as cities: A controlled experiment");
    }

    private static String readFile(String fileName) throws IOException {
        String fullFilePath = GoogleScholarParserTest.class.getResource("/" + fileName).getPath();
        return new String(Files.readAllBytes(Paths.get(fullFilePath)));
    }

}
