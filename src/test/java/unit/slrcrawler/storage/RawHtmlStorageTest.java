package unit.slrcrawler.storage;

import nl.tudelft.serg.slrcrawler.HtmlPage;
import nl.tudelft.serg.slrcrawler.storage.FileNamer;
import nl.tudelft.serg.slrcrawler.storage.RawHtmlStorage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;

import static unit.slrcrawler.FileReader.readFile;
import static org.assertj.core.api.Assertions.assertThat;

public class RawHtmlStorageTest {
    private final FileNamer fileNamer = new FileNamer(false);

    @Test
    void
    save_as_raw_html(@TempDir Path tempDir) throws IOException {
        HtmlPage exampleHtmlPage = new HtmlPage("scholar", 1, "url", "<html><body>abc</body></html>");
        new RawHtmlStorage(fileNamer, tempDir.toString()).store(exampleHtmlPage);

        String storedHtml = readFile(tempDir, "scholar-p1.html");
        assertThat(exampleHtmlPage.getHtml()).isEqualTo(storedHtml);
    }

}
