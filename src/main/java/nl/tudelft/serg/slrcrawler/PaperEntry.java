package nl.tudelft.serg.slrcrawler;

import java.util.Objects;

public class PaperEntry {

    private final String title;
    private final String url;
    private final String firstAuthor;
    private final int year;
    private final int citation;

    public PaperEntry(String title, String url, String firstAuthor, int year, int citation) {
        this.title = title;
        this.url = url;
        this.firstAuthor = firstAuthor;
        this.year = year;
        this.citation = citation;
    }

    public String getTitle() {
        return title;
    }

    public String getFirstAuthor() {
        return firstAuthor;
    }

    public int getYear() {
        return year;
    }

    public int getCitation() {
        return citation;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "PaperEntry{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", firstAuthor='" + firstAuthor + '\'' +
                ", year=" + year +
                ", citation=" + citation +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaperEntry that = (PaperEntry) o;
        return year == that.year &&
                citation == that.citation &&
                title.equals(that.title) &&
                url.equals(that.url) &&
                firstAuthor.equals(that.firstAuthor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, url, firstAuthor, year, citation);
    }
}
