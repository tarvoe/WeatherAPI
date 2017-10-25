
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

    OpenWeatherAPI weatherFromTheWeb = new OpenWeatherAPI();

    @Test
    public void didItReturnAUrl() throws Exception {

        //Tekitab näidissisendi forecastUrlile
        final URL forecastUrl = weatherFromTheWeb.buildNewWeatherRequestURL(countryCode, city/*, APIToken parameeter siia*/);

        //Testib kas forecastUrl on URL klassist
        assertThat(forecastUrl, instanceOf(URL.class));
    }

    @Test
    public void didTheRequestConnectToAPI() throws Exception {

        int statusCode = weatherFromTheWeb.getWeatherApiResponseStatusFromWeb(countryCode, city/*, APIToken parameeter siia*/);

        assertEquals(200, statusCode);

    }

    @Test
    public void ditItReturnThreeDayForecast() throws Exception {

        final JSONArray threeDayForecastResponse =
                weatherFromTheWeb.getThreeDaysForecastFromWeb(weatherFromTheWeb.buildNewWeatherRequestURL(countryCode,city).toString());

        int numberOfForecastsInJsonARRAY = threeDayForecastResponse.length();
        assertEquals(3, numberOfForecastsInJsonARRAY);

    }


    @Test
    public void didItReturnTheHighestAndLowestTempForEachDay() throws Exception {

        final JSONArray highestLowestTempResponse =
                weatherFromTheWeb.getHighestAndLowestTemperature(weatherFromTheWeb.buildNewWeatherRequestURL(countryCode,city).toString());

        int numberOfForecastsInJsonARRAY = highestLowestTempResponse.length();
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

