package unit.slrcrawler.library.springer;

import nl.tudelft.serg.slrcrawler.library.springer.SpringerConfig;
import org.apache.commons.validator.routines.UrlValidator;
import org.junit.jupiter.api.Test;

import static nl.tudelft.serg.slrcrawler.library.springer.SpringerUrlBuilder.buildUrl;
import static nl.tudelft.serg.slrcrawler.util.UrlEncoder.encode;
import static org.assertj.core.api.Assertions.assertThat;

public class SpringerUrlBuilderTest {

    UrlValidator urlValidator = new UrlValidator();

    @Test
    void
    build_url_with_no_configs() {
        String url = buildUrl(SpringerConfig.none(), "a b c", 0);

        assertThat(url)
                .contains("page/1")
                .contains(encode("a b c"))
                .doesNotContain("facet-");

        assertThat(urlValidator.isValid(url)).isTrue();
    }

    @Test
    void
    build_url_with_all_configs() {
        SpringerConfig config = new SpringerConfig("Computer Science", "Software Engineering", "ConferencePaper");
        String url = buildUrl(config, "a b c", 0);

        assertThat(url)
                .contains("page/1")
                .contains("facet-discipline=" + encode("Computer Science"))
                .contains("facet-sub-discipline=" + encode("Software Engineering"))
                .contains("facet-content-type=" + encode("ConferencePaper"));

        assertThat(urlValidator.isValid(url)).isTrue();
    }


}
