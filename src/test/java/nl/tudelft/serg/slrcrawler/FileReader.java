package nl.tudelft.serg.slrcrawler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileReader {
    public static String readFile(Path tempDir, String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(tempDir.toString(), fileName)));
    }
}
