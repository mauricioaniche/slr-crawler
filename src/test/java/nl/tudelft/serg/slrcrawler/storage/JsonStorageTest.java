package nl.tudelft.serg.slrcrawler.storage;

import com.google.gson.Gson;
import nl.tudelft.serg.slrcrawler.HtmlPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonStorageTest {

    @Test
    void
    save_as_json(@TempDir Path tempDir) throws IOException {
        HtmlPage exampleHtmlPage = new HtmlPage("scholar", 1, "url", "<html><body>abc</body></html>");
        new JsonStorage(tempDir.toString()).store(exampleHtmlPage);

        String html = readFile(tempDir, "scholar-p1.json");
        HtmlPage htmlPageReadFromFile = new Gson().fromJson(html, HtmlPage.class);

        assertThat(htmlPageReadFromFile).isEqualTo(exampleHtmlPage);
    }

    private static String readFile(Path tempDir, String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(tempDir.toString(), fileName)));
    }
}
