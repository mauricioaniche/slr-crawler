package nl.tudelft.serg.slrcrawler.library.acm;

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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ACMParser implements LibraryParser {

    /**
     * All results are in '.List-results-item' class.
     * We use a Set because it's amazing the number of times the same
     * element appears in the HTML... :/
     */
    @Override
    public List<PaperEntry> parse(HtmlPage htmlPage) {
        Set<PaperEntry> entries = new HashSet<>();

        Document doc = Jsoup.parse(htmlPage.getHtml());
        Elements results = doc.select(".issue-item--search");

        for (Element result : results) {
            entries.add(extractPaperInfoFromHtmlElement(result));
        }

        return new ArrayList<>(entries);
    }

    private PaperEntry extractPaperInfoFromHtmlElement(Element result) {

        return new PaperEntryBuilder()
                .acm()
                .title(extractTitle(result))
                .year(extractYear(result))
                .author(extractAuthor(result))
                .conference(extractConference(result))
                .url("https://dl.acm.org" + extractUrl(result))
                .citations(extractCitations(result))
                .build();
    }

    private int extractCitations(Element result) {
        try {
            String divText = result
                    .select(".citation").select("span")
                    .text();

            return Integer.parseInt(divText.replaceAll("[^0-9.]", ""));
        } catch (NumberFormatException e) {
            throw new InvalidPageException("Error extracting citation",e);
        }
    }

    private String extractConference(Element result) {
        try {
            return result.select(".epub-section__title").text();
        } catch (Exception e) {
            throw new InvalidPageException("Missing conference",e);
        }
    }

    private String extractAuthor(Element result) {
        try {
            return result.select("[aria-label='authors']").text();
        } catch (Exception e) {
            throw new InvalidPageException("Missing author",e);
        }
    }

    private int extractYear(Element result) {
        try {
            String pubDate = result
                    .select(".issue-item__detail")
                    .select(".dot-separator")
                    .select("span")
                    .get(0).text();

            /**
             * The year is always represented in messy way.
             * We try to remove all non-numbers from the string, and then
             * pick the first 4 numbers that appear, and consider that the year.
             */
            String[] parts = pubDate.replaceAll("[^0-9. ]", "").split(" ");
            for(String part : parts) {
                if(part.length() == 4)
                    return Integer.parseInt(part);
            }

            return -1;
        } catch(Exception e) {
            throw new InvalidPageException("Missing year",e);
        }
    }

    private String extractUrl(Element result) {
        try {
            return result.select(".issue-item__title a").attr("href");
        } catch(Exception e) {
            throw new InvalidPageException("Missing url",e);
        }
    }

    private String extractTitle(Element result) {
        try {
            return result.select(".issue-item__title").text();
        } catch(Exception e) {
            throw new InvalidPageException("Missing title",e);
        }
    }
}
