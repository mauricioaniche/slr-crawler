package nl.tudelft.serg.slrcrawler.cli;

import nl.tudelft.serg.slrcrawler.library.Library;
import nl.tudelft.serg.slrcrawler.library.acm.ACMLibrary;
import nl.tudelft.serg.slrcrawler.library.ieee.IEEEXploreLibrary;
import nl.tudelft.serg.slrcrawler.library.scholar.GoogleScholarLibrary;
import nl.tudelft.serg.slrcrawler.library.sciencedirect.ScienceDirectLibrary;
import nl.tudelft.serg.slrcrawler.library.springer.SpringerLibrary;
import nl.tudelft.serg.slrcrawler.output.Outputter;
import nl.tudelft.serg.slrcrawler.output.csv.CsvOutputter;
import nl.tudelft.serg.slrcrawler.processor.ExceptionHandler;
import nl.tudelft.serg.slrcrawler.processor.PageProcessor;
import nl.tudelft.serg.slrcrawler.processor.SLRProcessor;
import nl.tudelft.serg.slrcrawler.processor.Sleeper;
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

    @Option(names={"-t", "--time"}, description = "The number of seconds to wait in between page visits. Good to avoid libraries to block you. Default = 0")
    int seconds;

    @Option(names={"-l", "--libraries"}, split=",", required = true, description = "Which libraries to use. Currently 'scholar', 'ieee', 'acm', 'sciencedirect', 'springer'")
    String[] libraries;

    @Option(names={"-b", "--browser"}, description = "Which browser to open for the crawling. You have to configure Selenium's plugin in your machine. Supported 'safari', 'firefox', 'chrome'")
    String browser;

    @Option(names = { "-h", "--help" }, usageHelp = true, description = "display a help message")
    boolean helpRequested = false;

    @Option(names = { "-f", "--storageFormat" }, description = "format to store files. Default=html. Options: 'html', 'json'", defaultValue = "html")
    String storageFormat;

    private static final Logger logger = LogManager.getLogger(SLRCrawlerCli.class);

    public static void main(String[] args) {
        SLRCrawlerCli opts = new SLRCrawlerCli();
        new CommandLine(opts).parseArgs(args);

        /**
         * If user is asking for help, we print it, and close the app.
         */
        if (opts.helpRequested) {
            CommandLine.usage(new SLRCrawlerCli(), System.out);
            return;
        }

        /**
         * We know build everything we need from the configs:
         * - the webdriver
         * - the storage
         * - the outputter
         * - the libraries
         */
        WebDriver driver = buildWebDriver(opts);
        HtmlPageStorage storage = buildStorage(opts);
        List<Library> libraries = buildLibraries(opts, driver);
        Outputter out = builtOutputter(opts);
        PageProcessor pageProcessor = new PageProcessor(storage, out);
        ExceptionHandler exceptionHandler = new ExceptionHandler();
        Sleeper sleeper = new Sleeper(opts.seconds);

        SLRProcessor slr = new SLRProcessor(libraries, pageProcessor, sleeper, exceptionHandler);

        /**
         * Let's run!
         */
        logStartup(opts, libraries);
        slr.collect(opts.keywords, opts.startFrom, opts.stopAt);
        logFinish();

        /**
         * Now, let's clean the resources and close the app.
         * The System.exit() is here to force it. Sometimes Selenium keeps some
         * threads... So, we kill it!
         */
        close(opts, out, driver);
        System.exit(0);

    }

    private static WebDriver buildWebDriver(SLRCrawlerCli opts) {
        if(opts.browser.equals("safari"))
            return new SafariDriver();
        if(opts.browser.equals("firefox"))
            return new FirefoxDriver();
        if(opts.browser.equals("chrome"))
            return new ChromeDriver();

        throw new IllegalArgumentException(String.format("Browser not supported (%s)", opts.browser));
    }

    private static void logFinish() {
        logger.info("Done at " + LocalDateTime.now());
    }

    private static void close(SLRCrawlerCli opt, Outputter out, WebDriver driver) {
        out.close();
        if(driver != null)
            driver.close();
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
        if(opt.storageFormat.equals("html"))
            return new RawHtmlStorage(opt.storageDir);
        if(opt.storageFormat.equals("json"))
            return new JsonStorage(opt.storageDir);

        throw new IllegalArgumentException("Invalid storage");
    }

    @NotNull
    private static List<Library> buildLibraries(SLRCrawlerCli opts, WebDriver driver) {
        List<Library> libraries = new ArrayList<>();

        for (String library : opts.libraries) {
            switch (library) {
                case "scholar":
                    libraries.add(new GoogleScholarLibrary(driver));
                    break;
                case "ieee":
                    libraries.add(new IEEEXploreLibrary(driver));
                    break;
                case "acm":
                    libraries.add(new ACMLibrary(driver));
                    break;
                case "sciencedirect":
                    libraries.add(new ScienceDirectLibrary(driver));
                    break;
                case "springer":
                    libraries.add(new SpringerLibrary(driver));
                    break;
            }
        }

        return libraries;
    }
}
