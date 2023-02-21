package pl.nieckarz.cryptocurrencyapi.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class HttpRequestService {
    public static JSONObject sendGetRequest(String url) throws Exception {
        // Create a URL object and open a connection
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // Set the request method to GET
        con.setRequestMethod("GET");

        // Read the response from the input stream
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // Parse the response as JSON
        JSONObject jsonResponse = new JSONObject(response.toString());

        // Return the response as a JSONObject
        return jsonResponse;
    }
}
