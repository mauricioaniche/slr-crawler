package nl.tudelft.serg.slrcrawler.processor;

import nl.tudelft.serg.slrcrawler.library.Library;
import nl.tudelft.serg.slrcrawler.output.Outputter;
import nl.tudelft.serg.slrcrawler.storage.HtmlPageStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class SLRProcessor {

    private static final Logger logger = LogManager.getLogger(SLRProcessor.class);

    private final List<Library> libraries;
    private final HtmlPageStorage storage;
    private final Outputter outputter;
    private final PageProcessor pageProcessor;
    private final ExceptionHandler exceptionHandler;

    public SLRProcessor(List<Library> libraries, HtmlPageStorage storage, Outputter outputter,
                        PageProcessor pageProcessor, ExceptionHandler exceptionHandler) {
        this.libraries = libraries;
        this.storage = storage;
        this.outputter = outputter;
        this.pageProcessor = pageProcessor;
        this.exceptionHandler = exceptionHandler;
    }

    /**
     * The main method of the program.
     * Algorithm:
     *
     * For each library
     *   For each page [start, end]
     *     Process page
     *
     * @param keywords the keywords to search
     * @param startFrom element to start (not precise, see docs)
     * @param stopAt last element to collect (not precise, see docs)
     */
    public void collect(String keywords, int startFrom, int stopAt) {
        for (Library library : libraries) {
            logger.info(String.format("Library %s starting",library.name()));

            for(int page = library.firstPage(startFrom); page <= library.lastPage(stopAt); page++) {
                try {
                    pageProcessor.process(keywords, library, page);
                } catch(Exception e) {
                    exceptionHandler.handle(e);
                }
            }
        }
    }


}
