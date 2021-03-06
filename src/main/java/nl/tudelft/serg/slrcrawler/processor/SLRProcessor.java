package nl.tudelft.serg.slrcrawler.processor;

import nl.tudelft.serg.slrcrawler.library.Library;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class SLRProcessor {

    private static final Logger logger = LogManager.getLogger(SLRProcessor.class);

    private final List<Library> libraries;
    private final PageProcessor pageProcessor;
    private final Sleeper sleeper;
    private final ExceptionHandler exceptionHandler;

    public SLRProcessor(List<Library> libraries, PageProcessor pageProcessor,
                        Sleeper sleeper, ExceptionHandler exceptionHandler) {
        this.libraries = libraries;
        this.pageProcessor = pageProcessor;
        this.sleeper = sleeper;
        this.exceptionHandler = exceptionHandler;
    }

    /**
     * The main method of the program.
     * Algorithm:
     *
     * For each library
     *   For each page [start, end]
     *     Process page
     *     Sleep for a bit as to prevent blocks
     *
     * @param keywords the keywords to search
     * @param startFrom element to start (not precise, see docs)
     * @param stopAt last element to collect (not precise, see docs)
     */
    public void collect(String keywords, int startFrom, int stopAt) {
        for (Library library : libraries) {
            int firstPage = library.firstPageInclusive(startFrom);
            int lastPage = library.lastPageInclusive(stopAt);

            logger.info(String.format("Library %s, pages %d-%d",library.name(), firstPage, lastPage));

            for(int page = firstPage; page <= lastPage; page++) {
                try {
                    pageProcessor.process(keywords, library, page);
                    sleeper.sleep();
                } catch(Exception e) {
                    exceptionHandler.handle(e);
                }
            }
        }
    }


}
