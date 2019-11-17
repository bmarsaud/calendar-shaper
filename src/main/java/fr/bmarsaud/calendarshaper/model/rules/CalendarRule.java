package fr.bmarsaud.calendarshaper.model.rules;

import fr.bmarsaud.calendarshaper.model.transformations.Transformation;

public class CalendarRule extends ShaperRule {

    @Override
    public String apply(String data) {
        for(Transformation transformation : transformations) {
            data = transformation.transform(data);
        }

        return data;
    }
}
