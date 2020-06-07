package nl.tudelft.serg.slrcrawler;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.openqa.selenium.WebDriver;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Runner {

    public static void main(String[] args) throws IOException {

        WebDriver driver = new WebDriverFactory().build();
        List<Crawler> crawlers = new CrawlerFactory().build(driver);

        String keywords = "controlled experiment software engineering";
        int pages = 2;

        try (CSVPrinter printer = new CSVPrinter(new FileWriter("csv.txt"), CSVFormat.EXCEL)) {
            crawlers.stream()
                    .map(c -> c.crawl(keywords, pages))
                    .flatMap(entries -> entries.stream())
                    .forEach(entry -> printCsv(printer, entry));
        }

        driver.close();

    }

    private static void printCsv(CSVPrinter printer, PaperEntry entry) {
        try {
            printer.printRecord(entry.getTitle(), entry.getUrl(), entry.getFirstAuthor(), entry.getYear(), entry.getCitation());
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
