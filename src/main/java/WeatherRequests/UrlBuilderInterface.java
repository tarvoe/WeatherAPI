package WeatherRequests;

import java.net.URL;

public interface UrlBuilderInterface {

    URL buildNewSingleWeatherRequestURL(String city, String APPID);

    URL buildNewForecastRequestURL( String city, String APPID);

}
