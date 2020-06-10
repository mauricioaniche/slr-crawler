package nl.tudelft.serg.slrcrawler.processor;

import nl.tudelft.serg.slrcrawler.HtmlPage;
import nl.tudelft.serg.slrcrawler.PaperEntry;
import nl.tudelft.serg.slrcrawler.library.Library;
import nl.tudelft.serg.slrcrawler.output.Outputter;
import nl.tudelft.serg.slrcrawler.storage.HtmlPageStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class PageProcessor {

    private static final Logger logger = LogManager.getLogger(SLRProcessor.class);

    private final HtmlPageStorage storage;
    private final Outputter outputter;

    public PageProcessor(HtmlPageStorage storage, Outputter outputter) {
        this.storage = storage;
        this.outputter = outputter;
    }

    public void process(String keywords, Library library, int page) {
        logger.info(String.format("- Page %d",page));
        logger.info(String.format("-- Crawling"));

        HtmlPage htmlPage = library.crawler().downloadPage(keywords, page);
        storage.store(htmlPage);

        logger.info(String.format("-- Parsing"));
        List<PaperEntry> entries = library.parser().parse(htmlPage);

        logger.info(String.format("-- Writing"));
        entries.forEach(outputter::write);
        logger.info(String.format("-- Done!"));
    }

}
