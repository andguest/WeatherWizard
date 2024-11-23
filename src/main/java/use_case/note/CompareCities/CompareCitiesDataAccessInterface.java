package use_case.note.CompareCities;

import entity.Weather;

import java.io.IOException;
import java.util.Map;

/**
 * Interface for the Comparing Cities Use case.
 */
public interface CompareCitiesDataAccessInterface {
    /**
     * Check if City exists.
     * @param cityname the weather is displayed for
     * @return if city exists
     */
    boolean isCityexist(String cityname);

    /**
     * Creates the Weather.
     * @param cityname the weather is displayed for
     * @return the weather information
     * @throws IOException if the city does not exist or oi.
     */
    Weather getWeather(String cityname) throws IOException;

    /**
     * Saves the Weather.
     * @param weather save weather infor of a city to a map. since there is method getCityname in Weather Object.
     * this method can work without have cityname as its input.
     */
    void saveWeatherinfor(Weather weather);

    /**
     * Returns the City.
     * @return the city
     * */
    Map getcitytoweather();

    /**
     * Sets city.
     * @param cityname check if the cityname is valid or not
     */
    boolean isCityExist(String cityname);

    /**
     * This method "clean" the elements inside this.citytoweather we don't want to accumulate pairs.
     */
    void clearcitytoweather();
}
