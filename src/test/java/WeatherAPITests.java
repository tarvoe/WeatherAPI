
import org.junit.Test;
import java.net.URL;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import Repository.OpenWeatherAPI;
public class WeatherAPITests {

    String countryCode = "EE";
    String city = "Tallinn";

    @Test
    public void doesReturnForecastUrl() throws Exception {

        //Näidissisend forecastUrlile
        final URL forecastUrl = OpenWeatherAPI.weatherRequestURL(countryCode, city);

        //Testib kas forecastUrl on URL klassist
        assertThat(forecastUrl, instanceOf(URL.class));
    }

    @Test
    public void doesConnectToAPI() throws Exception {

        // Siia pärast : openWeatherApi.getStatusCode vms mis tagastab lehekülje staatuse
        final Integer responseStatusCode = 2;

        int statusCode = responseStatusCode;
        assertEquals(statusCode, 200);
    }

    @Test
    public void doesReturnThreeDayForecast() throws Exception {

        // Siia pärast: openWeatherApi.getThreeDayWeather vms, mis tagastab json listi ja tuleb leida listi lenght
        final JSONArray threeDayForecastResponse = 1;

        int numberOfForecastsInJsonList = threeDayForecastResponse.length();
        assertEquals(3, numberOfForecastsInJsonList);

    }

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

}

