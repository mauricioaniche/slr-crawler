package nl.tudelft.serg.slrcrawler.library.scholar;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class GoogleScholarLibraryTest {

    final GoogleScholarLibrary library = new GoogleScholarLibrary();

    @ParameterizedTest
    @CsvSource({
            "0, 0",
            "1, 1",
            "10, 1",
            "11, 2"})
    void
    number_of_pages_for_total_amount_of_elements(int maxNoOfElements, int expectedNumberOfPages) {
        assertThat(library.pagesForMaxNumberOfElements(maxNoOfElements))
                .isEqualTo(expectedNumberOfPages);
    }
}
