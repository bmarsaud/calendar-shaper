package fr.bmarsaud.calendarshaper;

import org.glassfish.grizzly.http.server.HttpServer;

import java.io.IOException;

import fr.bmarsaud.calendarshaper.http.RequestHandler;

public class CalendarShaper {
    public static void main(String[] args) {
        int port = 8080;
        if(args.length == 1) {
            port = Integer.valueOf(args[0]);
        }

        HttpServer server = HttpServer.createSimpleServer("", port);
        server.getServerConfiguration().addHttpHandler(new RequestHandler(), "/test");

        try {
            server.start();
            System.out.println("calendar-shaper successfully started ! Press key to stop...");
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
