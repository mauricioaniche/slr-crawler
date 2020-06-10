package nl.tudelft.serg.slrcrawler.library.scholar;

import static nl.tudelft.serg.slrcrawler.util.UrlEncoder.encode;

public class GoogleScholarUrlBuilder {

    public static String buildUrl(GoogleScholarConfig config, String keywords, int zeroBasedPageNumber) {
        String url = String.format("https://scholar.google.com/scholar?start=%d&q=%s&hl=en",
                (zeroBasedPageNumber) * 10, /* not about pages, but number of the first element to show */
                encode(keywords));

        if(config.getStartingYear()>0)
            url += "&as_ylo=" + config.getStartingYear();

        return url;
    }

}
