
import Repository.WeatherInterface;
import org.json.JSONArray;
import org.junit.Test;
import java.net.URL;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import Repository.OpenWeatherAPI;
public class WeatherAPITests {

    private String countryCode = "EE";
    private String city = "Tallinn";
    private String APPID = "0786e4e1ae01d4e119f0260e53a683d0";

    WeatherInterface weatherFromTheWeb = new OpenWeatherAPI();

    @Test
    public void didItReturnAUrl() throws Exception {

        //Tekitab näidissisendi forecastUrlile
        final URL singleWeatherRequestURL = weatherFromTheWeb.buildNewSingleWeatherRequestURL(countryCode, city, APPID );
        final URL forecastRequestURL = weatherFromTheWeb.buildNewForecastRequestURL(countryCode, city, APPID );


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
    public void didItReturnTheHighestAndLowestTempForEachDay() throws Exception {

        final JSONArray highestLowestTempResponse =
                weatherFromTheWeb.getHighestAndLowestTemperature(weatherFromTheWeb.buildNewSingleWeatherRequestURL(countryCode,city, APPID).toString());

        int numberOfForecastsInJsonARRAY = highestLowestTempResponse.length();
        assertEquals(3, numberOfForecastsInJsonARRAY);
    }

    @Test
    public void ditItReturnThreeDayForecast() throws Exception {

        final JSONArray threeDayForecastResponse =
                weatherFromTheWeb.getThreeDaysForecastFromWeb(weatherFromTheWeb.buildNewSingleWeatherRequestURL(countryCode,city, APPID).toString());

        int numberOfForecastsInJsonARRAY = threeDayForecastResponse.length();
        assertEquals(3, numberOfForecastsInJsonARRAY);

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

