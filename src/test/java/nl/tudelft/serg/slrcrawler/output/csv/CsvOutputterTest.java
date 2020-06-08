package nl.tudelft.serg.slrcrawler.output.csv;

import nl.tudelft.serg.slrcrawler.PaperEntry;
import nl.tudelft.serg.slrcrawler.PaperEntryBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static nl.tudelft.serg.slrcrawler.FileReader.readFile;
import static org.assertj.core.api.Assertions.assertThat;

public class CsvOutputterTest {

    @Test
    void
    generate_csvs(@TempDir Path tempDir) throws IOException {
        CsvOutputter out = new CsvOutputter(Paths.get(tempDir.toString(), "temp.csv").toString());

        out.write(anyValidPaperEntry());
        out.close();

        String result = readFile(tempDir, "temp.csv");

        String[] lines = result.split("\n");
        assertThat(lines[0].trim()).isEqualTo("library,title,url,first author,year,citations");
        assertThat(lines[1].trim()).isEqualTo("scholar,Software systems as cities: A controlled experiment,https://dl.acm.org/doi/abs/10.1145/1985793.1985868,R Wettel,2011,228");
    }

    private PaperEntry anyValidPaperEntry() {
        return new PaperEntryBuilder()
                .scholar()
                .title("Software systems as cities: A controlled experiment")
                .url("https://dl.acm.org/doi/abs/10.1145/1985793.1985868")
                .author("R Wettel")
                .year(2011)
                .citations(228)
                .build();
    }
}
