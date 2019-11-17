package fr.bmarsaud.calendarshaper.model.transformations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindAnReplaceTransformation extends Transformation {
    private String findRegex;
    private int findGroupId;
    private String replaceRegex;
    private int replaceGroupId;

    private transient Pattern findPattern;
    private transient Pattern replacePattern;

    public FindAnReplaceTransformation(String findRegex, int findGroupId, String replaceRegex, int replaceGroupId) {
        this.findRegex = findRegex;
        this.findGroupId = findGroupId;
        this.replaceRegex = replaceRegex;
        this.replaceGroupId = replaceGroupId;
    }

    @Override
    public String transform(String data) {
        if(findPattern == null && replacePattern == null) {
            findPattern = Pattern.compile(findRegex);
            replacePattern = Pattern.compile(replaceRegex);
        }

        String stringToFind = null, stringToReplace = null;

        Matcher findMatcher = findPattern.matcher(data);
        Matcher replaceMatcher = replacePattern.matcher(data);

        if(findMatcher.find()) stringToFind = findMatcher.group(findGroupId);
        if(replaceMatcher.find()) stringToReplace = replaceMatcher.group(replaceGroupId);

        if(stringToFind != null && stringToReplace != null) {
            data = data.replaceAll(Pattern.quote(stringToFind), Pattern.quote(stringToReplace));
        }

        return data;
    }
}
