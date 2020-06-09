package unit.slrcrawler.library.scholar;

import nl.tudelft.serg.slrcrawler.library.scholar.GoogleScholarLibrary;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class GoogleScholarLibraryTest {

    final GoogleScholarLibrary library = new GoogleScholarLibrary(mock(WebDriver.class));

    @ParameterizedTest
    @CsvSource({
            "0, 0",
            "1, 1",
            "10, 1",
            "11, 2"})
    void
    number_of_pages_for_total_amount_of_elements(int maxNoOfElements, int expectedNumberOfPages) {
        assertThat(library.lastPage(maxNoOfElements))
                .isEqualTo(expectedNumberOfPages);
    }
}
