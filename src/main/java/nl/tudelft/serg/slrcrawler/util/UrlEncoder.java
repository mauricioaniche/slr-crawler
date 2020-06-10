package nl.tudelft.serg.slrcrawler.util;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class UrlEncoder {
    public static String encode(String keywords) {
        return URLEncoder.encode(keywords, StandardCharsets.UTF_8);
    }
}
