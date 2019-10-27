package fr.bmarsaud.calendarshaper.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import fr.bmarsaud.calendarshaper.model.Calendar;

public class RequestHandler implements HttpHandler {
    private Calendar calendar;

    public RequestHandler(Calendar calendar) {
        this.calendar = calendar;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        HttpClient httpClient = HttpClient.newHttpClient();
        try {
            HttpRequest forwardedRequest = buildRequest(exchange);
            HttpResponse response = httpClient.send(forwardedRequest, HttpResponse.BodyHandlers.ofByteArray());
            byte[] data = (byte[]) response.body();

            for(String header : response.headers().map().keySet()) {
                exchange.getResponseHeaders().add(header, response.headers().firstValue(header).get());
            }

            exchange.sendResponseHeaders(response.statusCode(), data.length);

            OutputStream os = exchange.getResponseBody();
            os.write(data);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HttpRequest buildRequest(HttpExchange exchange) {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
        requestBuilder.uri(calendar.getUrl());

        byte[] data = null;
        if(exchange.getRequestHeaders().containsKey("Content-Length")) {
            try {
                data = exchange.getRequestBody().readAllBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        HttpRequest.BodyPublisher bodyPublisher = data != null ? HttpRequest.BodyPublishers.ofByteArray(data) : HttpRequest.BodyPublishers.noBody();
        requestBuilder.method(exchange.getRequestMethod(), bodyPublisher);

        for(String header : exchange.getRequestHeaders().keySet()) {
            try {
                requestBuilder.setHeader(header, exchange.getRequestHeaders().getFirst(header));
            } catch(IllegalArgumentException ex) {
                //TODO: log this
                ex.printStackTrace();
            }
        }

        return requestBuilder.build();
    }
}
