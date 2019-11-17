package fr.bmarsaud.calendarshaper.model;

import java.net.URI;
import java.util.ArrayList;

import fr.bmarsaud.calendarshaper.model.rules.ShaperRule;

public class Calendar {
    private String name;
    private URI url;
    private ArrayList<ShaperRule> rules;

    public Calendar(String name, URI url) {
        this.name = name;
        this.url = url;
        this.rules = new ArrayList<>();
    }

    public Calendar(String name, ArrayList<ShaperRule> rules) {
        this.name = name;
        this.rules = rules;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public URI getUrl() {
        return url;
    }

    public void setUrl(URI url) {
        this.url = url;
    }

    public ArrayList<ShaperRule> getRules() {
        return rules;
    }

    public void setRules(ArrayList<ShaperRule> rules) {
        this.rules = rules;
    }
}
