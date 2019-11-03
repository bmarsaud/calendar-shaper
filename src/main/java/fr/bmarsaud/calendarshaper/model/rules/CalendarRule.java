package fr.bmarsaud.calendarshaper.model.rules;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class CalendarRule {
    protected Logger logger = LoggerFactory.getLogger(CalendarRule.class);

    public abstract String apply(String data);
}
