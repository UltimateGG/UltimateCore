package me.ultimate.httputils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class RequestOptions {
    private String host;
    private RequestType type;
    private HashMap<String, String> headers;

    public RequestOptions(String host) {
        this(host, RequestType.GET, new HashMap<>());
    }

    public RequestOptions(String host, RequestType type) {
        this(host, type, new HashMap<>());
    }

    public RequestOptions(String host, RequestType type, HashMap<String, String> headers) {
        this.host = host;
        this.type = type;
        this.headers = headers;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public URL getUrl() throws MalformedURLException {
        return new URL(host);
    }
}
