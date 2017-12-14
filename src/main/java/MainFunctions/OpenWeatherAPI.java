package MainFunctions;


import InputOutputFileFunctions.FileController;
import ResponseFunctions.ResponseController;
import UrlRequests.UrlBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class OpenWeatherAPI implements WeatherInterface {

    private static final Double INITIAL_VALUE_FOR_MINIMUM_TEMPERATURE = 200.0;
    private static final Double INITIAL_VALUE_FOR_MAXIMUM_TEMPERATURE = -150.0;
    private static FileController fileController = new FileController();
    private static UrlBuilder urlBuilder = new UrlBuilder();
    private static OpenWeatherAPI openWeatherAPI = new OpenWeatherAPI();

    public static void main(String[] args) throws IOException, JSONException {

    }


    @Override
    public void writeForecastsForNextThreeDaysToAfile (List<String> cities, ResponseController responseController) throws IOException, JSONException {
        for (String city : cities) {
            String APPID = "0786e4e1ae01d4e119f0260e53a683d0";
            URL foreCastUrl = urlBuilder.buildNewForecastRequestURL(city, APPID);
            String responseBody = responseController.getResponseBodyFromURL(foreCastUrl);
            JSONObject responseInJSON = responseController.makeStringResponseToJSONObject(responseBody);
            String cityName = responseController.getCityName(responseInJSON);
            HashMap<String, Object> threeDays = openWeatherAPI.createHashMapOfThreeDayForecast(responseInJSON, responseController);
            ArrayList<Double> latandlon = openWeatherAPI.getLanLotOfCityFromUrlResponseInArrayList(responseInJSON, responseController);
            List<String> content = new ArrayList<>();
            content.add(threeDays.toString());
            content.add(latandlon.toString());
            fileController.fileWriter(content, cityName);
        }
    }

    @Override
    public HashMap<String, Object> createHashMapOfThreeDayForecast (JSONObject dataInJSONFormat, ResponseController responseController) throws JSONException, IOException {
        JSONArray dataInJsonArray = responseController.getWeatherPredictionJSONArray(dataInJSONFormat);
        double minTemperatuur = INITIAL_VALUE_FOR_MINIMUM_TEMPERATURE;
        double maxTemperatuur = INITIAL_VALUE_FOR_MAXIMUM_TEMPERATURE;
        HashMap<String, Object> threeDaysWithTheirMinAndMaxTemperatures = new HashMap<>();
        ArrayList<Double> minAndMaxTemperatures = new ArrayList<>();

        for(int i=0; i < dataInJsonArray.length(); i++) {
            if (!Objects.equals(responseController.getDateInText(dataInJsonArray, i), responseController.getDateInText(dataInJsonArray, 0)) &&
                    (!dataInJsonArray.isNull(i + 1) && (Objects.equals(responseController.getDateInText(dataInJsonArray, i),
                            responseController.getDateInText(dataInJsonArray,i+1)))) ){
                if (responseController.getMaxTemp(dataInJsonArray, i) > maxTemperatuur) {
                    maxTemperatuur = responseController.getMaxTemp(dataInJsonArray, i);
                }
                if (responseController.getMinTemp(dataInJsonArray, i) < minTemperatuur) {
                    minTemperatuur = responseController.getMinTemp(dataInJsonArray, i);
                }
            } else if (!Objects.equals(responseController.getDateInText(dataInJsonArray, i), responseController.getDateInText(dataInJsonArray, 0)) &&
                    (dataInJsonArray.isNull(i + 1) || (!Objects.equals(responseController.getDateInText(dataInJsonArray, i),
                            responseController.getDateInText(dataInJsonArray,i+1))))) {
                if (responseController.getMaxTemp(dataInJsonArray, i) > maxTemperatuur) {
                    maxTemperatuur = responseController.getMaxTemp(dataInJsonArray, i);
                }
                if (responseController.getMinTemp(dataInJsonArray, i) < minTemperatuur) {
                    minTemperatuur = responseController.getMinTemp(dataInJsonArray, i);
                }
                minAndMaxTemperatures.add(maxTemperatuur);
                minAndMaxTemperatures.add(minTemperatuur);
                if (threeDaysWithTheirMinAndMaxTemperatures.size() < 3) {
                    threeDaysWithTheirMinAndMaxTemperatures.put( responseController.getDateInText(dataInJsonArray, i), minAndMaxTemperatures.clone());
                }
                minAndMaxTemperatures.clear();
                maxTemperatuur = INITIAL_VALUE_FOR_MAXIMUM_TEMPERATURE;
                minTemperatuur = INITIAL_VALUE_FOR_MINIMUM_TEMPERATURE;
            }
        }
        return threeDaysWithTheirMinAndMaxTemperatures;
    }

    @Override
    public ArrayList<Double> getLanLotOfCityFromUrlResponseInArrayList (JSONObject dataFromURLBodyInJSONForm, ResponseController responseController) throws JSONException {
        Double lat = responseController.getLatitude(dataFromURLBodyInJSONForm);
        Double lon = responseController.getLongitude(dataFromURLBodyInJSONForm);
        ArrayList<Double> latandlon = new ArrayList<>();
        latandlon.add(lat);
        latandlon.add(lon);
        return latandlon;
    }

}