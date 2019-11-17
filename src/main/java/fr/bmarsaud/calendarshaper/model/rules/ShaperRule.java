package fr.bmarsaud.calendarshaper.model.rules;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

import fr.bmarsaud.calendarshaper.model.transformations.Transformation;


public abstract class ShaperRule {
    protected transient Logger logger = LoggerFactory.getLogger(ShaperRule.class);

    protected ArrayList<Transformation> transformations;

    public abstract String apply(String data);
}
