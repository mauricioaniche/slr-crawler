package nl.tudelft.serg.slrcrawler.library.acm;

import nl.tudelft.serg.slrcrawler.PaperEntry;
import nl.tudelft.serg.slrcrawler.PaperEntryBuilder;
import nl.tudelft.serg.slrcrawler.library.JsoupLibraryParserTemplate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ACMParser extends JsoupLibraryParserTemplate {

    /**
     * No year might mean it's a propaganda.
     */
    protected boolean isValid(PaperEntry entry) {
        return entry.getYear()>0;
    }

    @Override
    protected Elements papers(Document doc) {
        return doc.select(".issue-item--search");
    }

    @Override
    protected PaperEntryBuilder paperEntry() {
        return new PaperEntryBuilder().fromACM();
    }

    protected int extractCitations(Element result) {
        String divText = result
                .select(".citation").select("span")
                .text();

        return Integer.parseInt(divText.replaceAll("[^0-9.]", ""));
    }

    protected String extractConference(Element result) {
        return result.select(".epub-section__title").text();
    }

    protected String extractAuthor(Element result) {
        return result.select("[aria-label='authors']").text();
    }

    protected int extractYear(Element result) {
        Elements yearSpan = result
                .select(".issue-item__detail")
                .select(".dot-separator")
                .select("span");

        /**
         * The element is not there.
         * It might happen when ACM shows propaganda of some proceedings...
         */
        if(yearSpan.size()==0)
            return -1;

        String pubDate = yearSpan.get(0).text();

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

    protected String extractUrl(Element result) {
        return result.select(".issue-item__title a").attr("href");
    }

    protected String extractTitle(Element result) {
        return result
                .select(".issue-item__title")
                .text();
    }

    @Override
    protected String urlPrefix() {
        return "https://dl.acm.org";
    }
}
