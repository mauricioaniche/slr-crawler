package unit.slrcrawler.library;

import nl.tudelft.serg.slrcrawler.library.MLAParser;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class MLAParserTest {

    @ParameterizedTest
    @MethodSource("mlaGenerator")
    void
    parse_mla(String mlaString, String expectedAuthor, String expectedConference) {
        MLAParser.MLA mla = MLAParser.parse(mlaString);

        assertThat(mla.getAuthor()).isEqualTo(expectedAuthor);
        assertThat(mla.getConference()).isEqualTo(expectedConference);

    }

    static Stream<Arguments> mlaGenerator() {
        Arguments tc1 = Arguments.of(
                "Van Emden, Eva, and Leon Moonen. \"Java quality assurance by detecting code smells.\" Ninth Working Conference on Reverse Engineering, 2002. Proceedings.. IEEE, 2002.",
                "Van Emden, Eva, and Leon Moonen",
                "Ninth Working Conference on Reverse Engineering, 2002. Proceedings.");

        Arguments tc2 = Arguments.of(
                "Yamashita, Aiko, and Leon Moonen. \"Do code smells reflect important maintainability aspects?.\" 2012 28th IEEE international conference on software maintenance (ICSM). IEEE, 2012.",
                "Yamashita, Aiko, and Leon Moonen",
                "2012 28th IEEE international conference on software maintenance (ICSM)");

        Arguments tc3 = Arguments.of(
                "Khomh, Foutse, Massimiliano Di Penta, and Yann-Gael Gueheneuc. \"An exploratory study of the impact of code smells on software change-proneness.\" 2009 16th Working Conference on Reverse Engineering. IEEE, 2009.",
                "Khomh, Foutse, Massimiliano Di Penta, and Yann-Gael Gueheneuc",
                "2009 16th Working Conference on Reverse Engineering");

        Arguments tc4 = Arguments.of(
                "Fontana, Francesca Arcelli, Pietro Braione, and Marco Zanoni. \"Automatic detection of bad smells in code: An experimental assessment.\" Journal of Object Technology 11.2 (2012): 5-1.",
                "Fontana, Francesca Arcelli, Pietro Braione, and Marco Zanoni",
                "Journal of Object Technology 11.2 (2012): 5-1");

        Arguments tc5 = Arguments.of(
                "Guo, Yuepu, et al. \"Domain-specific tailoring of code smells: an empirical study.\" Proceedings of the 32nd ACM/IEEE International Conference on Software Engineering-Volume 2. 2010.",
                "Guo, Yuepu, et al",
                "Proceedings of the 32nd ACM/IEEE International Conference on Software Engineering-Volume 2"
        );

        return Stream.of(tc1, tc2, tc3, tc4, tc5);
    }
}
