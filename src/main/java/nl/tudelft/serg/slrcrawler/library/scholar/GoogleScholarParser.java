package nl.tudelft.serg.slrcrawler.library.scholar;

import nl.tudelft.serg.slrcrawler.PaperEntryBuilder;
import nl.tudelft.serg.slrcrawler.library.JsoupLibraryParserTemplate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GoogleScholarParser extends JsoupLibraryParserTemplate {

    /**
     * We get the link that is inside the h3 containing the title
     */
    protected String extractUrl(Element result) {
        return result
                .select(".gs_rt")
                .select("a")
                .attr("href");
    }

    /**
     * To get the year, we parse the author line.
     * Splitting by dash, we have the year in the last 4 characters of the string.
     */
    protected int extractYear(Element result) {
        String authorLine = getAuthorLine(result);
        String part = authorLine.split(" - ")[1];
        return Integer.parseInt(part.substring(part.length() - 4));
    }

    @Override
    protected PaperEntryBuilder paperEntry() {
        return new PaperEntryBuilder().scholar();
    }

    @Override
    protected Elements papers(Document doc) {
        return doc.select(".gs_ri");
    }

    /**
     * We find the 'cited by' element.
     * We remove all characters (the regex is there to avoid 'cited by' in other languages)
     */
    protected int extractCitations(Element result) {
        Element citedByElement = result.select(".gs_fl a").get(2);
        return Integer.parseInt(citedByElement.text().replaceAll("[^0-9.]", ""));
    }

    @Override
    protected String extractConference(Element result) {
        return "(no conference)";
    }

    /**
     * We get only the first author, as Google hides the names of the others
     * in long lists.
     */
    protected String extractAuthor(Element result) {
        String authorLine = getAuthorLine(result);
        return authorLine.split(",")[0];
    }

    protected String extractTitle(Element result) {
        return result.select(".gs_rt").text();
    }

    /**
     * The author line contains the authors and year.
     */
    private String getAuthorLine(Element result) {
        return result.select(".gs_a").text();
    }


    @Override
    protected String urlPrefix() {
        return "";
    }
}
