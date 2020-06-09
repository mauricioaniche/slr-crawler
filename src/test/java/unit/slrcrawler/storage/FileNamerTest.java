package unit.slrcrawler.storage;

import nl.tudelft.serg.slrcrawler.HtmlPage;
import nl.tudelft.serg.slrcrawler.storage.FileNamer;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

public class FileNamerTest {

    @Test void
    give_file_name() {
        FileNamer fileNamer = new FileNamer();

        HtmlPage htmlPage1 = createHtmlPage("scholar", 1, noon());
        assertThat(fileNamer.name(htmlPage1, "html"))
                .isEqualTo("scholar-p1-t2020-01-23-12-00-00.html");
    }

    @Test void
    no_white_spaces_in_name() {
        FileNamer fileNamer = new FileNamer();

        HtmlPage htmlPage2 = createHtmlPage("other library", 2, noon());
        assertThat(fileNamer.name(htmlPage2, "json"))
                .isEqualTo("other-library-p2-t2020-01-23-12-00-00.json");
    }

    @Test void
    no_time_stamp() {
        FileNamer fileNamer = new FileNamer(false);

        HtmlPage htmlPage1 = createHtmlPage("scholar", 1, noon());
        assertThat(fileNamer.name(htmlPage1, "html"))
                .isEqualTo("scholar-p1.html");
    }

    @SuppressWarnings("deprecation")
    private HtmlPage createHtmlPage(String library, int pageNumber, LocalDateTime dt) {
        return new HtmlPage(library, pageNumber, "<any url, doesn't matter>", "<any html>", dt);
    }

    private LocalDateTime noon() {
        return LocalDateTime.of(LocalDate.of(2020, 1, 23), LocalTime.NOON);
    }
}
