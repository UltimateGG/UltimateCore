package me.ultimate.httputils;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class HttpResponse {
    private HttpRequest request;
    private HttpURLConnection connection;
    private InputStream stream;
    private boolean error;
    private int responseCode;
    private int contentLength;

    public HttpResponse(HttpRequest request, HttpURLConnection connection) {
        this.request = request;
        this.connection = connection;
        try { this.stream = connection.getErrorStream() != null ? connection.getErrorStream() : connection.getInputStream(); } catch (IOException e) {}
        try { this.responseCode = connection.getResponseCode(); } catch (IOException e) {}
        this.error = connection.getErrorStream() != null;
        this.contentLength = connection.getContentLength();
    }

    public HttpRequest getRequest() {
        return request;
    }

    public HttpURLConnection getConnection() {
        return connection;
    }

    public InputStream getStream() {
        return stream;
    }

    public boolean hadError() {
        return error;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public int getContentLength() {
        return contentLength;
    }

    public String getResponseAsString() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        return content.toString();
    }

    public JsonElement json() throws IOException {
        return JsonParser.parseString(getResponseAsString());
    }
}
