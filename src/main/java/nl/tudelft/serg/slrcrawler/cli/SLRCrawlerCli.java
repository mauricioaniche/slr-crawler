package nl.tudelft.serg.slrcrawler.cli;

import nl.tudelft.serg.slrcrawler.library.sciencedirect.ScienceDirectLibrary;
import nl.tudelft.serg.slrcrawler.library.springer.SpringerLibrary;
import nl.tudelft.serg.slrcrawler.processor.ExceptionHandler;
import nl.tudelft.serg.slrcrawler.processor.PageProcessor;
import nl.tudelft.serg.slrcrawler.processor.SLRProcessor;
import nl.tudelft.serg.slrcrawler.library.Library;
import nl.tudelft.serg.slrcrawler.library.acm.ACMLibrary;
import nl.tudelft.serg.slrcrawler.library.ieee.IEEEXploreLibrary;
import nl.tudelft.serg.slrcrawler.library.scholar.GoogleScholarLibrary;
import nl.tudelft.serg.slrcrawler.output.Outputter;
import nl.tudelft.serg.slrcrawler.output.csv.CsvOutputter;
import nl.tudelft.serg.slrcrawler.storage.HtmlPageStorage;
import nl.tudelft.serg.slrcrawler.storage.JsonStorage;
import nl.tudelft.serg.slrcrawler.storage.RawHtmlStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import picocli.CommandLine;
import picocli.CommandLine.Option;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SLRCrawlerCli {

    @Option(names={"-k", "--keywords"}, required=true, description = "The keywords used in the search")
    String keywords;

    @Option(names={"-d", "--dir"}, required=true, description = "Directory to store everything")
    String storageDir;

    @Option(names={"-n", "--stopAt"}, required=true, description = "The number of the last item to be captured (note that the crawler might return a bit more than specified, depending on the library)")
    int stopAt;

    @Option(names={"-s", "--startFrom"}, description = "The number of the first item to start (could be a bit less, depending on the library)")
    int startFrom;

    @Option(names={"-l", "--libraries"}, split=",", required = true, description = "Which libraries to use. Currently 'scholar', 'ieee', 'acm', 'sciencedirect', 'springer'")
    String[] libraries;

    @Option(names={"-b", "--browser"}, description = "Which browser to open for the crawling. You have to configure Selenium's plugin in your machine. Supported 'safari', 'firefox', 'chrome'")
    String browser;

    @Option(names = { "-h", "--help" }, usageHelp = true, description = "display a help message")
    boolean helpRequested = false;

    @Option(names = { "-f", "--storageFormat" }, description = "format to store files. Default=html. Options: 'html', 'json'", defaultValue = "html")
    String storageFormat;

    private static final Logger logger = LogManager.getLogger(SLRCrawlerCli.class);
    private WebDriver driver;

    public static void main(String[] args) {
        SLRCrawlerCli opts = new SLRCrawlerCli();
        new CommandLine(opts).parseArgs(args);
        if (opts.helpRequested) {
            CommandLine.usage(new SLRCrawlerCli(), System.out);
            return;
        }

        buildWebDriver(opts);
        HtmlPageStorage storage = buildStorage(opts);
        List<Library> libraries = buildLibraries(opts);
        Outputter out = builtOutputter(opts);
        PageProcessor pageProcessor = new PageProcessor(storage, out);
        ExceptionHandler exceptionHandler = new ExceptionHandler();

        SLRProcessor slr = new SLRProcessor(libraries, storage, out, pageProcessor, exceptionHandler);

        logStartup(opts, libraries);
        slr.collect(opts.keywords, opts.startFrom, opts.stopAt);
        logFinish();

        close(opts, out);
        System.exit(0);

    }

    private static void buildWebDriver(SLRCrawlerCli opts) {
        if(opts.browser.equals("safari"))
            opts.driver = new SafariDriver();
        else if(opts.browser.equals("firefox"))
            opts.driver = new FirefoxDriver();
        else if(opts.browser.equals("chrome"))
            opts.driver = new ChromeDriver();
        else
            throw new IllegalArgumentException(String.format("Browser not supported (%s)", opts.browser));
    }

    private static void logFinish() {
        logger.info("Done at " + LocalDateTime.now());
    }

    private static void close(SLRCrawlerCli opt, Outputter out) {
        out.close();
        if(opt.driver !=null)
            opt.driver.close();
    }

    private static void logStartup(SLRCrawlerCli opt, List<Library> libraries) {
        logger.info("**** SLR Crawler ****");
        logger.info("Keywords: " + opt.keywords);
        logger.info("Max No of Results: " + opt.stopAt);
        logger.info("Libraries available: " + libraries.stream().map(x->x.name()).collect(Collectors.joining(",")));
        logger.info("Starting at " + LocalDateTime.now());
    }

    @NotNull
    private static Outputter builtOutputter(SLRCrawlerCli opt) {
        return new CsvOutputter(Paths.get(opt.storageDir, "slr.csv").toString());
    }

    @NotNull
    private static HtmlPageStorage buildStorage(SLRCrawlerCli opt) {
        HtmlPageStorage storage;
        if(opt.storageFormat.equals("html"))
            storage = new RawHtmlStorage(opt.storageDir);
        else if(opt.storageFormat.equals("json"))
            storage = new JsonStorage(opt.storageDir);
        else
            throw new IllegalArgumentException("Invalid storage");
        return storage;
    }

    @NotNull
    private static List<Library> buildLibraries(SLRCrawlerCli opts) {
        List<Library> libraries = new ArrayList<>();
        List<String> librariesAsList = Arrays.asList(opts.libraries);

        if(librariesAsList.contains("scholar"))
            libraries.add(new GoogleScholarLibrary(opts.driver));

        if(librariesAsList.contains("ieee")) {
            libraries.add(new IEEEXploreLibrary(opts.driver));
        }

        if(librariesAsList.contains("acm"))
            libraries.add(new ACMLibrary(opts.driver));

        if(librariesAsList.contains("sciencedirect"))
            libraries.add(new ScienceDirectLibrary(opts.driver));

        if(librariesAsList.contains("springer"))
            libraries.add(new SpringerLibrary(opts.driver));

        return libraries;
    }
}
