package nl.tudelft.serg.slrcrawler;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Google Scholar crawler works like this:
 *
 * 0. We visit https://scholar.google.com
 * 1. It starts with a "q" field. We fill it with our search query, and click at the button.
 * 2. All results can be found through a 'gs_ri' class.
 * 3. For each of the elements
 *  3.1 We get the title (element with class name 'gs_fl')
 *  3.2 We get the name of the author (only first author)
 *  3.3 We get the number of citations
 *  3.4 We get the year
 * 4. We find the link of the next page, we visit it.
 * 5. Stop when number of pages is done
 */
public class GoogleScholarCrawler implements Crawler {

    private final WebDriver driver;

    public GoogleScholarCrawler(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Main loop.
     * While there are pages to parse, we visit the page and we parse it.
     */
    public List<PaperEntry> crawl(String keywords, int noOfPages) {

        final List<PaperEntry> entries = new ArrayList<>();

        driver.get("https://scholar.google.com");
        driver.findElement(By.name("q")).sendKeys(keywords);
        driver.findElement(By.name("btnG")).submit();

        while(noOfPages>0) {
            waitForGoogle();
            entries.addAll(parsePage());
            noOfPages--;

            if(noOfPages>0) goToNextPage();
        }

        return entries;
    }

    private List<PaperEntry> parsePage() {
        final List<PaperEntry> entries = new ArrayList<>();

        List<WebElement> results = driver.findElements(By.className("gs_ri"));
        for (WebElement result : results) {
            try {
                entries.add(extractPaperInfoFromHtmlElement(result));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return entries;
    }

    /**
     * The precise 'a' has no easy way to be found.
     * We then find the icon, and locate its parent.
     */
    private void goToNextPage() {
        String nextPageUrl = driver
                .findElement(By.className("gs_ico_nav_next"))
                .findElement(By.xpath("./..")) // find parent node
                .getAttribute("href");

        driver.get(nextPageUrl);
    }


    /**
     * TODO: we need a smarter way to do it.
     * But... wait for what?
     */
    private void waitForGoogle() {
        try {
            Thread.sleep(5000);
        }catch(Exception e) {
            // this should not really happen...
        }
    }

    private PaperEntry extractPaperInfoFromHtmlElement(WebElement result) {
        String paperTitle = extractPaperTitle(result);
        String url = extractPaperUrl(result);

        String authorLine = result.findElement(By.className("gs_a")).getText();
        String paperAuthor = extractFirstAuthor(authorLine);
        int year = extractYear(authorLine);

        int citations = extractCitations(result);

        return new PaperEntry(paperTitle, url, paperAuthor, year, citations);
    }

    /**
     * We get the link that is inside the h3 containing the title
     */
    private String extractPaperUrl(WebElement result) {
        return result
                .findElement(By.className("gs_rt"))
                .findElement(By.tagName("a"))
                .getAttribute("href");
    }

    /**
     * To get the year, we parse the author line.
     * Splitting by dash, we have the year in the last 4 characters of the string.
     */
    private int extractYear(String authorLine) {
        String part = authorLine.split(" - ")[0];
        return Integer.parseInt(part.substring(part.length() - 4));
    }

    /**
     * We find the 'cited by' element.
     * We remove all characters (the regex is there to avoid 'cited by' in other languages)
     */
    private int extractCitations(WebElement result) {
        WebElement citedByElement = result.findElement(By.className("gs_fl")).findElements(By.tagName("a")).get(2);
        return Integer.parseInt(citedByElement.getText().replaceAll("[^0-9.]", ""));
    }

    /**
     * We get only the first author, as Google hides the names of the others
     * in long lists.
     */
    private String extractFirstAuthor(String authorLine) {
        return authorLine.split(",")[0];
    }

    private String extractPaperTitle(WebElement result) {
        return result.findElement(By.className("gs_rt")).getText();
    }
}
