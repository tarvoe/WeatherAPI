package Repository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public interface WeatherInterface {

    URL buildNewSingleWeatherRequestURL(String countryCode, String city, String APPID);

    URL buildNewForecastRequestURL(String countryCode, String city, String APPID);

    Integer getWeatherApiResponseStatusFromWeb(String countryCode, String city, String APPID) throws IOException;

    Integer getResponseCodeOfURL(String url) throws IOException;

    ArrayList getHighestAndLowestTemperature(String url, int indexOfTheDay) throws IOException, JSONException;

    HashMap getThreeDaysForecastFromWeb(String url) throws IOException, JSONException;

    JSONArray makeStringToJSONArray(String dataFromURLBody) throws IOException, JSONException;


}
