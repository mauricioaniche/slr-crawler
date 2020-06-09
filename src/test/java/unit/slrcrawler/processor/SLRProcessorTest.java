package unit.slrcrawler.processor;

import nl.tudelft.serg.slrcrawler.library.Library;
import nl.tudelft.serg.slrcrawler.output.Outputter;
import nl.tudelft.serg.slrcrawler.processor.ExceptionHandler;
import nl.tudelft.serg.slrcrawler.processor.PageProcessor;
import nl.tudelft.serg.slrcrawler.processor.SLRProcessor;
import nl.tudelft.serg.slrcrawler.storage.HtmlPageStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SLRProcessorTest {

    public static final String anyKeywords = "a b c";
    @Mock Library library;
    @Mock HtmlPageStorage storage;
    @Mock Outputter outputter;
    @Mock PageProcessor pageProcessor;
    @Mock List<Library> libraries;
    @Mock ExceptionHandler exceptionHandler;

    private SLRProcessor slrProcessor;


    @BeforeEach
    void setup() {
        libraries = new ArrayList<>() {{
            add(library);
        }};

        slrProcessor = new SLRProcessor(libraries, storage, outputter, pageProcessor, exceptionHandler);
    }

    @ParameterizedTest
    @CsvSource({"0,5", "0,0", "5, 5", "5, 10"})
    void
    processes_all_pages_according_to_limits_of_the_library(int firstPage, int lastPage) {
        when(library.firstPage(0)).thenReturn(firstPage);
        when(library.lastPage(50)).thenReturn(lastPage);

        slrProcessor.collect(anyKeywords, 0, 50);

        for(int i = firstPage; i <= lastPage; i++) {
            verify(pageProcessor).process(anyKeywords, library, i);
        }
        verifyNoMoreInteractions(pageProcessor);
    }

    @Test void
    processes_all_libraries() {
        Library library2 = mock(Library.class);
        libraries.add(library2);

        when(library.firstPage(0)).thenReturn(0);
        when(library.lastPage(50)).thenReturn(0);
        when(library2.firstPage(0)).thenReturn(0);
        when(library2.lastPage(50)).thenReturn(0);

        slrProcessor.collect(anyKeywords, 0, 50);

        verify(pageProcessor).process(eq(anyKeywords), eq(library), any(Integer.class));
        verify(pageProcessor).process(eq(anyKeywords), eq(library2), any(Integer.class));
    }

    @Test void
    handle_exceptions_from_processor() {
        when(library.firstPage(0)).thenReturn(0);
        when(library.lastPage(50)).thenReturn(2);

        // throws an exception for page 1
        RuntimeException exception = new RuntimeException("some error");
        doThrow(exception)
                .when(pageProcessor)
                .process(anyKeywords, library, 1);

        // Mockito forces us to define the behavior of the other calls
        doNothing().when(pageProcessor).process(anyKeywords, library, 0);
        doNothing().when(pageProcessor).process(anyKeywords, library, 2);

        slrProcessor.collect(anyKeywords, 0, 50);

        verify(exceptionHandler).handle(exception);
        verifyNoMoreInteractions(exceptionHandler);
    }

}
