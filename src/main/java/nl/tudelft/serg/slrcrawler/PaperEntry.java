package nl.tudelft.serg.slrcrawler;

import java.util.Objects;

public class PaperEntry {

    private final String title;
    private final String url;
    private final String firstAuthor;
    private final int year;
    private final int citations;

    public PaperEntry(String title, String url, String firstAuthor, int year, int citations) {
        this.title = title;
        this.url = url;
        this.firstAuthor = firstAuthor;
        this.year = year;
        this.citations = citations;
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

    public int getCitations() {
        return citations;
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
                ", citation=" + citations +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaperEntry that = (PaperEntry) o;
        return year == that.year &&
                citations == that.citations &&
                title.equals(that.title) &&
                url.equals(that.url) &&
                firstAuthor.equals(that.firstAuthor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, url, firstAuthor, year, citations);
    }
}
