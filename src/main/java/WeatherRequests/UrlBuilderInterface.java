package WeatherRequests;

import java.net.URL;

public interface UrlBuilderInterface {

    URL buildNewSingleWeatherRequestURL(String countryCode, String city, String APPID);

    URL buildNewForecastRequestURL(String countryCode, String city, String APPID);

}
