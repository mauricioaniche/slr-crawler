package nl.tudelft.serg.slrcrawler.library.scholar;

public class GoogleScholarConfig {

    public final int startingYear;

    public GoogleScholarConfig(int startingYear) {
        this.startingYear = startingYear;
    }

    public int getStartingYear() {
        return startingYear;
    }

    public static GoogleScholarConfig none() {
        return new GoogleScholarConfig(0);
    }
}
