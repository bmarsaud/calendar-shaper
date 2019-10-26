package fr.bmarsaud.calendarshaper.http;

import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;

public class RequestHandler extends HttpHandler {
    @Override
    public void service(Request request, Response response) throws Exception {
        //TODO: forwards request
        final String message = "It works !";
        response.setContentType("text/plain");
        response.setContentLength(message.length());
        response.getWriter().write(message);
    }
}
