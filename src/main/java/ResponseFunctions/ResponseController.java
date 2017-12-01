package ResponseFunctions;

import UrlRequests.UrlBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URL;


public class ResponseController {

    static OkHttpClient client = new OkHttpClient();
    static UrlBuilder urlBuilder = new UrlBuilder();

    public static void main(String[] args) throws IOException {
        UrlBuilder builder = new UrlBuilder();
        ResponseController controller = new ResponseController();
        URL url = builder.buildNewForecastRequestURL("Tallinn", "0786e4e1ae01d4e119f0260e53a683d0");
        System.out.println(url);
        System.out.println(controller.getResponseBodyFromURL(url));


    }

    public Integer getWeatherApiResponseStatusFromWeb(String city, String APPID) throws IOException {
        return getResponseCodeOfURL(urlBuilder.buildNewSingleWeatherRequestURL(city, APPID));
    }

    public Integer getResponseCodeOfURL(URL url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        return response.code();
    }

    public String getResponseBodyFromURL (URL url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public JSONObject makeStringResponseToJSONObject(String dataFromURLBodyInStringForm) throws JSONException {
        JSONObject jsonObject = new JSONObject(dataFromURLBodyInStringForm);
        return jsonObject;
    }

    public JSONArray getWeatherPredictionJSONArray(JSONObject dataFromURLBodyInJSONForm) throws JSONException {
        return dataFromURLBodyInJSONForm.getJSONArray("list");
    }

    public Double getMaxTemp (JSONArray dataFromURLBodyInJSONForm, Integer numberInArray) throws JSONException {
        return Double.parseDouble(dataFromURLBodyInJSONForm.getJSONObject(numberInArray).getJSONObject("main").getString("temp_max"));
    }

    public Double getMinTemp (JSONArray dataFromURLBodyInJSONForm, Integer numberInArray) throws JSONException {
        return Double.parseDouble(dataFromURLBodyInJSONForm.getJSONObject(numberInArray).getJSONObject("main").getString("temp_min"));
    }

    public Double getLatitude (JSONObject dataFromURLBodyInJSONForm) throws JSONException {
        return Double.parseDouble(dataFromURLBodyInJSONForm.getJSONObject("city").getJSONObject("coord").getString("lat"));
    }

    public Double getLongitude(JSONObject dataFromURLBodyInJSONForm) throws JSONException {
        return Double.parseDouble(dataFromURLBodyInJSONForm.getJSONObject("city").getJSONObject("coord").getString("lon"));
    }

    public String getDateInText (JSONArray dataFromURLBodyInJSONForm, Integer numberInArray) throws JSONException {
        return dataFromURLBodyInJSONForm.getJSONObject(numberInArray).getString("dt_txt").substring(0, 10);
    }

    public String getCityName (JSONObject dataFromURLBodyInJSONForm) throws JSONException {
        return dataFromURLBodyInJSONForm.getJSONObject("city").getString("name");
    }


}
