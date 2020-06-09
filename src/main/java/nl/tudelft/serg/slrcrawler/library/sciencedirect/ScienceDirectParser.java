package nl.tudelft.serg.slrcrawler.library.sciencedirect;

import nl.tudelft.serg.slrcrawler.PaperEntryBuilder;
import nl.tudelft.serg.slrcrawler.library.JsoupLibraryParserTemplate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.stream.Collectors;

public class ScienceDirectParser extends JsoupLibraryParserTemplate {

    protected String extractUrl(Element result) {
        return result
                .select("h2")
                .select("span")
                .select("a")
                .attr("href");
    }

    protected int extractYear(Element result) {
        String pubDate = result.select(".SubType")
                .select("span")
                .get(3)
                .text();

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

    }

    @Override
    protected PaperEntryBuilder paperEntry() {
        return new PaperEntryBuilder().fromScienceDirect();
    }

    @Override
    protected Elements papers(Document doc) {
        return doc.select(".ResultItem");
    }

    protected int extractCitations(Element result) {
        return -1;
    }

    @Override
    protected String extractConference(Element result) {
        return result.select(".SubType")
                .select("span")
                .get(0)
                .text();
    }

    protected String extractAuthor(Element result) {
        return result
                .select(".Authors")
                .select(".author")
                .stream()
                .map(el -> el.text())
                .collect(Collectors.joining(", "));
    }

    protected String extractTitle(Element result) {
        return result
                .select("h2")
                .select("span")
                .select("a")
                .text();
    }

    @Override
    protected String urlPrefix() {
        return "https://www.sciencedirect.com";
    }
}
