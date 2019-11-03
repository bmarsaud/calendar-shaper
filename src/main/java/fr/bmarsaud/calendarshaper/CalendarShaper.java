package fr.bmarsaud.calendarshaper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;

import fr.bmarsaud.calendarshaper.http.RequestHandler;
import fr.bmarsaud.calendarshaper.model.Calendar;
import fr.bmarsaud.calendarshaper.model.Configuration;
import fr.bmarsaud.calendarshaper.model.rules.CalendarRule;
import fr.bmarsaud.calendarshaper.model.rules.RuleSerializer;

public class CalendarShaper {
    private ArrayList<Calendar> calendars;
    private Configuration config;
    private Gson gson;

    public CalendarShaper() {
        calendars = new ArrayList<>();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(CalendarRule.class, new RuleSerializer());
        gson = gsonBuilder.setPrettyPrinting().create();
    }

    public void loadConfiguration() {
        File file = new File(Configuration.CONFIG_FILE);

        if(file.exists()) {
            try {
                FileReader fileReader = new FileReader(file);
                config = gson.fromJson(fileReader, Configuration.class);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            config = new Configuration();
        }
    }

    public void loadCalendars() {
        File file = new File(Configuration.CALENDAR_FILE);

        if(file.exists()) {
            try {
                FileReader fileReader = new FileReader(file);
                calendars = new ArrayList(Arrays.asList(gson.fromJson(fileReader, Calendar[].class)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveCalendars() {
        File file = new File(Configuration.CALENDAR_FILE);
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(gson.toJson(calendars));
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(config.getPort()), 0);
            for(Calendar calendar : calendars) {
                HttpContext context = server.createContext("/" + calendar.getName());
                context.setHandler(new RequestHandler(calendar));
            }

            server.start();

            System.out.println("calendar-shaper successfully started on port " + config.getPort() + " ! Press key to stop...");
            System.in.read();

            server.stop(config.getPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CalendarShaper calendarShaper = new CalendarShaper();
        calendarShaper.loadConfiguration();
        calendarShaper.loadCalendars();
        calendarShaper.start();
        calendarShaper.saveCalendars();
    }
}
