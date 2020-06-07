package nl.tudelft.serg.slrcrawler.output;

import nl.tudelft.serg.slrcrawler.PaperEntry;

public interface Outputter {
    void write(PaperEntry entry);
    void close();
}
