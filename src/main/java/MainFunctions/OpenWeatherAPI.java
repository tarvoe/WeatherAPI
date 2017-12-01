package MainFunctions;


import ResponseFunctions.ResponseController;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.*;

public class OpenWeatherAPI implements WeatherInterface {

    private static final Double INITIAL_VALUE_FOR_MINIMUM_TEMPERATURE = 200.0;
    private static final Double INITIAL_VALUE_FOR_MAXIMUM_TEMPERATURE = -150.0;
    private static ResponseController responseController = new ResponseController();

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
        System.out.println("The minimum and maximum temperatures in " + responseController.getCityName(dataInJSONFormat) + " for the next three days are:");
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

    public static void main(String[] args) throws IOException, JSONException {

    }
}