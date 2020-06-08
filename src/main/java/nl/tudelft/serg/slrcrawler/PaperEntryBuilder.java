package nl.tudelft.serg.slrcrawler;

public class PaperEntryBuilder {

    private String author;
    private int year = 0;
    private int citations = -1;
    private String url;
    private String conference = "(no conference)";
    private String title;
    private String library;

    public PaperEntryBuilder scholar() {
        this.library = "scholar";
        return this;
    }

    public PaperEntryBuilder ieee() {
        this.library = "ieee";
        return this;
    }

    public PaperEntryBuilder title(String title) {
        this.title = title;
        return this;
    }

    public PaperEntryBuilder author(String author) {
        this.author = author;
        return this;
    }

    public PaperEntryBuilder year(int year) {
        this.year = year;
        return this;
    }

    public PaperEntryBuilder citations(int citations) {
        this.citations = citations;
        return this;
    }

    public PaperEntryBuilder url(String url) {
        this.url = url;
        return this;
    }

    public PaperEntryBuilder conference(String conference) {
        this.conference = conference;
        return this;
    }

    @SuppressWarnings("deprecation")
    public PaperEntry build() {
        /**
         * Year, library, title, url, and authors are compulsory
         */
        if(year==0)
            throw new InvalidPaperEntryException("Missing year");
        if(title==null)
            throw new InvalidPaperEntryException("Missing title");
        if(url==null)
            throw new InvalidPaperEntryException("Missing url");
        if(author==null)
            throw new InvalidPaperEntryException("Missing author");
        if(library==null)
            throw new InvalidPaperEntryException("Missing library");

        return new PaperEntry(library, title, conference, url, author, year, citations);
    }
}
