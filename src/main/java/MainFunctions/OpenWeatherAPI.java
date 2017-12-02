package MainFunctions;


import InputOutputFileFunctions.FileController;
import InputOutputFileFunctions.UserInputController;
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
    private static ResponseController responseController = new ResponseController();
    private static String APPID = "0786e4e1ae01d4e119f0260e53a683d0";

    public static void main(String[] args) throws IOException, JSONException {
        FileController fileController = new FileController();
        UserInputController userInputController = new UserInputController();
        UrlBuilder urlBuilder = new UrlBuilder();
        ResponseController responseController = new ResponseController();
        OpenWeatherAPI openWeatherAPI = new OpenWeatherAPI();

        List<String> cities = userInputController.userInputTaker();

        for (int i=0 ; i<cities.size(); i++) {
            URL foreCastUrl = urlBuilder.buildNewForecastRequestURL(cities.get(i), APPID);
            String responseBody = responseController.getResponseBodyFromURL(foreCastUrl);
            JSONObject responseInJSON = responseController.makeStringResponseToJSONObject(responseBody);
            String cityName = responseController.getCityName(responseInJSON);

            HashMap<String, Object> threeDays = openWeatherAPI.createHashMapOfThreeDayForecast(responseInJSON);
            System.out.println(threeDays.toString());

            ArrayList<Double> latandlon = openWeatherAPI.getLanLotOfCityFromUrlResponseInArrayList(responseInJSON);

            List <String> content = new ArrayList<>();
            content.add(threeDays.toString());
            content.add(latandlon.toString());
            fileController.fileWriter(content, cityName);
        }


    }

    @Override
    public HashMap<String, Object> createHashMapOfThreeDayForecast (JSONObject dataInJSONFormat) throws JSONException {
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
        System.out.println("The maximum and minimum temperatures in " + responseController.getCityName(dataInJSONFormat) + " for the next three days are:");
        return threeDaysWithTheirMinAndMaxTemperatures;
    }

    @Override
    public ArrayList<Double> getLanLotOfCityFromUrlResponseInArrayList (JSONObject dataFromURLBodyInJSONForm) throws JSONException {
        Double lan = responseController.getLatitude(dataFromURLBodyInJSONForm);
        Double lon = responseController.getLongitude(dataFromURLBodyInJSONForm);
        ArrayList<Double> latandlon = new ArrayList<>();
        latandlon.add(lan);
        latandlon.add(lon);
        return latandlon;
    }

}