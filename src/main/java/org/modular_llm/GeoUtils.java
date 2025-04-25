package org.modular_llm;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

public class GeoUtils {

    /**
     * Gets the latitude and longitude for a location using Nominatim API.
     *
     * @param location A human-readable location (e.g., "Boulder, Colorado")
     * @return double[] array of size 2: [latitude, longitude]; null if not found or error
     */
    public static double[] getCoordinates(String location) {
        try {
            String encoded = URLEncoder.encode(location, "UTF-8");
            String url = "https://nominatim.openstreetmap.org/search?q=" + encoded + "&format=json&limit=1";

            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0"); // Required by Nominatim

            if (conn.getResponseCode() == 200) {
                Scanner scanner = new Scanner(conn.getInputStream());
                StringBuilder sb = new StringBuilder();
                while (scanner.hasNext()) {
                    sb.append(scanner.nextLine());
                }
                scanner.close();

                JSONArray results = new JSONArray(sb.toString());
                if (results.length() > 0) {
                    JSONObject obj = results.getJSONObject(0);
                    double lat = Double.parseDouble(obj.getString("lat"));
                    double lon = Double.parseDouble(obj.getString("lon"));
                    return new double[]{lat, lon};
                }
            }
        } catch (IOException e) {
            System.err.println("IOException while fetching coordinates: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error while fetching coordinates: " + e.getMessage());
        }

        return null;
    }
}

