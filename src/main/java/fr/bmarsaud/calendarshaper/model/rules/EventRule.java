package fr.bmarsaud.calendarshaper.model.rules;

import fr.bmarsaud.calendarshaper.model.transformations.Transformation;

public class EventRule extends ShaperRule {
    final static String splitToken = "BEGIN:VEVENT\r\n";

    @Override
    public String apply(String data) {
        String splitData[] = data.split(splitToken);
        String parsedData = splitData[0];

        for(int i = 1; i < splitData.length - 1; i++ ) {
            String eventData = splitData[i];

            for(Transformation transformation : transformations) {
                 eventData = transformation.transform(eventData);
            }

            parsedData += eventData;
        }

        parsedData += splitData[splitData.length - 1];

        return parsedData;
    }
}
