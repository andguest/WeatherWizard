package data_access;

import java.io.*;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;

import entity.Weather;
import use_case.note.HistoricalWeatherDataAccessInterface;

/**
 * This class provides the service of saving and getting historical weather data.
 */
public class HistoricalWeatherDataAccessObject implements HistoricalWeatherDataAccessInterface {
    @Override
    public void saveWeather(Weather weather, String timeStamp) throws IOException {

        // Save the weather data to the database
        // Convert the Weather object to JSON
//        final String nextLine = "\",\n";
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder
                .append("{\n")
                .append("  \"timeStamp\": \"").append(timeStamp).append("\",\n")
                .append("  \"city\": \"").append(weather.getCityName()).append("\",\n")
                .append("  \"longitude\": ").append(weather.getLon()).append(",\n")
                .append("  \"latitude\": ").append(weather.getLat()).append(",\n")
                .append("  \"temperature\": ").append(weather.getTemperature()).append(",\n")
                .append("  \"looks\": \"").append(weather.getWeather()).append("\",\n")
                .append("  \"alertDescription\": \"").append(weather.getDescription()).append("\",\n")
                .append("  \"humidity\": ").append(weather.getHumidity()).append(",\n")
                .append("  \"windSpeed\": ").append(weather.getWindSpeed()).append("\n")
                .append("}");

        final String json = jsonBuilder.toString();

        // Convert StringBuilder to String
        final String weatherJson = jsonBuilder.toString();

        // Write JSON to a file

        String relativePath = "src/main/resources/weather.json";
        File file = new File(relativePath);

        try {
            StringBuilder finalJson = new StringBuilder();
            if (!file.exists() || file.length() == 0) {
                // If file does not exist or is empty, create a new JSON array
                finalJson.append("[\n").append(weatherJson).append("\n]");
            } else {
                // Read existing data from the file
                String existingJson = Files.readString(file.toPath(), StandardCharsets.UTF_8);

                // Remove the closing bracket of the JSON array
                existingJson = existingJson.trim();
                if (existingJson.endsWith("]")) {
                    existingJson = existingJson.substring(0, existingJson.length() - 1);
                }

                // Append the new object with a comma if there's existing data
                finalJson.append(existingJson).append(",\n").append(weatherJson).append("\n]");
            }

            // Write the updated JSON data to the file
            try (FileWriter fileWriter = new FileWriter(file, false)) {
                fileWriter.write(finalJson.toString());
                System.out.println("Successfully appended weather data to weather.json");
            }
        } catch (IOException excep) {
            excep.printStackTrace();
            System.out.println("Failed to append weather data to weather.json");
        }
    }

    @Override
    public Weather getWeather(String city, String timestamp) throws IOException {
        // Get the weather data from the database
        // Read the JSON from the file
        final String filePath = "weather.json";
        try {
            URL resource = getClass().getClassLoader().getResource("weather.json");
            if (resource == null) {
                throw new FileNotFoundException("File not found: src/main/resources/weather.json");
            }

            // Read the content of the JSON file as a String
            final String jsonString = Files.readString(Paths.get(resource.toURI()), StandardCharsets.UTF_8);
            final JSONArray weatherArray = new JSONArray(jsonString);

//            final JSONArray weatherArray = weatherObject1.getJSONArray("weatherData");

            for (int i = 0; i < weatherArray.length(); i++) {
                // Getting the JSONObject at the index i
                final JSONObject weatherObject = weatherArray.getJSONObject(i);
                // Getting the city from the JSONObject
                final String cityNameCall = weatherObject.getString("city");
                // Getting the timestamp from the JSONObject
                final String timeStamp = weatherObject.getString("timeStamp");
                // Checking if the city and timestamp match the input
                if (cityNameCall.equals(city) && timeStamp.equals(timestamp)) {
                    // Create weather object
                    //TODO: Changed weather declaration, uncertain if it there is key "description" and
                    // "alertDescription" for historical weather. If no we need to change Weather class.
                    final Weather weather = new Weather(
                            cityNameCall,
                            weatherObject.getInt("temperature"),
                            weatherObject.getString("looks"),
                            weatherObject.getString("alertDescription"),
                            weatherObject.getInt("windSpeed"),
                            weatherObject.getInt("longitude"),
                            weatherObject.getInt("humidity"),
                            weatherObject.getInt("longitude"),
                            weatherObject.getInt("latitude"),
                            weatherObject.getString("alertDescription")
                    );
                    return weather;
                }

            }
        }
        catch (IOException | URISyntaxException exe) {
            exe.printStackTrace();
        }
        throw new IOException("Weather data not found");
    }

}
