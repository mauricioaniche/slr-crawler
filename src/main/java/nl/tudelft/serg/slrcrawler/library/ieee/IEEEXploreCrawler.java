package nl.tudelft.serg.slrcrawler.library.ieee;

import nl.tudelft.serg.slrcrawler.library.SeleniumLibraryCrawlerTemplate;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static nl.tudelft.serg.slrcrawler.library.ieee.IEEEXploreLibrary.NAME;

public class IEEEXploreCrawler extends SeleniumLibraryCrawlerTemplate {

    public IEEEXploreCrawler(WebDriver driver) {
        super(driver);
    }

    @Override
    protected String name() {
        return NAME;
    }

    @Override
    protected By elementToAppear() {
        return By.className("results-actions");
    }

    protected String url(String keywords, int zeroBasedPageNumber) {
        return String.format("https://ieeexplore.ieee.org/search/searchresult.jsp?newsearch=true&queryText=%s&returnFacets=ALL&returnType=SEARCH&rowsPerPage=25&pageNumber=%d",
                urlify(keywords, "%20"),
                (zeroBasedPageNumber+1) /* page starts in 1, thus the +1 */
        );
    }

}
