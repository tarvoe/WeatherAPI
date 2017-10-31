package Repository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public interface WeatherInterface {

    URL buildNewSingleWeatherRequestURL(String countryCode, String city, String APPID);

    URL buildNewForecastRequestURL(String countryCode, String city, String APPID);

    Integer getWeatherApiResponseStatusFromWeb(String countryCode, String city, String APPID) throws IOException;

    Integer getResponseCodeOfURL(String url) throws IOException;

    JSONArray getThreeDaysForecastFromWeb(String url) throws IOException, JSONException;


}
