package nl.tudelft.serg.slrcrawler.library.ieee;

import nl.tudelft.serg.slrcrawler.PaperEntryBuilder;
import nl.tudelft.serg.slrcrawler.library.JsoupLibraryParserTemplate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class IEEEXploreParser extends JsoupLibraryParserTemplate {

    @Override
    protected Elements papers(Document doc) {
        return doc.select(".main-section .List-results-items")
                .select(".hide-mobile");
    }

    @Override
    protected PaperEntryBuilder paperEntry() {
        return new PaperEntryBuilder().ieee();
    }

    protected int extractCitations(Element result) {
        String divText = result
                .select(".description").select("div").get(2)
                .text();

        if(divText.contains("Cited by")) {
            return Integer.parseInt(divText.replaceAll("[^0-9.]", ""));
        }

        // no citation info
        return -1;
    }

    protected String extractConference(Element result) {
        return result.select(".description a").get(0).text();
    }

    protected String extractAuthor(Element result) {
        return result.select(".author").text();
    }

    protected int extractYear(Element result) {
        return Integer.parseInt(result
                .select(".publisher-info-container span").get(0)
                .text()
                .replaceAll("[^0-9.]", ""));
    }

    protected String extractUrl(Element result) {
        return result.select(".result-item h2 a").attr("href");
    }

    protected String extractTitle(Element result) {
        return result.select("h2 a").text();
    }

    @Override
    protected String urlPrefix() {
        return "https://ieeexplore.ieee.org";
    }
}
