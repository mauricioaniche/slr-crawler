package nl.tudelft.serg.slrcrawler.library.scholar;

import nl.tudelft.serg.slrcrawler.HtmlPage;
import nl.tudelft.serg.slrcrawler.library.LibraryCrawler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import static nl.tudelft.serg.slrcrawler.library.scholar.GoogleScholarLibrary.NAME;


public class GoogleScholarCrawler implements LibraryCrawler {

    @Override
    public HtmlPage downloadPage(String keywords, int pageNumber) {
        try {
            String url = url(keywords, pageNumber);
            Document doc = Jsoup.connect(url).get();
            return new HtmlPage(NAME, pageNumber, url, doc.html());
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String url(String keywords, int pageNumber) {
        return String.format("https://scholar.google.com/scholar?start=%d&q=%s&hl=en",
                (pageNumber)*10,
                urlify(keywords));
    }

    private String urlify(String keywords) {
        return keywords.replace(" ", "+");
    }
}
