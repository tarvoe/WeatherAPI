package UrlRequests;

import okhttp3.HttpUrl;

import java.net.URL;

public class UrlBuilder {

    public URL buildNewForecastRequestURL(String city, String APPID) {

        return new HttpUrl.Builder()
                .scheme("http")
                .host("api.openweathermap.org")
                .addPathSegments("/data/2.5/forecast")
                .addQueryParameter("q", city)
                .addQueryParameter("APPID", APPID)
                .addQueryParameter("units", "metric")
                .build().url();
    }

    public URL buildNewSingleWeatherRequestURL( String city, String APPID) {
        return new HttpUrl.Builder()
                .scheme("http")
                .host("api.openweathermap.org")
                .addPathSegments("/data/2.5/weather")
                .addQueryParameter("q",  city)
                .addQueryParameter("APPID", APPID)
                .addQueryParameter("units", "metric")
                .build().url();
    }

}
