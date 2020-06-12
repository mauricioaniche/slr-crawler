package unit.slrcrawler.library;

import nl.tudelft.serg.slrcrawler.library.ChicagoNotationParser;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class ChicagoNotationParserTest {

    @ParameterizedTest
    @MethodSource("mlaGenerator")
    void
    parse_mla(String mlaString, String expectedAuthor, String expectedConference) {
        ChicagoNotationParser.ChicagoNotation chicagoNotation = ChicagoNotationParser.parse(mlaString);

        assertThat(chicagoNotation.getAuthor()).isEqualTo(expectedAuthor);
        assertThat(chicagoNotation.getConference()).isEqualTo(expectedConference);

    }

    static Stream<Arguments> mlaGenerator() {
        Arguments tc1 = Arguments.of(
                "Van Emden, Eva, and Leon Moonen. \"Java quality assurance by detecting code smells.\" Ninth Working Conference on Reverse Engineering, 2002. Proceedings.. IEEE, 2002.",
                "Van Emden, Eva, and Leon Moonen",
                "Ninth Working Conference on Reverse Engineering, 2002. Proceedings.. IEEE");

        Arguments tc2 = Arguments.of(
                "Yamashita, Aiko, and Leon Moonen. \"Do code smells reflect important maintainability aspects?.\" 2012 28th IEEE international conference on software maintenance (ICSM). IEEE, 2012.",
                "Yamashita, Aiko, and Leon Moonen",
                "2012 28th IEEE international conference on software maintenance (ICSM). IEEE");

        Arguments tc3 = Arguments.of(
                "Khomh, Foutse, Massimiliano Di Penta, and Yann-Gael Gueheneuc. \"An exploratory study of the impact of code smells on software change-proneness.\" 2009 16th Working Conference on Reverse Engineering. IEEE, 2009.",
                "Khomh, Foutse, Massimiliano Di Penta, and Yann-Gael Gueheneuc",
                "2009 16th Working Conference on Reverse Engineering. IEEE");

        Arguments tc4 = Arguments.of(
                "Fontana, Francesca Arcelli, Pietro Braione, and Marco Zanoni. \"Automatic detection of bad smells in code: An experimental assessment.\" Journal of Object Technology 11.2 (2012): 5-1.",
                "Fontana, Francesca Arcelli, Pietro Braione, and Marco Zanoni",
                "Journal of Object Technology 11.2 (2012): 5-1");

        Arguments tc5 = Arguments.of(
                "Guo, Yuepu, et al. \"Domain-specific tailoring of code smells: an empirical study.\" Proceedings of the 32nd ACM/IEEE International Conference on Software Engineering-Volume 2. 2010.",
                "Guo, Yuepu, et al",
                "Proceedings of the 32nd ACM/IEEE International Conference on Software Engineering-Volume 2"
        );

        Arguments tc6 = Arguments.of(
            "Slinger, Stefan. \"Code smell detection in Eclipse.\" Delft University of Technology (2005).",
            "Slinger, Stefan",
            "Delft University of Technology (2005)"
        );

        Arguments tc7 = Arguments.of(
                "Vidal, Santiago A., Claudia Marcos, and J. Andrés Díaz-Pace. \"An approach to prioritize code smells for refactoring.\" Automated Software Engineering 23, no. 3 (2016): 501-532.",
                "Vidal, Santiago A., Claudia Marcos, and J. Andrés Díaz-Pace",
                "Automated Software Engineering 23, no. 3 (2016): 501-532"
        );

        Arguments tc8 = Arguments.of(
                "Sjøberg, Dag IK, Aiko Yamashita, Bente CD Anda, Audris Mockus, and Tore Dybå. \"Quantifying the effect of code smells on maintenance effort.\" IEEE Transactions on Software Engineering 39, no. 8 (2012): 1144-1156.",
                "Sjøberg, Dag IK, Aiko Yamashita, Bente CD Anda, Audris Mockus, and Tore Dybå",
                "IEEE Transactions on Software Engineering 39, no. 8 (2012): 1144-1156"
        );

        Arguments tc9 = Arguments.of(
                "Olbrich, Steffen, Daniela S. Cruzes, Victor Basili, and Nico Zazworka. \"The evolution and impact of code smells: A case study of two open source systems.\" In 2009 3rd international symposium on empirical software engineering and measurement, pp. 390-400. IEEE, 2009.",
                "Olbrich, Steffen, Daniela S. Cruzes, Victor Basili, and Nico Zazworka",
                "In 2009 3rd international symposium on empirical software engineering and measurement, pp. 390-400. IEEE"
        );

        Arguments tc10 = Arguments.of(
                "Peters, Ralph, and Andy Zaidman. \"Evaluating the lifespan of code smells using software repository mining.\" In 2012 16th European Conference on Software Maintenance and Reengineering, pp. 411-416. IEEE, 2012.",
                "Peters, Ralph, and Andy Zaidman",
                "In 2012 16th European Conference on Software Maintenance and Reengineering, pp. 411-416. IEEE"
        );

        return Stream.of(tc1, tc2, tc3, tc4, tc5, tc6, tc7, tc8, tc9, tc10);
    }
}
