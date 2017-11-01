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



    static String countryCode3 = "EE";
    static String city3 = "Tallinn";
    static String APPID3 = "0786e4e1ae01d4e119f0260e53a683d0";

    static OkHttpClient client = new OkHttpClient();

    @Override
    public URL buildNewSingleWeatherRequestURL(String countryCode, String city, String APPID) {

            return new HttpUrl.Builder()
                    .scheme("http")
                .host("api.openweathermap.org")
                .addPathSegments("/data/2.5/weather")
                .addQueryParameter("q", countryCode + "," + city)
                .addQueryParameter("APPID", APPID)
                .build().url();
    }

    @Override
    public URL buildNewForecastRequestURL(String countryCode, String city, String APPID) {

        return new HttpUrl.Builder()
                .scheme("http")
                .host("api.openweathermap.org")
                .addPathSegments("/data/2.5/forecast")
                .addQueryParameter("q", countryCode + "," + city)
                .addQueryParameter("APPID", APPID)
                .build().url();
    }

    @Override
    public Integer getWeatherApiResponseStatusFromWeb(String countryCode, String city, String APPID) throws IOException {
        return getResponseCodeOfURL(buildNewSingleWeatherRequestURL(countryCode, city, APPID).toString());
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
        OpenWeatherAPI threeDayForecast = new OpenWeatherAPI();
        String response = threeDayForecast.getResponseBodyFromURL(url);
        JSONArray responseInJSONArray = makeStringToJSONArray(response);
        return responseInJSONArray;
    }

    @Override
    public JSONArray getHighestAndLowestTemperature (String url) throws IOException, JSONException{

        OpenWeatherAPI highestLowest = new OpenWeatherAPI();
        String response = highestLowest.getResponseBodyFromURL(url);
        JSONArray responseInJSONArray = makeStringToJSONArray(response);
        return responseInJSONArray;
    }

    public JSONArray makeStringToJSONArray (String dataFromURLBodyInStringForm) throws JSONException {
        JSONObject jsonObject = new JSONObject(dataFromURLBodyInStringForm);
        JSONArray dataFromURLBodyInJSONArrayForm = jsonObject.getJSONArray("weather");
        return dataFromURLBodyInJSONArrayForm;
    }

    String getResponseBodyFromURL(String url) throws IOException {
        Request newURLrequest = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(newURLrequest).execute()) {
            return response.body().string();
        }
    }

    public static void main(String[] args) throws IOException {
        OpenWeatherAPI test = new OpenWeatherAPI();
        String andmed = test.buildNewForecastRequestURL(countryCode3,city3,APPID3).toString();
        String response = test.getResponseBodyFromURL(andmed);
        System.out.println(response);
    }
}





/*
class SingleDayForecastBuilder extends OpenWeatherAPI {
    public URL buildNewSingleWeatherRequestURL(String countryCode, String city, String APPID) {

        return new HttpUrl.Builder()
                .scheme("http")
                .host("api.openweathermap.org")
                .addPathSegments("/data/2.5/weather")
                .addQueryParameter("q", countryCode + "," + city)
                .addQueryParameter("APPID", APPID)
                .build().url();
    }
}

class ThreeDayForecastBuilder extends OpenWeatherAPI {
    public URL buildNewSingleWeatherRequestURL(String countryCode, String city, String APPID) {

        return new HttpUrl.Builder()
                .scheme("http")
                .host("api.openweathermap.org")
                .addPathSegments("/data/2.5/forecast")
                .addQueryParameter("q", countryCode + "," + city)
                .addQueryParameter("APPID", APPID)
                .build().url();
    }
}*/