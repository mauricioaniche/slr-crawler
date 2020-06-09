package unit.slrcrawler.library;

import nl.tudelft.serg.slrcrawler.library.Library;
import nl.tudelft.serg.slrcrawler.library.LibraryCrawler;
import nl.tudelft.serg.slrcrawler.library.LibraryParser;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class LibraryTest {

    final Library library = new Library() {
        @Override
        public LibraryCrawler crawler() {
            return null;
        }

        @Override
        public LibraryParser parser() {
            return null;
        }

        @Override
        public String name() {
            return null;
        }

        @Override
        public int elementsPerPage() {
            return 10;
        }
    };

    /**
     * Domain analysis
     *
     * Variables:
     * - first element:
     *   - negative (exceptional)
     *   - zero
     *   - not negative
     *
     * - last element
     *   - negative (exceptional)
     *   - zero (exceptional)
     *   - not negative
     *
     * - (first element, last element):
     *   - first element == last element
     *   - first element > last element (exceptional, this can not be dealt with here)
     *   - 0 < first element < elements per page
     *   - first element and last element belong to the same page
     *   - first element and last element belong to different pages
     *
     * - boundaries:
     *   - first element == elements per page
     *   - first element == elements per page - 1
     *   - first element == elements per page + 1
     *
     *   - last element == multiple of elements per page
     *   - last element == multiple of elements per page - 1
     *   - last element == multiple of elements per page + 1
     */
    @ParameterizedTest
    @MethodSource("testCases")
    void
    page_intervals(String description, int firstElement, int lastElement, int expectedFirstPage, int expectedLastPage) {
        assertThat(library.firstPageInclusive(firstElement)).isEqualTo(expectedFirstPage);
        assertThat(library.lastPageInclusive(lastElement)).isEqualTo(expectedLastPage);
    }

    static Stream<Arguments> testCases() {
        return Stream.of(
            Arguments.of("first element zero", 0, 5, 0, 0),
            Arguments.of("first and last element zero", 0, 0, 0, 0),
            Arguments.of("first element == elements per page", 10, 15, 1, 1),
            Arguments.of("first element == elements per page - 1", 9, 15, 0, 1),
            Arguments.of("first element == elements per page + 1", 11, 15, 1, 1),
            Arguments.of("last element == elements per page", 5, 20, 0, 2),
            Arguments.of("last element == elements per page - 1", 9, 21, 0, 2),
            Arguments.of("last element == elements per page + 1", 11, 19, 1, 1),
            Arguments.of("first element and last element belong to the same page", 23, 27, 2, 2),
            Arguments.of("first element and last element belong to different pages", 23, 37, 2, 3),
            Arguments.of("negative numbers", -1, -1, 0, 0)
        );
    }
}
