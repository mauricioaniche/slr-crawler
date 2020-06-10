package nl.tudelft.serg.slrcrawler.library.springer;

public class SpringerConfig {

    private final String discipline;
    private final String subDiscipline;
    private final String contentType;

    public SpringerConfig(String discipline, String subDiscipline, String contentType) {
        this.discipline = discipline;
        this.subDiscipline = subDiscipline;
        this.contentType = contentType;
    }

    public String getDiscipline() {
        return discipline;
    }

    public String getSubDiscipline() {
        return subDiscipline;
    }

    public String getContentType() {
        return contentType;
    }

    public static SpringerConfig none() {
        return new SpringerConfig("", "", "");
    }
}
