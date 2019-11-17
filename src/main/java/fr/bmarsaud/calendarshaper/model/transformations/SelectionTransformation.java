package fr.bmarsaud.calendarshaper.model.transformations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SelectionTransformation extends Transformation {
    private String regex;
    private int groupId;
    private transient Pattern pattern;

    public SelectionTransformation(String regex, int groupId) {
        this.regex = regex;
        this.groupId = groupId;
    }

    @Override
    public String transform(String data) {
        if(pattern == null) {
            pattern = Pattern.compile(regex);
        }

        Matcher matcher = pattern.matcher(data);
        if(matcher.find()) {
            return matcher.group(groupId);
        }

        return "";
    }
}
