package Repository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public interface WeatherInterface {


    Integer getWeatherApiResponseStatusFromWeb( String city, String APPID) throws IOException;

    Integer getResponseCodeOfURL(String url) throws IOException;

    //ArrayList getHighestAndLowestTemperatureForTheNextThreeDays(String url, int indexOfTheDay) throws IOException, JSONException;

    HashMap<String, Object> createHashMapOfThreeDayForecast(JSONArray jsonAndmed)throws JSONException;

    //HashMap getThreeDaysForecastFromWeb(String url) throws IOException, JSONException;

    JSONArray getWeatherPredictionsForEachTimeInJSONArrayFormFromUrlResponse(String dataFromURLBody) throws IOException, JSONException;

    String getResponseBodyFromURL(String url) throws IOException;


}
