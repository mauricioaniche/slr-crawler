package nl.tudelft.serg.slrcrawler.storage;

import com.google.gson.Gson;
import nl.tudelft.serg.slrcrawler.HtmlPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Paths;

public class JsonStorage implements HtmlPageStorage {

    private static final Logger logger = LogManager.getLogger(JsonStorage.class);

    private final String directory;

    public JsonStorage(String directory) {
        this.directory = directory;
    }

    @Override
    public void store(HtmlPage htmlPage) {
        Gson gson = new Gson();
        String jsonObject = gson.toJson(htmlPage);

        try(PrintWriter pw = new PrintWriter(Paths.get(directory, fileName(htmlPage)).toFile())) {
            pw.println(jsonObject);
        } catch (FileNotFoundException e) {
            logger.error("Fail when persisting html page in disk", e);
        }
    }

    private String fileName(HtmlPage htmlPage) {
        return htmlPage.getLibrary() + "-p" + htmlPage.getPageNumber() + ".json";
    }
}
