package nl.tudelft.serg.slrcrawler.library.ieee;

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

public class IEEEXploreParser implements LibraryParser {

    /**
     * All results are in '.List-results-item' class.
     * We use a Set because it's amazing the number of times the same
     * element appears in the HTML... :/
     */
    @Override
    public List<PaperEntry> parse(HtmlPage htmlPage) {
        Set<PaperEntry> entries = new HashSet<>();

        Document doc = Jsoup.parse(htmlPage.getHtml());
        Elements results = doc.select(".main-section .List-results-items")
                .select(".hide-mobile");

        for (Element result : results) {
            entries.add(extractPaperInfoFromHtmlElement(result));
        }

        return new ArrayList<>(entries);
    }

    private PaperEntry extractPaperInfoFromHtmlElement(Element result) {

        return new PaperEntryBuilder()
                .ieee()
                .title(extractTitle(result))
                .year(extractYear(result))
                .author(extractAuthor(result))
                .conference(extractConference(result))
                .url("https://ieeexplore.ieee.org" + extractUrl(result))
                .citations(extractCitations(result))
                .build();
    }

    private int extractCitations(Element result) {
        try {
            String divText = result
                    .select(".description").select("div").get(2)
                    .text();

            if(divText.contains("Cited by")) {
                return Integer.parseInt(divText.replaceAll("[^0-9.]", ""));
            }

            // no citation info
            return -1;
        } catch (NumberFormatException e) {
            throw new InvalidPageException("Error extracting citation",e);
        }
    }

    private String extractConference(Element result) {
        try {
            return result.select(".description a").get(0).text();
        } catch (Exception e) {
            throw new InvalidPageException("Missing conference",e);
        }
    }

    private String extractAuthor(Element result) {
        try {
            return result.select(".author").text();
        } catch (Exception e) {
            throw new InvalidPageException("Missing author",e);
        }
    }

    private int extractYear(Element result) {
        try {
            return Integer.parseInt(result
                    .select(".publisher-info-container span").get(0)
                    .text()
                    .replaceAll("[^0-9.]", ""));
        } catch(Exception e) {
            throw new InvalidPageException("Missing url",e);
        }
    }

    private String extractUrl(Element result) {
        try {
            return result.select(".result-item h2 a").attr("href");
        } catch(Exception e) {
            throw new InvalidPageException("Missing url",e);
        }
    }

    private String extractTitle(Element result) {
        try {
            return result.select("h2 a").text();
        } catch(Exception e) {
            throw new InvalidPageException("Missing title",e);
        }
    }
}
