package unit.slrcrawler.library.scholar;

import nl.tudelft.serg.slrcrawler.library.scholar.GoogleScholarConfig;
import org.junit.jupiter.api.Test;

import static nl.tudelft.serg.slrcrawler.library.scholar.GoogleScholarConfig.none;
import static nl.tudelft.serg.slrcrawler.library.scholar.GoogleScholarUrlBuilder.buildUrl;
import static nl.tudelft.serg.slrcrawler.util.UrlEncoder.encode;
import static org.assertj.core.api.Assertions.assertThat;

public class GoogleScholarUrlBuilderTest {

    @Test
    void
    build_url_with_no_config() {
        String url = buildUrl(none(), "a b c", 0);

        assertThat(url)
                .contains(encode("a b c"))
                .doesNotContain("as_ylo");
    }

    @Test
    void
    build_url_with_config() {
        String url = buildUrl(new GoogleScholarConfig(2010), "a b c", 0);

        assertThat(url)
                .contains(encode("a b c"))
                .contains("as_ylo=2010");
    }
}
