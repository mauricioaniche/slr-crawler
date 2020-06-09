package unit.slrcrawler.processor;

import nl.tudelft.serg.slrcrawler.HtmlPage;
import nl.tudelft.serg.slrcrawler.PaperEntry;
import nl.tudelft.serg.slrcrawler.library.Library;
import nl.tudelft.serg.slrcrawler.library.LibraryCrawler;
import nl.tudelft.serg.slrcrawler.library.LibraryParser;
import nl.tudelft.serg.slrcrawler.output.Outputter;
import nl.tudelft.serg.slrcrawler.processor.PageProcessor;
import nl.tudelft.serg.slrcrawler.storage.HtmlPageStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PageProcessorTest {

    public static final String anyKeyword = "a b c";
    public static final int anyPage = 1;

    @Mock HtmlPageStorage storage;
    @Mock Outputter outputter;
    @Mock Library library;
    @Mock LibraryCrawler crawler;
    @Mock LibraryParser parser;
    PageProcessor processor;

    @BeforeEach
    void setup() {
        when(library.parser()).thenReturn(parser);
        when(library.crawler()).thenReturn(crawler);

        processor = new PageProcessor(storage, outputter);
    }

    @Test void
    download_page() {
        processor.process(anyKeyword, library, anyPage);
        verify(crawler).downloadPage(anyKeyword, anyPage);
        verifyNoMoreInteractions(crawler);
    }

    @Test void
    parse_page() {
        HtmlPage htmlPage = mock(HtmlPage.class);
        when(crawler.downloadPage(anyKeyword, anyPage)).thenReturn(htmlPage);

        processor.process(anyKeyword, library, 1);
        verify(parser).parse(htmlPage);
        verifyNoMoreInteractions(parser);
    }

    @Test void
    store_page() {
        HtmlPage htmlPage = mock(HtmlPage.class);
        when(crawler.downloadPage(anyKeyword, anyPage)).thenReturn(htmlPage);

        processor.process(anyKeyword, library, 1);
        verify(storage).store(htmlPage);
        verifyNoMoreInteractions(storage);
    }

    @Test void
    output_paper_entry() {
        HtmlPage htmlPage = mock(HtmlPage.class);
        when(crawler.downloadPage(anyKeyword, anyPage)).thenReturn(htmlPage);

        PaperEntry entry1 = mock(PaperEntry.class);
        PaperEntry entry2 = mock(PaperEntry.class);
        when(parser.parse(htmlPage)).thenReturn(Arrays.asList(entry1, entry2));

        processor.process(anyKeyword, library, 1);
        verify(outputter).write(entry1);
        verify(outputter).write(entry2);
        verifyNoMoreInteractions(outputter);
    }
}
