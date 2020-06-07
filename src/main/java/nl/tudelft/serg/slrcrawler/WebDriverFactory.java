package nl.tudelft.serg.slrcrawler;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

public class WebDriverFactory {

    public WebDriver build() {
        SafariOptions options = new SafariOptions();
        return new SafariDriver(options);
    }
}
