package nl.tudelft.serg.slrcrawler;

import java.time.LocalDateTime;
import java.util.Objects;

public class HtmlPage {

    private final String library;
    private final int pageNumber;
    private final String url;
    private final String html;
    private final LocalDateTime timeOfCollection;

    public HtmlPage(String library, int pageNumber, String url, String html) {
        this.library = library;
        this.pageNumber = pageNumber;
        this.url = url;
        this.html = html;
        timeOfCollection = LocalDateTime.now();
    }

    public String getLibrary() {
        return library;
    }

    public String getUrl() {
        return url;
    }

    public String getHtml() {
        return html;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public LocalDateTime getTimeOfCollection() {
        return timeOfCollection;
    }

    @Override
    public String toString() {
        return "HtmlPage{" +
                "library='" + library + '\'' +
                ", pageNumber=" + pageNumber +
                ", url='" + url + '\'' +
                ", html='" + html + '\'' +
                ", timeOfCollection=" + timeOfCollection +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HtmlPage htmlPage = (HtmlPage) o;
        return pageNumber == htmlPage.pageNumber &&
                library.equals(htmlPage.library) &&
                url.equals(htmlPage.url) &&
                html.equals(htmlPage.html) &&
                timeOfCollection.equals(htmlPage.timeOfCollection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(library, pageNumber, url, html, timeOfCollection);
    }
}
