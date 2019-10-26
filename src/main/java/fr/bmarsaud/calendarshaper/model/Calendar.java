package fr.bmarsaud.calendarshaper.model;

import java.net.URL;
import java.util.ArrayList;

import fr.bmarsaud.calendarshaper.model.rules.CalendarRule;

public class Calendar {
    private String name;
    private URL url;
    private ArrayList<CalendarRule> rules;

    public Calendar(String name, URL url) {
        this.name = name;
        this.url = url;
        this.rules = new ArrayList<>();
    }

    public Calendar(String name, ArrayList<CalendarRule> rules) {
        this.name = name;
        this.rules = rules;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public ArrayList<CalendarRule> getRules() {
        return rules;
    }

    public void setRules(ArrayList<CalendarRule> rules) {
        this.rules = rules;
    }
}
