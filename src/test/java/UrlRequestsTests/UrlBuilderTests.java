package UrlRequestsTests;

import ResponseFunctions.ResponseController;
import UrlRequests.UrlBuilder;
import org.junit.Test;

import java.net.URL;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class UrlBuilderTests {

    private UrlBuilder urlBuilder = new UrlBuilder();
    private ResponseController responseController = new ResponseController();
    private String city = "Tallinn";
    private String APPID = "0786e4e1ae01d4e119f0260e53a683d0";

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
        int statusCode = responseController.getWeatherApiResponseStatusFromWeb( city, APPID);
        assertEquals(200, statusCode);
    }
}
