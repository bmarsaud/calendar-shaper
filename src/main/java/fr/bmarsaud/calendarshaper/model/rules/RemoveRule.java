package fr.bmarsaud.calendarshaper.model.rules;

public class RemoveRule extends CalendarRule {
    private String pattern;

    public RemoveRule(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String apply(String data) {
        return data;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}
