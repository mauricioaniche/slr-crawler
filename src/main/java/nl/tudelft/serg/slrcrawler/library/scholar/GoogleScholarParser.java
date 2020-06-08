package nl.tudelft.serg.slrcrawler.library.scholar;

import nl.tudelft.serg.slrcrawler.HtmlPage;
import nl.tudelft.serg.slrcrawler.PaperEntry;
import nl.tudelft.serg.slrcrawler.PaperEntryBuilder;
import nl.tudelft.serg.slrcrawler.library.InvalidPageException;
import nl.tudelft.serg.slrcrawler.library.LibraryParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class GoogleScholarParser implements LibraryParser {

    @Override
    public List<PaperEntry> parse(HtmlPage htmlPage) {
        List<PaperEntry> entries = new ArrayList<>();

        Document doc = Jsoup.parse(htmlPage.getHtml());
        Elements results = doc.select(".gs_ri");

        for (Element result : results) {
            entries.add(extractPaperInfoFromHtmlElement(result));
        }

        return entries;
    }

    private PaperEntry extractPaperInfoFromHtmlElement(Element result) {
        return new PaperEntryBuilder()
                .scholar()
                .title(extractPaperTitle(result))
                .url(extractPaperUrl(result))
                .author(extractFirstAuthor(result))
                .year(extractYear(result))
                .citations(extractCitations(result))
            .build();
    }

    /**
     * The author line contains the authors and year.
     */
    private String getAuthorLine(Element result) {
        try {
            return result.select(".gs_a").text();
        } catch(Exception e) {
            throw new InvalidPageException("Invalid author line", e);
        }
    }

    /**
     * We get the link that is inside the h3 containing the title
     */
    private String extractPaperUrl(Element result) {
        try {
            return result
                    .select(".gs_rt")
                    .select("a")
                    .attr("href");
        } catch(Exception e) {
            throw new InvalidPageException("Invalid paper url", e);
        }
    }

    /**
     * To get the year, we parse the author line.
     * Splitting by dash, we have the year in the last 4 characters of the string.
     */
    private int extractYear(Element result) {
        try {
            String authorLine = getAuthorLine(result);
            String part = authorLine.split(" - ")[1];
            return Integer.parseInt(part.substring(part.length() - 4));
        } catch(Exception e) {
            throw new InvalidPageException("Invalid year", e);
        }
    }

    /**
     * We find the 'cited by' element.
     * We remove all characters (the regex is there to avoid 'cited by' in other languages)
     */
    private int extractCitations(Element result) {
        try {
            Element citedByElement = result.select(".gs_fl a").get(2);
            return Integer.parseInt(citedByElement.text().replaceAll("[^0-9.]", ""));
        } catch(Exception e) {
            throw new InvalidPageException("Invalid citations", e);
        }
    }

    /**
     * We get only the first author, as Google hides the names of the others
     * in long lists.
     */
    private String extractFirstAuthor(Element result) {
        String authorLine = getAuthorLine(result);
        return authorLine.split(",")[0];
    }

    private String extractPaperTitle(Element result) {
        try {
            return result.select(".gs_rt").text();
        } catch(Exception e) {
            throw new InvalidPageException("Invalid title", e);
        }
    }
}
