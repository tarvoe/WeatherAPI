package Repository;



import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.URL;

public class OpenWeatherAPI {

    OkHttpClient client = new OkHttpClient();

    public URL weatherRequestURL(String countryCode, String city) {

        return new HttpUrl.Builder()
                .scheme("http")
                .host("api.openweathermap.org")
                .addPathSegments("/data/2.5/forecast")
                .addQueryParameter("q", countryCode + "," + city)
                .build().url();
    }

    public  Integer getWeatherApiResponseStatus(String countryCode, String city) throws IOException {
        return getResponseCodeOfURL(weatherRequestURL(countryCode, city).toString());
    }

    private Integer getResponseCodeOfURL(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.code();
    }




}