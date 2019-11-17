package fr.bmarsaud.calendarshaper.model.transformations;

public class ReplaceTransformation extends Transformation {
    private String regex;
    private String replacement;

    public ReplaceTransformation(String regex, String replacement) {
        this.regex = regex;
        this.replacement = replacement;
    }

    @Override
    public String transform(String data) {
        return data.replaceAll(regex, replacement);
    }
}
