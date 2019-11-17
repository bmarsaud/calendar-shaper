package fr.bmarsaud.calendarshaper.model.transformations;

public class RemoveTransformation extends Transformation {
    private String regex;

    public RemoveTransformation(String regex) {
        this.regex = regex;
    }

    @Override
    public String transform(String data) {
        return data.replace(data, "");
    }
}
