package fr.bmarsaud.calendarshaper;

import org.glassfish.grizzly.http.server.HttpServer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import fr.bmarsaud.calendarshaper.http.RequestHandler;
import fr.bmarsaud.calendarshaper.model.Calendar;

public class CalendarShaper {
    private ArrayList<Calendar> calendars;

    public CalendarShaper() {
        calendars = new ArrayList<>();
    }

    public void loadCalendars() {
        URL url = null;
        try {
            url = new URL("http://example.com/calendar.ics");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        calendars.add(new Calendar("test", url));
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
