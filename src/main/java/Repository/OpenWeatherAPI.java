package Repository;



import jdk.nashorn.internal.parser.JSONParser;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OpenWeatherAPI implements WeatherInterface {



    static String countryCode3 = "EE";
    static String city3 = "Tallinn";
    static String APPID3 = "0786e4e1ae01d4e119f0260e53a683d0";

    static OkHttpClient client = new OkHttpClient();

    @Override
    public URL buildNewSingleWeatherRequestURL(String countryCode, String city, String APPID) {

            return new HttpUrl.Builder()
                    .scheme("http")
                .host("api.openweathermap.org")
                .addPathSegments("/data/2.5/weather")
                .addQueryParameter("q", countryCode + "," + city)
                .addQueryParameter("APPID", APPID)
                .addQueryParameter("units", "metric")
                .build().url();
    }

    @Override
    public URL buildNewForecastRequestURL(String countryCode, String city, String APPID) {

        return new HttpUrl.Builder()
                .scheme("http")
                .host("api.openweathermap.org")
                .addPathSegments("/data/2.5/forecast")
                .addQueryParameter("q", countryCode + "," + city)
                .addQueryParameter("APPID", APPID)
                .addQueryParameter("units", "metric")
                .build().url();
    }

    @Override
    public Integer getWeatherApiResponseStatusFromWeb(String countryCode, String city, String APPID) throws IOException {
        return getResponseCodeOfURL(buildNewSingleWeatherRequestURL(countryCode, city, APPID).toString());
    }

    @Override
    public Integer getResponseCodeOfURL(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.code();
    }


    public JSONArray makeStringToJSONArray (String dataFromURLBodyInStringForm) throws JSONException {
        JSONObject jsonObject = new JSONObject(dataFromURLBodyInStringForm);
        JSONArray dataFromURLBodyInJSONArrayForm = jsonObject.getJSONArray("list");
        return dataFromURLBodyInJSONArrayForm;
    }

    String getResponseBodyFromURL(String url) throws IOException {
        Request newURLrequest = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(newURLrequest).execute()) {
            return response.body().string();
        }
    }

    @Override
    public HashMap<String, ArrayList> getThreeDaysForecastFromWeb(String url) throws IOException, JSONException {
        OpenWeatherAPI threeDayForecast = new OpenWeatherAPI();
        String response = threeDayForecast.getResponseBodyFromURL(url);

        HashMap<String,ArrayList> days= new HashMap<>();

        JSONArray responseInJSONArray = makeStringToJSONArray(response);
        for (int i = 0; i<3; i++){

            ArrayList<String> minmaxtemp = new ArrayList<>();
            String oneDay = responseInJSONArray.getJSONObject(i).getString("dt_txt");
            String MAXtemp = responseInJSONArray.getJSONObject(i).getJSONObject("main").getString("temp_max");
            String MINtemp = responseInJSONArray.getJSONObject(i).getJSONObject("main").getString("temp_min");
            minmaxtemp.add(MAXtemp);
            minmaxtemp.add(MINtemp);
            days.put(oneDay, minmaxtemp);
        }
        return days;
    }

    @Override
    public ArrayList<String> getHighestAndLowestTemperature (String url, int indexOfTheDay) throws IOException, JSONException{
        OpenWeatherAPI highestLowest = new OpenWeatherAPI();
        ArrayList<String> hilotemperatures = new ArrayList<>();
        String response = highestLowest.getResponseBodyFromURL(url);
        JSONArray responseInJSONArray = makeStringToJSONArray(response);
        String MAXtemp = responseInJSONArray.getJSONObject(indexOfTheDay).getJSONObject("main").getString("temp_max");
        String MINtemp = responseInJSONArray.getJSONObject(indexOfTheDay).getJSONObject("main").getString("temp_min");
        hilotemperatures.add(MAXtemp);
        hilotemperatures.add(MINtemp);
        return hilotemperatures;
    }

    public static void main(String[] args) throws IOException, JSONException {
        OpenWeatherAPI test = new OpenWeatherAPI();
        String andmed2 = test.getResponseBodyFromURL(test.buildNewForecastRequestURL(countryCode3,city3,APPID3).toString());
        System.out.println(andmed2);

        JSONArray andmed2Arrays = test.makeStringToJSONArray(andmed2);
        System.out.println(andmed2Arrays);
        /*String response = test.getResponseBodyFromURL(andmed);
        JSONArray andmedListis = test.makeStringToJSONArray(response);
        System.out.println(andmedListis.getClass());
        System.out.println(andmedListis);
        JSONObject kuupäev = andmedListis.getJSONObject(0);
        String aeg = kuupäev.getString("dt_txt");
        System.out.println(kuupäev);*/

        /*
        HashMap<String, ArrayList> vastus = test.getThreeDaysForecastFromWeb(test.buildNewForecastRequestURL(countryCode3,city3,APPID3).toString());
        System.out.println(vastus);

        JSONArray testandmed = test.makeStringToJSONArray(test.getResponseBodyFromURL(test.buildNewForecastRequestURL(countryCode3,city3,APPID3).toString()));

        //ykskindelpaev on JSONobject
        String kindelRidaJSONObjektist = testandmed.getJSONObject(0).getJSONObject("main").getString("temp_max");
        System.out.println(kindelRidaJSONObjektist);*/
    }
}





/*
class SingleDayForecastBuilder extends OpenWeatherAPI {
    public URL buildNewSingleWeatherRequestURL(String countryCode, String city, String APPID) {

        return new HttpUrl.Builder()
                .scheme("http")
                .host("api.openweathermap.org")
                .addPathSegments("/data/2.5/weather")
                .addQueryParameter("q", countryCode + "," + city)
                .addQueryParameter("APPID", APPID)
                .build().url();
    }
}

class ThreeDayForecastBuilder extends OpenWeatherAPI {
    public URL buildNewSingleWeatherRequestURL(String countryCode, String city, String APPID) {

        return new HttpUrl.Builder()
                .scheme("http")
                .host("api.openweathermap.org")
                .addPathSegments("/data/2.5/forecast")
                .addQueryParameter("q", countryCode + "," + city)
                .addQueryParameter("APPID", APPID)
                .build().url();
    }
}*/