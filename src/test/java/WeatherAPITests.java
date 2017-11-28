
import WeatherRequests.UrlBuilder;
import WeatherRequests.UrlBuilderInterface;
import Repository.WeatherInterface;
import org.junit.Test;
import java.net.URL;
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
    public void didItReturnASingleWeatherRequestUrl() throws Exception {
        final URL singleWeatherRequestURL = urlBuilder.buildNewSingleWeatherRequestURL(city, APPID );
        assertThat(singleWeatherRequestURL, instanceOf(URL.class));
    }

    @Test
    public void didItReturnAForecastWeatherRequestUrl() throws Exception {
        final URL forecastRequestURL = urlBuilder.buildNewForecastRequestURL( city, APPID );
        assertThat(forecastRequestURL, instanceOf(URL.class));
    }


    @Test
    public void didTheRequestConnectToAPI() throws Exception {
        int statusCode = weatherFromTheWeb.getWeatherApiResponseStatusFromWeb( city, APPID);
        assertEquals(200, statusCode);
    }

    @Test
    public void didItReturnTheHighestAndLowestTempForThreeNextThreeDays() throws Exception {

        final HashMap<String, Object> highestLowestTempForNextThreeDays =
                weatherFromTheWeb.createHashMapOfThreeDayForecast(
                        weatherFromTheWeb.getWeatherPredictionsForEachTimeInJSONArrayFormFromUrlResponse(
                                weatherFromTheWeb.getResponseBodyFromURL(urlBuilder.buildNewForecastRequestURL(city, APPID).toString())));

        int numberOfForecastsInJsonARRAY = highestLowestTempForNextThreeDays.size();
        assertEquals(3, numberOfForecastsInJsonARRAY);
    }

    /*
    @Test
    public void ditItReturnThreeDayForecast() throws Exception {

        final HashMap threeDayForecastResponse =
                weatherFromTheWeb.getThreeDaysForecastFromWeb(urlBuilder.buildNewForecastRequestURL(city, APPID).toString());



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

