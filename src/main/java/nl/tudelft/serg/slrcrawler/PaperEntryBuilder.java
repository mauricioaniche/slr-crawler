package nl.tudelft.serg.slrcrawler;

public class PaperEntryBuilder {

    private String author;
    private int year = 0;
    private int citations = -1;
    private String url;
    private String conference = "(no conference)";
    private String title;
    private String library;

    public PaperEntryBuilder fromSpringer() {
        this.library = "springer";
        return this;
    }

    public PaperEntryBuilder fromScholar() {
        this.library = "scholar";
        return this;
    }

    public PaperEntryBuilder fromIEEE() {
        this.library = "ieee";
        return this;
    }

    public PaperEntryBuilder fromACM() {
        this.library = "acm";
        return this;
    }

    public PaperEntryBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public PaperEntryBuilder fromAuthor(String author) {
        this.author = author;
        return this;
    }

    public PaperEntryBuilder inYear(int year) {
        this.year = year;
        return this;
    }

    public PaperEntryBuilder withCitations(int citations) {
        this.citations = citations;
        return this;
    }

    public PaperEntryBuilder downloadableFrom(String url) {
        this.url = url;
        return this;
    }

    public PaperEntryBuilder publishedAt(String conference) {
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
