package Repository;



import WeatherRequests.UrlBuilder;
import WeatherRequests.UrlBuilderInterface;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

public class OpenWeatherAPI implements WeatherInterface {


    public static final Double INITIAL_VALUE_FOR_MINIMUMTEMPERATURE = 200.0;
    public static final Double INITIAL_VALUE_FOR_MAXIMUMTEMPERATURE = -150.0;

    static String countryCode3 = "EE";
    static String city3 = "Tallinn";
    static String APPID3 = "0786e4e1ae01d4e119f0260e53a683d0";

    UrlBuilderInterface urlBuilder = new UrlBuilder();
    static OkHttpClient client = new OkHttpClient();


    @Override
    public Integer getWeatherApiResponseStatusFromWeb(String city, String APPID) throws IOException {
        return getResponseCodeOfURL(urlBuilder.buildNewSingleWeatherRequestURL(city, APPID).toString());
    }

    @Override
    public Integer getResponseCodeOfURL(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.code();
    }

    @Override
    public JSONArray getWeatherPredictionsForEachTimeInJSONArrayFormFromUrlResponse(String dataFromURLBodyInStringForm) throws JSONException {
        JSONObject jsonObject = new JSONObject(dataFromURLBodyInStringForm);
        return jsonObject.getJSONArray("list");
    }

    @Override
    public String getResponseBodyFromURL(String url) throws IOException {
        Request newURLrequest = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(newURLrequest).execute()) {
            return response.body().string();
        }
    }

    public ArrayList<Double> getLanLotOfCityFromUrlResponseInArrayList (String dataFromURLBodyInStringForm) throws JSONException {
        JSONObject jsonObject = new JSONObject(dataFromURLBodyInStringForm);
        Double linnaLon = Double.parseDouble(jsonObject.getJSONObject("city").getJSONObject("coord").getString("lon"));
        Double linnaLat = Double.parseDouble(jsonObject.getJSONObject("city").getJSONObject("coord").getString("lat"));
        ArrayList<Double> latjalon = new ArrayList<>();
        latjalon.add(linnaLon);
        latjalon.add(linnaLat);
        return latjalon;
    }

    public Double getMaxTemp (JSONArray dataFromURLBodyInJSONForm, Integer numberInArray) throws JSONException {
        return Double.parseDouble(dataFromURLBodyInJSONForm.getJSONObject(numberInArray).getJSONObject("main").getString("temp_max"));
    }

    public Double getMinTemp (JSONArray dataFromURLBodyInJSONForm, Integer numberInArray) throws JSONException {
        return Double.parseDouble(dataFromURLBodyInJSONForm.getJSONObject(numberInArray).getJSONObject("main").getString("temp_min"));
    }

    public String getDateInText (JSONArray dataFromURLBodyInJSONForm, Integer numberInArray) throws JSONException {
        return dataFromURLBodyInJSONForm.getJSONObject(numberInArray).getString("dt_txt").substring(0, 10);
    }


    @Override
    public HashMap<String, Object> createHashMapOfThreeDayForecast (JSONArray dataInJsonArray) throws JSONException {
        double minTemperatuur = INITIAL_VALUE_FOR_MINIMUMTEMPERATURE;
        double maxTemperatuur = INITIAL_VALUE_FOR_MAXIMUMTEMPERATURE;
        HashMap<String, Object> threeDaysWithTheirMinAndMaxTemperatures = new HashMap<>();
        ArrayList<Double> minAndMaxTemperatures = new ArrayList<>();
        for(int i=0; i < dataInJsonArray.length(); i++) {
            if (!Objects.equals(getDateInText(dataInJsonArray, i), getDateInText(dataInJsonArray, 0)) &&
                    (!dataInJsonArray.isNull(i + 1) && (Objects.equals(getDateInText(dataInJsonArray, i), getDateInText(dataInJsonArray,i+1)))) ){
                if (getMaxTemp(dataInJsonArray, i) > maxTemperatuur) {
                    maxTemperatuur = getMaxTemp(dataInJsonArray, i);
                }
                if (getMinTemp(dataInJsonArray, i) < minTemperatuur) {
                    minTemperatuur = getMinTemp(dataInJsonArray, i);
                }
            } else if (!Objects.equals(getDateInText(dataInJsonArray, i), getDateInText(dataInJsonArray, 0)) &&
                    (dataInJsonArray.isNull(i + 1) || (!Objects.equals(getDateInText(dataInJsonArray, i), getDateInText(dataInJsonArray,i+1))))) {
                if (getMaxTemp(dataInJsonArray, i) > maxTemperatuur) {
                    maxTemperatuur = getMaxTemp(dataInJsonArray, i);
                }
                if (getMinTemp(dataInJsonArray, i) < minTemperatuur) {
                    minTemperatuur = getMinTemp(dataInJsonArray, i);
                }
                minAndMaxTemperatures.add(maxTemperatuur);
                minAndMaxTemperatures.add(minTemperatuur);
                if (threeDaysWithTheirMinAndMaxTemperatures.size() < 3) {
                    threeDaysWithTheirMinAndMaxTemperatures.put( getDateInText(dataInJsonArray, i), minAndMaxTemperatures.clone());
                }
                minAndMaxTemperatures.clear();
                maxTemperatuur = INITIAL_VALUE_FOR_MAXIMUMTEMPERATURE;
                minTemperatuur = INITIAL_VALUE_FOR_MINIMUMTEMPERATURE;
            }
        }
        return threeDaysWithTheirMinAndMaxTemperatures;
    }


    public static void main(String[] args) throws IOException, JSONException {
        OpenWeatherAPI test = new OpenWeatherAPI();
        UrlBuilder urliehtiaja = new UrlBuilder();
        JSONArray testAndmed = test.getWeatherPredictionsForEachTimeInJSONArrayFormFromUrlResponse(test.getResponseBodyFromURL(urliehtiaja.buildNewForecastRequestURL(city3, APPID3).toString()));
        HashMap <String, Object> andmed = test.createHashMapOfThreeDayForecast(testAndmed);
        System.out.println(andmed);

    }
}