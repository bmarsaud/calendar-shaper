package fr.bmarsaud.calendarshaper.model.transformations;

public class SetValueTransformation extends Transformation {
    private String value;

    public SetValueTransformation(String value) {
        this.value = value;
    }

    @Override
    public String transform(String data) {
        return value;
    }
}