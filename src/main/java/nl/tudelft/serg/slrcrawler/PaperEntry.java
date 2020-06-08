package nl.tudelft.serg.slrcrawler;

import java.util.Objects;

public class PaperEntry {

    private final String title;
    private final String conference;
    private final String url;
    private final String author;
    private final int year;
    private final int citations;
    private final String library;

    @Deprecated
    PaperEntry(String library, String title, String conference, String url, String author, int year, int citations) {
        this.library = library;
        this.title = title;
        this.conference = conference;
        this.url = url;
        this.author = author;
        this.year = year;
        this.citations = citations;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
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

    public String getConference() {
        return conference;
    }

    public String getLibrary() {
        return library;
    }

    @Override
    public String toString() {
        return "PaperEntry{" +
                "title='" + title + '\'' +
                ", conference='" + conference + '\'' +
                ", url='" + url + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                ", citations=" + citations +
                ", library='" + library + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaperEntry that = (PaperEntry) o;
        return year == that.year &&
                citations == that.citations &&
                Objects.equals(title, that.title) &&
                Objects.equals(conference, that.conference) &&
                Objects.equals(url, that.url) &&
                Objects.equals(author, that.author) &&
                Objects.equals(library, that.library);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, conference, url, author, year, citations, library);
    }
}
