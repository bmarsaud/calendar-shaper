package fr.bmarsaud.calendarshaper.model;

public class Configuration {
    private static final String CONFIG_FOLDER = "config";
    private static final int DEFAULT_PORT = 80;

    public static final String CONFIG_FILE = CONFIG_FOLDER + "/config.json";
    public static final String CALENDAR_FILE = CONFIG_FOLDER + "/calendars.json";

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
}
