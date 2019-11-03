package fr.bmarsaud.calendarshaper.model.rules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindAndReplaceRule extends CalendarRule {
    private String patternToFind;
    private String patternToReplace;
    private int findGroupId;
    private int replaceGroupId;
    private RuleScope ruleScope;

    @Override
    public String apply(String data) {
        Pattern compiledPatternToFind = Pattern.compile(patternToFind);
        Pattern compiledPatternToReplace = Pattern.compile(patternToReplace);

        Matcher findMatcher;
        Matcher replaceMatcher;

        String stringToFind = null;
        String stringToReplace = null;

        if(ruleScope == RuleScope.CALENDAR) {
            findMatcher = compiledPatternToFind.matcher(data);
            replaceMatcher = compiledPatternToReplace.matcher(data);

            if(findMatcher.find()) stringToFind = findMatcher.group(findGroupId);
            if(replaceMatcher.find()) stringToReplace = replaceMatcher.group(replaceGroupId);

            if(stringToFind != null && stringToReplace != null) {
                data = data.replaceAll(stringToFind, stringToReplace);
                logger.debug("String \"" + stringToFind + "\" replaced by \"" + stringToReplace + "\"");
            }
        } else if(ruleScope == RuleScope.EVENT) {
            final String splitToken = "BEGIN:VEVENT\r\n";
            String[] splitData = data.split(splitToken);
            String parsedData = "";

            for(int i = 0; i < splitData.length; i++) {
                findMatcher = compiledPatternToFind.matcher(splitData[i]);
                replaceMatcher = compiledPatternToReplace.matcher(splitData[i]);

                if(findMatcher.find()) stringToFind = findMatcher.group(findGroupId);
                if(replaceMatcher.find()) stringToReplace = replaceMatcher.group(replaceGroupId);

                if(stringToFind != null && stringToReplace != null) {
                    parsedData += splitToken;
                    parsedData += splitData[i].replaceAll(stringToFind, stringToReplace);
                    logger.debug("String \"" + stringToFind + "\" replaced by \"" + stringToReplace + "\"");
                }
            }

            if(parsedData.length() > 0) {
                data = parsedData.substring(splitToken.length());
            }
        } else {
            logger.error("RuleScope " + ruleScope + " is not supported !");
        }


        return data;
    }
}
