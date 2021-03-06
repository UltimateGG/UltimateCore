import me.ultimate.httputils.HttpRequest;
import me.ultimate.httputils.HttpResponse;
import me.ultimate.httputils.RequestOptions;
import me.ultimate.httputils.RequestType;

import java.io.IOException;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        System.out.println("Running from test source main..");

        //Do tests here..
        HttpRequest req = new HttpRequest(new RequestOptions("https://jsonplaceholder.typicode.com/todos/1", RequestType.GET, new HashMap<>()));
        try {
            System.out.println("Sending http request..");
            final HttpResponse res = req.send();

            System.out.println("HTTP/1.1 " + res.getResponseCode() + " " + res.getConnection().getResponseMessage());

            //Input stream to string
            System.out.println("RES:");
            System.out.println(res.json().getAsJsonObject().get("title").getAsString());
        } catch (IOException e) {
            System.out.println("Failed to send http request:");
            e.printStackTrace();
        }
    }
}
