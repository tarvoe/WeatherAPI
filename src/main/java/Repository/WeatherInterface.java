package Repository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public interface WeatherInterface {

    URL buildNewWeatherRequestURL(String countryCode, String city);

    Integer getWeatherApiResponseStatusFromWeb(String countryCode, String city) throws IOException;

    Integer getResponseCodeOfURL(String url) throws IOException;

    JSONArray getThreeDaysForecastFromWeb(String url) throws IOException, JSONException;


}
