package nl.tudelft.serg.slrcrawler.library;

public class MLAParser {

    public static class MLA {
        private final String author;
        private final String conference;

        public MLA(String author, String conference) {
            this.author = author;
            this.conference = conference;
        }

        public String getAuthor() {
            return author;
        }

        public String getConference() {
            return conference;
        }
    }

    public static MLA parse(String mla) {
        /**
         * Finding the authors is easy. It's the entire string up to the first dot.
         */
        String author = mla.substring(0, mla.indexOf("."));

        /**
         * Conference can be a bit more tricky, because Google sometimes does not end the
         * string with the year of publication.
         *
         * So, we first check whether there's an year at the end.
         * If there's an year, this means the end of the string is
         * < title >" < venue >.< year >.
         * If year is not there, then it's
         * < title >" < venue >.
         *
         * (Note the "s and .s as we use them to locate things)
         * We just look for the right substring then
         */

        boolean mlaEndsWithTheYear = mla.substring(mla.length() - 5, mla.length() - 2).matches("\\d*");
        int lastDotOfTheVenue = mlaEndsWithTheYear ?
            mla.lastIndexOf(".", mla.length()-2) : /* up to the last dot of the venue block */
            mla.length()-1; /* up to the end of the string */

        int lastCharOfTheTitleFirstCharOfVenue = mla.lastIndexOf("\"", mla.length() - 2) + 1;
        String conference = mla.substring(
            lastCharOfTheTitleFirstCharOfVenue,
            lastDotOfTheVenue
        ).trim();

        return new MLA(author, conference);

    }
}
