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
        String possibleYearAsString = part.substring(part.length() - 4);

        if(possibleYearAsString.trim().matches("\\d\\d\\d\\d")) {
            return Integer.parseInt(possibleYearAsString);
        }

        /* Year is not there... That happens in some strange citations */
        return 0;
    }

    @Override
    protected PaperEntryBuilder paperEntry() {
        return new PaperEntryBuilder().fromScholar();
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
        if(citedByElement.attr("href").contains("?cites"))
            return Integer.parseInt(citedByElement.text().replaceAll("[^0-9.]", ""));
        else
            return 0; /* no citations available */
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
        return result.select(".gs_rt")
                .text()
                .replace("[PDF]", "")
                .replace("[HTML]", "")
                .trim();
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
