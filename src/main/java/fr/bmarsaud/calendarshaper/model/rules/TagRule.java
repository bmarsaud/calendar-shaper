package fr.bmarsaud.calendarshaper.model.rules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.bmarsaud.calendarshaper.model.transformations.TagValueTransformation;
import fr.bmarsaud.calendarshaper.model.transformations.Transformation;

public class TagRule extends ShaperRule {
    private String tag;
    private transient Pattern pattern;

    @Override
    public String apply(String data) {
        if(pattern == null) {
            pattern = Pattern.compile(tag + ":(.*(\\R .*)*)");
        }

        String splitData[] = data.split(EventRule.SPLIT_TOKEN);
        String parsedData = splitData[0];

        for(int i = 1; i < splitData.length; i++ ) {
            String eventData = splitData[i];

            Matcher matcher = pattern.matcher(eventData);
            if(matcher.find()) {
                String tagData = matcher.group(1);

                for(Transformation transformation : transformations) {
                    if(transformation instanceof TagValueTransformation) {
                        tagData = transformation.transform(eventData);
                    } else {
                        tagData = transformation.transform(tagData);
                    }
                }

                eventData = matcher.replaceFirst(tag + ":" + tagData);
            }

            parsedData += EventRule.SPLIT_TOKEN;
            parsedData += eventData;
        }

        return parsedData;
    }
}
