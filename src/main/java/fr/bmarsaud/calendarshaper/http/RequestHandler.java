package fr.bmarsaud.calendarshaper.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

import fr.bmarsaud.calendarshaper.model.Calendar;

public class RequestHandler implements HttpHandler {
    private Calendar calendar;

    public RequestHandler(Calendar calendar) {
        this.calendar = calendar;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        //TODO: forwards request
        final String message = "Calendar \"" + calendar.getName() + "\"";

        exchange.getResponseHeaders().add("Content-Type", "text/plain");
        exchange.sendResponseHeaders(200, message.getBytes().length);

        OutputStream os = exchange.getResponseBody();
        os.write(message.getBytes());
        os.close();
    }
}
