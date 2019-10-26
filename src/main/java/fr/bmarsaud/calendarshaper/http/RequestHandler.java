package fr.bmarsaud.calendarshaper.http;

import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;

import fr.bmarsaud.calendarshaper.model.Calendar;

public class RequestHandler extends HttpHandler {
    private Calendar calendar;

    public RequestHandler(Calendar calendar) {
        this.calendar = calendar;
    }

    @Override
    public void service(Request request, Response response) throws Exception {
        //TODO: forwards request
        final String message = "Calendar \"" + calendar.getName() + "\"";
        response.setContentType("text/plain");
        response.setContentLength(message.length());
        response.getWriter().write(message);
    }
}
