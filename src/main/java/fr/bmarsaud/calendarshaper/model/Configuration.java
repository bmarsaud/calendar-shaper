package fr.bmarsaud.calendarshaper.model;

import java.io.File;
import java.net.URISyntaxException;

import fr.bmarsaud.calendarshaper.CalendarShaper;

public class Configuration {
    private static String CONFIG_FOLDER = "config";
    private static int DEFAULT_PORT = 80;

    public static String CONFIG_FILE = CONFIG_FOLDER + "/config.json";
    public static String CALENDAR_FILE = CONFIG_FOLDER + "/calendars.json";

    private int port;

    public Configuration() {
        port = DEFAULT_PORT;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public static void populateDefaultConfiguration() {
        String basePath = "";
        try {
            basePath = new File(CalendarShaper.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile().getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        CONFIG_FOLDER = basePath + "/" + CONFIG_FOLDER;
        CONFIG_FILE = basePath + "/" + CONFIG_FILE;
        CALENDAR_FILE = basePath + "/" + CALENDAR_FILE;
    }
}
