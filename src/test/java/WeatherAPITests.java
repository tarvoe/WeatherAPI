
import WeatherRequests.UrlBuilder;
import WeatherRequests.UrlBuilderInterface;
import Repository.WeatherInterface;
import org.junit.Test;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import Repository.OpenWeatherAPI;
public class WeatherAPITests {

    private String countryCode = "EE";
    private String city = "Tallinn";
    private String APPID = "0786e4e1ae01d4e119f0260e53a683d0";

    WeatherInterface weatherFromTheWeb = new OpenWeatherAPI();
    UrlBuilderInterface urlBuilder = new UrlBuilder();

    @Test
    public void didItReturnAUrl() throws Exception {

        //Tekitab näidissisendi forecastUrlile
        final URL singleWeatherRequestURL = urlBuilder.buildNewSingleWeatherRequestURL(countryCode, city, APPID );
        final URL forecastRequestURL = urlBuilder.buildNewForecastRequestURL(countryCode, city, APPID );


        //Testib kas forecastUrl on URL klassist
        assertThat(singleWeatherRequestURL, instanceOf(URL.class));
        assertThat(forecastRequestURL, instanceOf(URL.class));
    }


    @Test
    public void didTheRequestConnectToAPI() throws Exception {

        int statusCode = weatherFromTheWeb.getWeatherApiResponseStatusFromWeb(countryCode, city, APPID);

        assertEquals(200, statusCode);


    }

    @Test
    public void didItReturnTheHighestAndLowestTemp() throws Exception {

        final ArrayList<String> highestLowestTempResponse =
                weatherFromTheWeb.getHighestAndLowestTemperature(urlBuilder.buildNewForecastRequestURL(countryCode,city, APPID).toString(), 1);

        int numberOfForecastsInJsonARRAY = highestLowestTempResponse.size();
        assertEquals(2, numberOfForecastsInJsonARRAY);
    }

    @Test
    public void ditItReturnThreeDayForecast() throws Exception {

        final HashMap threeDayForecastResponse =
                weatherFromTheWeb.getThreeDaysForecastFromWeb(urlBuilder.buildNewForecastRequestURL(countryCode,city, APPID).toString());



        int numberOfForecastsInHashmap = threeDayForecastResponse.size();
        assertEquals(3, numberOfForecastsInHashmap);

    }
/*
    @Test
    public void doesReturnGeographicalCoordinates() throws Exception {

        //Siia pärast: openWeatherApi.getGeographicalCoordinates vms, mis tagastab linna koordinaadid
        final JSONObject coordinatesResponse = OpenWeatherAPI.getGeographicalCoordinatesOfRequestedCity(countryCode, city, key);
        Boolean responseHasLatitude = coordinatesResponse.has("lat");
        Boolean responseHasLongitude = coordinatesResponse.has("lon");
        Boolean hasLatitudeAndLongitude = responseHasLatitude && responseHasLongitude;
        assertEquals(true, hasLatitudeAndLongitude);
    }
*/
}

