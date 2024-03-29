package fr.bmarsaud.calendarshaper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import fr.bmarsaud.calendarshaper.model.rules.ShaperRule;
import fr.bmarsaud.calendarshaper.model.rules.RuleSerializer;

public class CalendarShaper {
    private Logger logger = LoggerFactory.getLogger(CalendarShaper.class);

    private ArrayList<Calendar> calendars;
    private Configuration config;
    private Gson gson;
    private HttpServer server;

    public CalendarShaper() {
        calendars = new ArrayList<>();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ShaperRule.class, new RuleSerializer());
        gson = gsonBuilder.setPrettyPrinting().create();
    }

    public void loadConfiguration() {
        Configuration.populateDefaultConfiguration();
        File file = new File(Configuration.CONFIG_FILE);

        if(file.exists()) {
            try {
                FileReader fileReader = new FileReader(file);
                config = gson.fromJson(fileReader, Configuration.class);
                logger.info("Configuration loaded !");
            } catch (FileNotFoundException e) {
                logger.error("Error while loading the configuration");
                e.printStackTrace();
            }
        } else {
            logger.info("No config found, fallback to default configuration");
            config = new Configuration();
        }
    }

    public void loadCalendars() {
        File file = new File(Configuration.CALENDAR_FILE);

        if(file.exists()) {
            try {
                FileReader fileReader = new FileReader(file);
                calendars = new ArrayList(Arrays.asList(gson.fromJson(fileReader, Calendar[].class)));
                logger.info(calendars.size() + " calendar" + (calendars.size() > 1 ? "s" : "") +" loaded !");
            } catch (FileNotFoundException e) {
                logger.error("Error while loading the calendars");
                e.printStackTrace();
            }
        } else {
            logger.info("Calendars config not found !");
        }
    }

    public void saveCalendars() {
        File file = new File(Configuration.CALENDAR_FILE);
        try {
            if(file.exists()) {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(gson.toJson(calendars));
                fileWriter.flush();
                fileWriter.close();
                logger.info("Calendars saved");
            }
        } catch (IOException e) {
            logger.error("Error while saving the calendars");
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            server = HttpServer.create(new InetSocketAddress(config.getPort()), 0);
            for(Calendar calendar : calendars) {
                HttpContext context = server.createContext("/" + calendar.getName());
                context.setHandler(new RequestHandler(calendar));
            }

            server.start();

            logger.info("calendar-shaper successfully started on port " + config.getPort() + " ! Press key to stop...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        logger.info("Stopping server...");
        saveCalendars();
        server.stop(1);
    }

    public static void main(String[] args) {
        CalendarShaper calendarShaper = new CalendarShaper();
        Runtime.getRuntime().addShutdownHook(new Thread(calendarShaper::stop));

        calendarShaper.loadConfiguration();
        calendarShaper.loadCalendars();
        calendarShaper.start();
    }
}
