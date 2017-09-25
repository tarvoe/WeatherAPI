package Repository;


import okhttp3.HttpUrl;

import java.net.URL;

public class OpenWeatherAPI {


    public URL weatherRequestURL(String countryCode, String city) {

        URL url = new HttpUrl.Builder()
                .scheme("http")
                .host("api.openweathermap.org")
                .addPathSegments("/data/2.5/forecast")
                .addQueryParameter("q", countryCode + "," + city)
                .build().url();
        return url;
    }





}