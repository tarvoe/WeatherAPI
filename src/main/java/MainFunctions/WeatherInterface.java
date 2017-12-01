package MainFunctions;


import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

public interface WeatherInterface {

    HashMap<String, Object> createHashMapOfThreeDayForecast(JSONObject dataInJSONFormat)throws JSONException;
    ArrayList<Double> getLanLotOfCityFromUrlResponseInArrayList (JSONObject dataFromURLBodyInStringForm) throws JSONException;

}
