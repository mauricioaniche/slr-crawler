package nl.tudelft.serg.slrcrawler.library.scholar;

import nl.tudelft.serg.slrcrawler.HtmlPage;
import nl.tudelft.serg.slrcrawler.PaperEntry;
import nl.tudelft.serg.slrcrawler.PaperEntryBuilder;
import nl.tudelft.serg.slrcrawler.library.InvalidPageException;
import nl.tudelft.serg.slrcrawler.library.LibraryParser;
import nl.tudelft.serg.slrcrawler.library.MLAParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GoogleScholarParserAugmented implements LibraryParser {

    private final WebDriver driver;

    public GoogleScholarParserAugmented(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public List<PaperEntry> parse(HtmlPage htmlPage) {
        Set<PaperEntry> entries = new HashSet<>();

        List<WebElement> results = papers();

        for (WebElement result : results) {
            PaperEntry entry = extractPaperInfoFromHtmlElement(result);
            if(isValid(entry))
                entries.add(entry);

//            ((JavascriptExecutor) driver).executeScript("window.scroll(0,100);");
//            try {
//                Thread.sleep(3 * 1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

        }

        return new ArrayList<>(entries);
    }

    protected boolean isValid(PaperEntry entry) {
        return true;
    }

    private PaperEntry extractPaperInfoFromHtmlElement(WebElement result) {

        MLAParser.MLA mla = getMLA(result);

        return paperEntry()
                .withTitle(extractTitleOrException(result))
                .inYear(extractYearOrException(result))
                .fromAuthor(extractAuthor(mla))
                .publishedAt(extractConference(mla))
                .downloadableFrom(urlPrefix() + extractUrlOrException(result))
                .withCitations(extractCitationsOrException(result))
                .build();
    }

    private int extractCitationsOrException(WebElement result) {
        try {
            return extractCitations(result);
        } catch (Exception e) {
            throw new InvalidPageException("Error extracting citations", e);
        }
    }

    private String extractUrlOrException(WebElement result) {
        try {
            return extractUrl(result);
        } catch (Exception e) {
            throw new InvalidPageException("Error extracting url", e);
        }
    }

    private int extractYearOrException(WebElement result) {
        try {
            return extractYear(result);
        } catch (Exception e) {
            throw new InvalidPageException("Error extracting year", e);
        }
    }

    private String extractTitleOrException(WebElement result) {
        try {
            return extractTitle(result);
        } catch (Exception e) {
            throw new InvalidPageException("Error extracting title", e);
        }
    }

    /**
     * We get the link that is inside the h3 containing the title
     */
    protected String extractUrl(WebElement result) {
        return result
                .findElement(By.cssSelector(".gs_rt a"))
                .getAttribute("href");
    }

    /**
     * To get the year, we parse the author line.
     * Splitting by dash, we have the year in the last 4 characters of the string.
     */
    protected int extractYear(WebElement result) {
        String authorLine = getAuthorLine(result);
        String part = authorLine.split(" - ")[1];
        String possibleYearAsString = part.substring(part.length() - 4);

        if(possibleYearAsString.trim().matches("\\d*")) {
            return Integer.parseInt(possibleYearAsString);
        }

        /* Year is not there... That happens in some strange citations */
        return 0;
    }

    protected PaperEntryBuilder paperEntry() {
        return new PaperEntryBuilder().fromScholar();
    }

    protected List<WebElement> papers() {
        return driver.findElements(By.className("gs_ri"));
    }

    /**
     * We find the 'cited by' element.
     * We remove all characters (the regex is there to avoid 'cited by' in other languages)
     */
    protected int extractCitations(WebElement result) {
        WebElement citedByElement = result.findElements(By.cssSelector(".gs_fl a")).get(2);
        if(citedByElement.getAttribute("href").contains("?cites"))
            return Integer.parseInt(citedByElement.getText().replaceAll("[^0-9.]", ""));
        else
            return 0; /* no citations available */
    }

    protected String extractConference(MLAParser.MLA mla) {
        return mla.getConference();
    }

    /**
     * We have to click at the " icon for the popup to open.
     * And then... we have to close it!
     */
    private MLAParser.MLA getMLA(WebElement result) {
        try {
            WebElement referenceButton = result.findElement(By.className("gs_or_cit"));
            referenceButton.click();
            Thread.sleep(3 * 1000);

            WebElement mla = driver.findElement(By.xpath("//*[@id=\"gs_citt\"]/table/tbody/tr[1]/td/div"));

            String mlaInText = mla.getText();
            System.out.println(mlaInText);

            result.findElement(By.xpath("//*[@id=\"gs_cit-x\"]")).click();
            Thread.sleep(3 * 1000);

            return MLAParser.parse(mlaInText);
        } catch(Exception e) {
            throw new InvalidPageException("Error extracting mla", e);
        }
    }

    protected String extractAuthor(MLAParser.MLA mla) {
        return mla.getAuthor();
    }

    protected String extractTitle(WebElement result) {
        return result.findElement(By.cssSelector(".gs_rt"))
                .getText()
                .replace("[PDF]", "")
                .replace("[HTML]", "")
                .trim();
    }

    /**
     * The author line contains the authors and year.
     */
    private String getAuthorLine(WebElement result) {
        return result.findElement(By.cssSelector(".gs_a")).getText();
    }

    protected String urlPrefix() {
        return "";
    }
}
