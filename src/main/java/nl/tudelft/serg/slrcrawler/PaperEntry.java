package nl.tudelft.serg.slrcrawler;

import java.util.Objects;

public class PaperEntry {

    private final String title;
    private final String conference;
    private final String url;
    private final String author;
    private final int year;
    private final int citations;

    PaperEntry(String title, String conference, String url, String author, int year, int citations) {
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

    @Override
    public String toString() {
        return "PaperEntry{" +
                "title='" + title + '\'' +
                ", conference='" + conference + '\'' +
                ", url='" + url + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                ", citations=" + citations +
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
                conference.equals(that.conference) &&
                url.equals(that.url) &&
                author.equals(that.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, conference, url, author, year, citations);
    }
}
