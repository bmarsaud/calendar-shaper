package fr.bmarsaud.calendarshaper.model.transformations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TagValueTransformation extends Transformation {

    private String tag;
    private transient Pattern pattern;

    public TagValueTransformation(String tag) {
        this.tag = tag;
    }

    @Override
    public String transform(String data) {
        if(pattern == null) {
            pattern = Pattern.compile(tag + ":(.*(\\R .*)*)");
        }

        Matcher matcher = pattern.matcher(data);
        if(matcher.find()) {
            return matcher.group(1);
        }

        return "";
    }
}
