package nl.tudelft.serg.slrcrawler;

import nl.tudelft.serg.slrcrawler.library.Library;
import nl.tudelft.serg.slrcrawler.library.LibraryCrawler;
import nl.tudelft.serg.slrcrawler.library.LibraryParser;
import nl.tudelft.serg.slrcrawler.output.Outputter;
import nl.tudelft.serg.slrcrawler.storage.HtmlPageStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SLRCrawlerTest {

    @Mock HtmlPageStorage storage;

    @Mock Library l1;
    @Mock Library l2;
    @Mock LibraryCrawler c1;
    @Mock LibraryCrawler c2;
    @Mock LibraryParser p1;
    @Mock LibraryParser p2;

    @Mock HtmlPage htmlc11;
    @Mock HtmlPage htmlc12;
    @Mock HtmlPage htmlc21;

    @Mock Outputter out;

    @Mock PaperEntry entry1;
    @Mock PaperEntry entry2;
    @Mock PaperEntry entry3;
    @Mock PaperEntry entry4;
    @Mock PaperEntry entry5;
    @Mock PaperEntry entry6;

    SLRCrawler slr;
    String keywords = "a b";
    int maxNumberOfElements = 20;
    int startFrom = 0;

    @BeforeEach
    void setup() {
        this.slr = new SLRCrawler(Arrays.asList(l1, l2), storage, out);
    }

    @Test void
    navigate_in_the_right_pages() {
        createLibraries();
        bothCrawlersWorkSuccessfully();
        bothParsersWorkSuccessfully();

        slr.collect(keywords, maxNumberOfElements, startFrom);

        verify(l1, atLeastOnce()).firstPage(startFrom);
        verify(l1, atLeastOnce()).lastPage(maxNumberOfElements);

        verify(l2, atLeastOnce()).firstPage(startFrom);
        verify(l2, atLeastOnce()).lastPage(maxNumberOfElements);

    }
    @Test void
    download_all_pages() {
        createLibraries();
        bothCrawlersWorkSuccessfully();
        bothParsersWorkSuccessfully();

        slr.collect(keywords, maxNumberOfElements, startFrom);

        verify(c1, times(1)).downloadPage(keywords, 0);
        verify(c1, times(1)).downloadPage(keywords, 1);
        verify(c2, times(1)).downloadPage(keywords, 0);
    }

    @Test void
    stores_everything() {
        createLibraries();
        bothCrawlersWorkSuccessfully();
        bothParsersWorkSuccessfully();

        slr.collect(keywords, maxNumberOfElements, startFrom);

        verify(storage, times(1)).store(htmlc11);
        verify(storage, times(1)).store(htmlc12);
        verify(storage, times(1)).store(htmlc21);
    }

    @Test void
    parses_everything() {
        createLibraries();
        bothCrawlersWorkSuccessfully();
        bothParsersWorkSuccessfully();

        slr.collect(keywords, maxNumberOfElements, startFrom);

        verify(p1, times(1)).parse(htmlc11);
        verify(p1, times(1)).parse(htmlc12);
        verify(p2, times(1)).parse(htmlc21);
    }

    @Test void
    outputs_everything() {
        createLibraries();
        bothCrawlersWorkSuccessfully();
        bothParsersWorkSuccessfully();

        slr.collect(keywords, maxNumberOfElements, startFrom);

        verify(out, times(1)).write(entry1);
        verify(out, times(1)).write(entry2);
        verify(out, times(1)).write(entry3);
        verify(out, times(1)).write(entry4);
        verify(out, times(1)).write(entry5);
        verify(out, times(1)).write(entry6);
    }

    @Test void
    do_not_stop_due_to_exceptions_in_parsing() {
        createLibraries();
        bothCrawlersWorkSuccessfully();
        aParserFails();

        slr.collect(keywords, maxNumberOfElements, startFrom);

        verify(out, never()).write(entry1);
        verify(out, never()).write(entry2);
        verify(out, times(1)).write(entry3);
        verify(out, times(1)).write(entry4);
        verify(out, times(1)).write(entry5);
        verify(out, times(1)).write(entry6);

    }

    @Test void
    do_not_stop_due_to_exceptions_in_crawling() {
        createLibraries();
        aCrawlerFails();
        parserWorksEvenThoughCrawlerFails();

        slr.collect(keywords, maxNumberOfElements, startFrom);

        verify(storage, never()).store(htmlc11);
        verify(p1, never()).parse(htmlc11);
    }

    private void parserWorksEvenThoughCrawlerFails() {
        when(p1.parse(htmlc12)).thenReturn(Arrays.asList(entry3, entry4));
        parser2works();
    }

    private void aCrawlerFails() {
        when(c1.downloadPage(keywords, 0)).thenThrow(new RuntimeException());
        when(c1.downloadPage(keywords, 1)).thenReturn(htmlc12);
        crawler2works();
    }

    private void bothParsersWorkSuccessfully() {
        parser1works();
        parser2works();
    }

    private void aParserFails() {
        when(p1.parse(htmlc11)).thenThrow(new RuntimeException());
        when(p1.parse(htmlc12)).thenReturn(Arrays.asList(entry3, entry4));

        parser2works();
    }


    private void bothCrawlersWorkSuccessfully() {
        crawler1works();
        crawler2works();
    }

    private void parser2works() {
        // parser 2 return paper entries
        when(p2.parse(htmlc21)).thenReturn(Arrays.asList(entry5, entry6));
    }

    private void parser1works() {
        // parser 1 returns paper entries
        when(p1.parse(htmlc11)).thenReturn(Arrays.asList(entry1, entry2));
        when(p1.parse(htmlc12)).thenReturn(Arrays.asList(entry3, entry4));
    }

    private void crawler2works() {
        // crawler 2 is called two times and returns two different pages
        when(c2.downloadPage(keywords, 0)).thenReturn(htmlc21);
    }

    private void crawler1works() {
        // crawler 1 is called two times and returns two different pages
        when(c1.downloadPage(keywords, 0)).thenReturn(htmlc11);
        when(c1.downloadPage(keywords, 1)).thenReturn(htmlc12);
    }

    private void createLibraries() {
        // set the two libraries, their crawlers and parsers
        when(l1.crawler()).thenReturn(c1);
        when(l1.parser()).thenReturn(p1);
        when(l1.firstPage(startFrom)).thenReturn(0);
        when(l1.lastPage(maxNumberOfElements)).thenReturn(2);

        when(l2.crawler()).thenReturn(c2);
        when(l2.parser()).thenReturn(p2);
        when(l2.firstPage(startFrom)).thenReturn(0);
        when(l2.lastPage(maxNumberOfElements)).thenReturn(1);
    }
}
