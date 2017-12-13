package MainFunctions;


import ResponseFunctions.ResponseController;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface WeatherInterface {

    HashMap<String, Object> createHashMapOfThreeDayForecast( JSONObject dataInJSONFormat, ResponseController responseController)throws JSONException, IOException;
    ArrayList<Double> getLanLotOfCityFromUrlResponseInArrayList (JSONObject dataFromURLBodyInStringForm,ResponseController responseController) throws JSONException;
    void writeForecastsForNextThreeDaysToAfile (List<String> cities,ResponseController responseController) throws IOException, JSONException;

}
