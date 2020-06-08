package nl.tudelft.serg.slrcrawler.storage;

import com.google.gson.Gson;
import nl.tudelft.serg.slrcrawler.HtmlPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;

import static nl.tudelft.serg.slrcrawler.FileReader.readFile;
import static org.assertj.core.api.Assertions.assertThat;

public class JsonStorageTest {

    private final FileNamer fileNamer = new FileNamer(false);

    @Test
    void
    save_as_json(@TempDir Path tempDir) throws IOException {
        HtmlPage exampleHtmlPage = new HtmlPage("scholar", 1, "url", "<html><body>abc</body></html>");
        new JsonStorage(fileNamer, tempDir.toString()).store(exampleHtmlPage);

        String html = readFile(tempDir, "scholar-p1.json");
        HtmlPage htmlPageReadFromFile = new Gson().fromJson(html, HtmlPage.class);

        assertThat(htmlPageReadFromFile).isEqualTo(exampleHtmlPage);
    }


}
