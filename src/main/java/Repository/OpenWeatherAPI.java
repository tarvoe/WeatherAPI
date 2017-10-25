package Repository;



import jdk.nashorn.internal.parser.JSONParser;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;

public class OpenWeatherAPI implements WeatherInterface {

    static OkHttpClient client = new OkHttpClient();

    @Override
    public URL buildNewWeatherRequestURL(String countryCode, String city/*, APIToken parameeter siia*/) {

            return new HttpUrl.Builder()
                    .scheme("http")
                .host("api.openweathermap.org")
                .addPathSegments("/data/2.5/weather")
                .addQueryParameter("q", countryCode + "," + city)
                .build().url();
    }

    @Override
    public Integer getWeatherApiResponseStatusFromWeb(String countryCode, String city /*, APIToken parameeter siia*/) throws IOException {
        return getResponseCodeOfURL(buildNewWeatherRequestURL(countryCode, city).toString());
    }

    @Override
    public Integer getResponseCodeOfURL(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.code();
    }

    @Override
    public JSONArray getThreeDaysForecastFromWeb(String url) throws IOException, JSONException {
        GetExample threeDayForecast = new GetExample();
        String response = threeDayForecast.responseBodyFromURL(url);
        JSONArray responseInJSONArray = makeStringToJSONArray(response);
        return responseInJSONArray;
    }

    public JSONArray getHighestAndLowestTemperature (String url) throws IOException, JSONException{

        GetExample highestLowest = new GetExample();
        String response = highestLowest.responseBodyFromURL(url);
        JSONArray responseInJSONArray = makeStringToJSONArray(response);
        return responseInJSONArray;
    }

    public JSONArray makeStringToJSONArray (String dataFromURLBody) throws JSONException {
        JSONObject jsnobject = new JSONObject(dataFromURLBody);
        JSONArray threeDayForecast = jsnobject.getJSONArray("weather");
        return threeDayForecast;
    }
}