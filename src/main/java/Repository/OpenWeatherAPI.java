package Repository;



import com.sun.deploy.net.HttpRequest;
import com.sun.deploy.net.HttpResponse;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;

import java.io.IOException;
import java.net.URL;

public class OpenWeatherAPI {

    static OkHttpClient client = new OkHttpClient();

    public static URL weatherRequestURL(String countryCode, String city/*, APIToken parameeter siia*/) {

        return new HttpUrl.Builder()
                .scheme("http")
                .host("api.openweathermap.org")
                .addPathSegments("/data/2.5/weather")
                .addQueryParameter("q", countryCode + "," + city)
                .build().url();
    }

    public static Integer getWeatherApiResponseStatus(String countryCode, String city /*, APIToken parameeter siia*/) throws IOException {
        return getResponseCodeOfURL(weatherRequestURL(countryCode, city).toString());
    }

    private static Integer getResponseCodeOfURL(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.code();
    }

    public static JSONArray getThreeDaysForecast (String url) throws IOException {
        GetExample threeDayForecast = new GetExample();
        String response = threeDayForecast.run("http://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b1b15e88fa797225412429c1c50c122a1");
        return null;
    }


}