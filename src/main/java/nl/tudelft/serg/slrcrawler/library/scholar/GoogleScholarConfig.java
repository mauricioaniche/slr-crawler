package nl.tudelft.serg.slrcrawler.library.scholar;

public class GoogleScholarConfig {

    public final int startingYear;
    private final boolean augmented;

    public GoogleScholarConfig(int startingYear, boolean augmented) {
        this.startingYear = startingYear;
        this.augmented = augmented;
    }

    public int getStartingYear() {
        return startingYear;
    }

    public static GoogleScholarConfig none() {
        return new GoogleScholarConfig(0, false);
    }

    public boolean isAugmented() {
        return augmented;
    }
}
