package unit.slrcrawler.library;

import unit.slrcrawler.FileReader;
import nl.tudelft.serg.slrcrawler.HtmlPage;

import java.net.URL;
import java.nio.file.Paths;

public class ParserBaseTest {

    protected HtmlPage htmlFrom(String fileName) {
        String html = readResource(fileName);
        return new HtmlPage("scholar", 1, "https://any-url.com", html);
    }

    protected String readResource(String fileName) {
        try {
            URL url = getClass().getClassLoader().getResource(".");
            String filePath = url.getPath();

            return FileReader.readFile(Paths.get(filePath), fileName);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

