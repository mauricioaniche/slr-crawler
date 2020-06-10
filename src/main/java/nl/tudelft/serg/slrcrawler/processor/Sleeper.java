package nl.tudelft.serg.slrcrawler.processor;

public class Sleeper {

    private final int seconds;

    public Sleeper(int seconds) {
        this.seconds = seconds;
    }

    public void sleep() {
        if(seconds == 0)
            return;

        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException("Could not sleep.. What's wrong?", e);
        }
    }
}
