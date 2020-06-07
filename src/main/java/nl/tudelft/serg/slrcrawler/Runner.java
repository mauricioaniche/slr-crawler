package nl.tudelft.serg.slrcrawler;

import nl.tudelft.serg.slrcrawler.library.Library;
import nl.tudelft.serg.slrcrawler.library.scholar.GoogleScholarLibrary;
import nl.tudelft.serg.slrcrawler.output.csv.CsvOutputter;
import nl.tudelft.serg.slrcrawler.storage.JsonStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Runner {

    private static final Logger logger = LogManager.getLogger(Runner.class);

    public static void main(String[] args) {

        String keywords = "controlled experiment software engineering";
        int numberOfPages = 2;

        String storageDir = "/Users/mauricioaniche/Desktop/teste/storage";
        String csvFile = "/Users/mauricioaniche/Desktop/teste/slr.csv";

        CsvOutputter out = new CsvOutputter(csvFile);
        JsonStorage storage = new JsonStorage(storageDir);
        List<Library> libraries = Arrays.asList(new GoogleScholarLibrary());
        SLRCrawler slr = new SLRCrawler(libraries, storage, out);

        logger.info("**** SLR Crawler ****");
        logger.info("Keywords: " + keywords);
        logger.info("Pages to crawl: " + numberOfPages);
        logger.info("Libraries available: " + libraries.stream().map(x->x.name()).collect(Collectors.joining(",")));
        logger.info("Starting at " + LocalDateTime.now());

        slr.collect(keywords, numberOfPages);

        out.close();

        logger.info("Done at " + LocalDateTime.now());


    }


}
