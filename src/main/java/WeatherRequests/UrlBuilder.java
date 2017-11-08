package WeatherRequests;

import okhttp3.HttpUrl;

import java.net.URL;

public class UrlBuilder implements UrlBuilderInterface {

    public URL buildNewForecastRequestURL(String countryCode, String city, String APPID) {

        return new HttpUrl.Builder()
                .scheme("http")
                .host("api.openweathermap.org")
                .addPathSegments("/data/2.5/forecast")
                .addQueryParameter("q", countryCode + "," + city)
                .addQueryParameter("APPID", APPID)
                .addQueryParameter("units", "metric")
                .build().url();
    }

    public URL buildNewSingleWeatherRequestURL(String countryCode, String city, String APPID) {
        return new HttpUrl.Builder()
                .scheme("http")
                .host("api.openweathermap.org")
                .addPathSegments("/data/2.5/weather")
                .addQueryParameter("q", countryCode + "," + city)
                .addQueryParameter("APPID", APPID)
                .addQueryParameter("units", "metric")
                .build().url();
    }

}
