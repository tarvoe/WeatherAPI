
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

    @Test
    public void doesReturnCurrentWeatherUrl() throws Exception {

        //Näidissisend forecastUrlile
        final URL forecastUrl = OpenWeatherAPI.weatherRequestURL(countryCode, city/*, APIToken parameeter siia*/);

        //Testib kas forecastUrl on URL klassist
        assertThat(forecastUrl, instanceOf(URL.class));
    }

    @Test
    public void doesTheRequestConnectToAPI() throws Exception {

        int statusCode = OpenWeatherAPI.getWeatherApiResponseStatus(countryCode, city/*, APIToken parameeter siia*/);

        assertEquals(200, statusCode);

    @Test
    public void doesReturnThreeDayForecast() throws Exception {

        // Siia pärast: openWeatherApi.getThreeDayWeather vms, mis tagastab json listi ja tuleb leida listi lenght
        final JSONArray threeDayForecastResponse = 1;

        int numberOfForecastsInJsonList = threeDayForecastResponse.length();
        assertEquals(3, numberOfForecastsInJsonList);

    }
    }
/*
    @Test
    public void doesReturnHighestAndLowestTemperatureOfEachDay() throws Exception {

        //Siia pärast: openWeatherApi.getHighestAndLowestTemperaturesForEachDay vms, mis tagastab kõrgima ja madalaima temperatuuri
        //TODO: Implement code

    }

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

