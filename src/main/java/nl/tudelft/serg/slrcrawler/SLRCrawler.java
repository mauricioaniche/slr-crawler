package nl.tudelft.serg.slrcrawler;

import nl.tudelft.serg.slrcrawler.library.Library;
import nl.tudelft.serg.slrcrawler.output.Outputter;
import nl.tudelft.serg.slrcrawler.storage.HtmlPageStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class SLRCrawler {

    private static final Logger logger = LogManager.getLogger(SLRCrawler.class);

    private final List<Library> libraries;
    private final HtmlPageStorage storage;
    private final Outputter outputter;

    public SLRCrawler(List<Library> libraries, HtmlPageStorage storage, Outputter outputter) {
        this.libraries = libraries;
        this.storage = storage;
        this.outputter = outputter;
    }

    public void collect(String keywords, int maxNoOfElements, int startFrom) {
        for (Library library : libraries) {
            logger.info(String.format("Library %s starting",library.name()));

            for(int page = library.firstPage(startFrom); page < library.lastPage(maxNoOfElements); page++) {
                try {
                    logger.info(String.format("- Page %d",page));
                    logger.info(String.format("-- Crawling"));

                    HtmlPage htmlPage = library.crawler().downloadPage(keywords, page);
                    storage.store(htmlPage);

                    logger.info(String.format("-- Parsing"));
                    List<PaperEntry> entries = library.parser().parse(htmlPage);
                    System.out.println(entries.size());

                    logger.info(String.format("-- Writing"));
                    entries.forEach(outputter::write);
                    logger.info(String.format("-- Done!"));
                } catch(Exception e) {
                    logger.error(e);
                }
            }
        }
    }
}
