package nl.tudelft.serg.slrcrawler.storage;

import nl.tudelft.serg.slrcrawler.HtmlPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Paths;

public class RawHtmlStorage implements HtmlPageStorage {

    private static final Logger logger = LogManager.getLogger(RawHtmlStorage.class);

    private final String directory;
    private final FileNamer fileNamer;

    public RawHtmlStorage(FileNamer fileNamer, String directory) {
        this.fileNamer = fileNamer;
        this.directory = directory;
    }

    public RawHtmlStorage(String directory) {
        this(new FileNamer(), directory);
    }

    @Override
    public void store(HtmlPage htmlPage) {
        try(PrintWriter pw = new PrintWriter(Paths.get(directory, fileNamer.name(htmlPage, "html")).toFile())) {
            pw.print(htmlPage.getHtml());
        } catch (FileNotFoundException e) {
            logger.error("Fail when persisting html page in disk", e);
        }
    }

}
