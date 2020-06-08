package nl.tudelft.serg.slrcrawler.library.ieee;

import nl.tudelft.serg.slrcrawler.HtmlPage;
import nl.tudelft.serg.slrcrawler.library.LibraryCrawler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import static nl.tudelft.serg.slrcrawler.library.ieee.IEEEXploreLibrary.NAME;

public class IEEEXploreCrawler implements LibraryCrawler {

    @Override
    public HtmlPage downloadPage(String keywords, int zeroBasedPageNumber) {
        try {
            String url = url(keywords, zeroBasedPageNumber);
            Document doc = Jsoup.connect(url).get();
            return new HtmlPage(NAME, zeroBasedPageNumber+1, url, doc.html());
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String url(String keywords, int pageNumber) {
        return String.format("https://ieeexplore.ieee.org/search/searchresult.jsp?newsearch=true&queryText=%s&returnFacets=ALL&returnType=SEARCH&rowsPerPage=25&pageNumber=%d",
                urlify(keywords),
                (pageNumber+1)
                );
    }

    private String urlify(String keywords) {
        return keywords.replace(" ", "%20");
    }
}
