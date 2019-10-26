package fr.bmarsaud.calendarshaper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.glassfish.grizzly.http.server.HttpServer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import fr.bmarsaud.calendarshaper.http.RequestHandler;
import fr.bmarsaud.calendarshaper.model.Calendar;
import fr.bmarsaud.calendarshaper.model.rules.CalendarRule;
import fr.bmarsaud.calendarshaper.model.rules.RuleSerializer;

public class CalendarShaper {
    private ArrayList<Calendar> calendars;
    private Gson gson;

    public CalendarShaper() {
        calendars = new ArrayList<>();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(CalendarRule.class, new RuleSerializer());
        gson = gsonBuilder.setPrettyPrinting().create();
    }

    public void loadCalendars() {
        File file = new File("calendars.json");

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
        File file = new File("calendars.json");
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(gson.toJson(calendars));
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(int port) {
        HttpServer server = HttpServer.createSimpleServer("", port);

        for(Calendar calendar : calendars) {
            server.getServerConfiguration().addHttpHandler(new RequestHandler(calendar), "/" + calendar.getName());
        }

        try {
            server.start();
            System.out.println("calendar-shaper successfully started ! Press key to stop...");
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        saveCalendars();
    }

    public static void main(String[] args) {
        int port = 8080;
        if(args.length == 1) {
            port = Integer.valueOf(args[0]);
        }

        CalendarShaper calendarShaper = new CalendarShaper();
        calendarShaper.loadCalendars();
        calendarShaper.start(port);
    }
}
