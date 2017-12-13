
import InputOutputFileFunctions.FileController;
import InputOutputFileFunctions.UserInputController;
import ResponseFunctions.ResponseController;
import UrlRequests.UrlBuilder;
import MainFunctions.WeatherInterface;
import org.json.JSONObject;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import MainFunctions.OpenWeatherAPI;
public class WeatherAPITests {

    private String countryCode = "EE";
    private String city = "Tallinn";
    private String APPID = "0786e4e1ae01d4e119f0260e53a683d0";

    WeatherInterface weatherFromTheWeb = new OpenWeatherAPI();
    ResponseController responseController = new ResponseController();
    UrlBuilder urlBuilder = new UrlBuilder();
    UserInputController userInputController = new UserInputController();
    FileController fileController = new FileController();

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

    @Test
    public void didTheRequeassastConnectToAPI() throws Exception {
        ResponseController controllerMock = mock(ResponseController.class);
    }
    @Test
    public void didItReturnTheHighestAndLowestTempForThreeNextThreeDays() throws Exception {
        URL url = urlBuilder.buildNewForecastRequestURL(city, APPID);
        String dataFromURLBodyInStringForm = responseController.getResponseBodyFromURL(url);
        JSONObject dataInJSONFormat = responseController.makeStringResponseToJSONObject(dataFromURLBodyInStringForm);
        final HashMap<String, Object> highestLowestTempsForNextThreeDays = weatherFromTheWeb.createHashMapOfThreeDayForecast(dataInJSONFormat, responseController);
        int numberOfForecastsInJsonARRAY = highestLowestTempsForNextThreeDays.size();
        assertEquals(3, numberOfForecastsInJsonARRAY);
    }

    @Test
    public void didItReturnLatitudeAndLonitude() throws Exception {
        URL url = urlBuilder.buildNewForecastRequestURL(city, APPID);
        String dataFromURLBodyInStringForm = responseController.getResponseBodyFromURL(url);
        JSONObject dataFromURLBodyInJSONForm= responseController.makeStringResponseToJSONObject(dataFromURLBodyInStringForm);
        final ArrayList<Double> lanandlot = weatherFromTheWeb.getLanLotOfCityFromUrlResponseInArrayList(dataFromURLBodyInJSONForm, responseController);
        assertEquals(2, lanandlot.size());
    }

    @Test
    public void didItMakeStringToJSONObject () throws Exception {
        URL url = urlBuilder.buildNewForecastRequestURL(city, APPID);
        String dataFromURLBodyInStringForm = responseController.getResponseBodyFromURL(url);
        final JSONObject jsonObject = responseController.makeStringResponseToJSONObject(dataFromURLBodyInStringForm);
        assertThat(jsonObject, instanceOf(JSONObject.class));
    }



    @Test
    public void didItReturnACorrectFilePath(){
        String fileName = "input.txt";
        final String file = fileController.filePathConstructor(fileName);
        assertEquals("C:\\Users\\Tarvo\\IdeaProjects\\"+ fileName, file);
    }

    @Test
    public void didItReadAFile () throws IOException {
        String fileLocation = "C:\\Users\\Tarvo\\IdeaProjects\\testDidItReadAFile.txt";
        List<String> linesInFile = fileController.fileReader(fileLocation);
        assertEquals("Tallinn", linesInFile.get(0));
    }
}

