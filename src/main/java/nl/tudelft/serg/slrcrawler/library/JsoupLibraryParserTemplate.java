package nl.tudelft.serg.slrcrawler.library;

import nl.tudelft.serg.slrcrawler.HtmlPage;
import nl.tudelft.serg.slrcrawler.PaperEntry;
import nl.tudelft.serg.slrcrawler.PaperEntryBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public abstract class JsoupLibraryParserTemplate implements LibraryParser {

    @Override
    public List<PaperEntry> parse(HtmlPage htmlPage) {
        java.util.Set<PaperEntry> entries = new HashSet<>();

        Document doc = Jsoup.parse(htmlPage.getHtml());
        Elements results = papers(doc);

        for (Element result : results) {
            entries.add(extractPaperInfoFromHtmlElement(result));
        }

        return new ArrayList<>(entries);
    }

    private PaperEntry extractPaperInfoFromHtmlElement(Element result) {
        return paperEntry()
                .withTitle(extractTitleOrException(result))
                .inYear(extractYearOrException(result))
                .fromAuthor(extractAuthorOrException(result))
                .publishedAt(extractConferenceOrException(result))
                .downloadableFrom(urlPrefix() + extractUrlOrException(result))
                .withCitations(extractCitationsOrException(result))
            .build();
    }

    private int extractCitationsOrException(Element result) {
        try {
            return extractCitations(result);
        } catch (Exception e) {
            throw new InvalidPageException("Error extracting citations", e);
        }
    }

    private String extractUrlOrException(Element result) {
        try {
            return extractUrl(result);
        } catch (Exception e) {
            throw new InvalidPageException("Error extracting url", e);
        }
    }

    private String extractConferenceOrException(Element result) {
        try {
            return extractConference(result);
        } catch (Exception e) {
            throw new InvalidPageException("Error extracting conference", e);
        }
    }

    private String extractAuthorOrException(Element result) {
        try {
            return extractAuthor(result);
        } catch (Exception e) {
            throw new InvalidPageException("Error extracting author", e);
        }
    }

    private int extractYearOrException(Element result) {
        try {
            return extractYear(result);
        } catch (Exception e) {
            throw new InvalidPageException("Error extracting year", e);
        }
    }

    private String extractTitleOrException(Element result) {
        try {
            return extractTitle(result);
        } catch (Exception e) {
            throw new InvalidPageException("Error extracting title", e);
        }
    }

    protected abstract PaperEntryBuilder paperEntry();

    protected abstract Elements papers(Document doc);

    protected abstract int extractCitations(Element result);

    protected abstract String extractUrl(Element result);

    protected abstract String extractConference(Element result);

    protected abstract String extractAuthor(Element result);

    protected abstract int extractYear(Element result);

    protected abstract String extractTitle(Element result);

    protected abstract String urlPrefix();
}
