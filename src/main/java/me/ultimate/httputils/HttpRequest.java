package me.ultimate.httputils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Iterator;

public class HttpRequest {
    private RequestOptions options;

    public HttpRequest(RequestOptions options) {
        this.options = options;
    }

    public HttpResponse send() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) options.getUrl().openConnection();

        connection.setRequestMethod(options.getType().toString());
        connection.setDoOutput(true);

        //Add headers
        final Iterator headers = options.getHeaders().entrySet().iterator();
        while (headers.hasNext()) {
            final String key = (String) headers.next();
            connection.addRequestProperty(key, options.getHeaders().get(key));
        }

        connection.disconnect(); //Not sending anymore requests on this url
        return new HttpResponse(this, connection);
    }

    public RequestOptions getOptions() {
        return options;
    }
}
