package fr.bmarsaud.calendarshaper.model.rules;

public class ReplaceRule extends CalendarRule {
    private String pattern;
    private String replacement;

    public ReplaceRule(String pattern, String replacement) {
        this.pattern = pattern;
        this.replacement = replacement;
    }

    @Override
    public void apply(String data) {

    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getReplacement() {
        return replacement;
    }

    public void setReplacement(String replacement) {
        this.replacement = replacement;
    }
}