package fr.bmarsaud.calendarshaper.model.rules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindAndReplaceRule extends CalendarRule {
    private String patternToFind;
    private String patternToReplace;
    private int findGroupId;
    private int replaceGroupId;

    @Override
    public String apply(String data) {
        String stringToFind = null;
        String stringToReplace = null;

        Pattern compiledPatternToFind = Pattern.compile(patternToFind);
        Pattern compiledPatternToReplace = Pattern.compile(patternToReplace);

        Matcher findMatcher = compiledPatternToFind.matcher(data);
        Matcher replaceMatcher = compiledPatternToReplace.matcher(data);

        if(findMatcher.find()) stringToFind = findMatcher.group(findGroupId);
        if(replaceMatcher.find()) stringToReplace = replaceMatcher.group(replaceGroupId);

        if(stringToFind != null && stringToReplace != null) {
            data = data.replaceAll(stringToFind, stringToReplace);
            logger.debug("String \"" + stringToFind + "\" replaced by \"" + stringToReplace + "\"");
        }

        return data;
    }
}
