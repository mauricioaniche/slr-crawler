package nl.tudelft.serg.slrcrawler.output.csv;

import nl.tudelft.serg.slrcrawler.PaperEntry;
import nl.tudelft.serg.slrcrawler.output.Outputter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;

public class CsvOutputter implements Outputter {

    private final CSVPrinter printer;

    public CsvOutputter(String file) {
        try {
            this.printer = new CSVPrinter(new FileWriter(file), CSVFormat.EXCEL);
            printer.printRecord("title", "url", "first author", "year", "citations");
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void write(PaperEntry entry) {
        try {
            printer.printRecord(entry.getTitle(), entry.getUrl(), entry.getFirstAuthor(), entry.getYear(), entry.getCitations());
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        try {
            printer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
