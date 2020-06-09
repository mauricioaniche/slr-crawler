package nl.tudelft.serg.slrcrawler.library.springer;

import nl.tudelft.serg.slrcrawler.PaperEntryBuilder;
import nl.tudelft.serg.slrcrawler.library.JsoupLibraryParserTemplate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SpringerParser extends JsoupLibraryParserTemplate {
    @Override
    protected PaperEntryBuilder paperEntry() {
        return new PaperEntryBuilder().fromSpringer();
    }

    @Override
    protected Elements papers(Document doc) {
        return doc
                .select("#results-list")
                .select("ol")
                .select("li");
    }

    @Override
    protected int extractCitations(Element result) {
        return -1;
    }

    @Override
    protected String extractUrl(Element result) {
        return result.select(".title").attr("href");
    }

    @Override
    protected String extractConference(Element result) {
        return result.select(".publication-title")
                .attr("title");
    }

    @Override
    protected String extractAuthor(Element result) {
        Elements authorsSpan = result.select(".authors");

        String authors = authorsSpan
                .text()
                .replaceAll("[^\\x00-\\x7F]", ""); /* remove non-ascii chars */

        /* When the author list is too long, Springer adds other authors in a hidden span */
        String hiddenAuthors = authorsSpan.select("span").attr("title");
        if(!hiddenAuthors.trim().isEmpty())
            authors += ", " + hiddenAuthors.trim();

        return authors;
    }

    @Override
    protected int extractYear(Element result) {
        return Integer.parseInt(result
                .select(".year")
                .text()
                .replaceAll("[^0-9.]", ""));
    }

    @Override
    protected String extractTitle(Element result) {
        return result.select(".title").text();
    }

    @Override
    protected String urlPrefix() {
        return "https://link.springer.com";
    }
}
