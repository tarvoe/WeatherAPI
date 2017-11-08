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

    static String countryCode3 = "EE";
    static String city3 = "Tallinn";
    static String APPID3 = "0786e4e1ae01d4e119f0260e53a683d0";

    UrlBuilderInterface urlBuilder = new UrlBuilder();
    static OkHttpClient client = new OkHttpClient();


    @Override
    public Integer getWeatherApiResponseStatusFromWeb(String countryCode, String city, String APPID) throws IOException {
        return getResponseCodeOfURL(urlBuilder.buildNewSingleWeatherRequestURL(countryCode, city, APPID).toString());
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
            ArrayList<String> minmaxtemp = threeDayForecast.getHighestAndLowestTemperature(url, i);
            String oneDay = responseInJSONArray.getJSONObject(i).getString("dt_txt");
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
        String testAndmed = test.getResponseBodyFromURL(test.urlBuilder.buildNewForecastRequestURL(countryCode3,city3, APPID3).toString());

        JSONArray testAndmedJsonis = test.makeStringToJSONArray(testAndmed);
        //Sul on JSONArray millest tahad kuupäeva järgi for loopides üle käia et saad ainult 3 järgmist päeva ja nende min max temp


        HashMap<Object, String> kolmpaevakoosilmaga = new HashMap<>();
        ArrayList<Double> minmaxtempid = new ArrayList<>();
        ArrayList<String> kuupaevad = new ArrayList<>();
        ArrayList<Double> mintempid = new ArrayList<>();
        ArrayList<Double> maxtempid = new ArrayList<>();

        for(int i=0; i < 15; i++){
            if(Objects.equals(testAndmedJsonis.getJSONObject(i).getString("dt_txt").substring(0,10),
                    testAndmedJsonis.getJSONObject(0).getString("dt_txt").substring(0,10))){
                i=i;
            }
            else if (Objects.equals(testAndmedJsonis.getJSONObject(i).getString("dt_txt").substring(0,10),
                    testAndmedJsonis.getJSONObject(i+1).getString("dt_txt").substring(0,10)))
            {
                Double MAXtempForCurrentDayAtGivenTime = Double.parseDouble(testAndmedJsonis.getJSONObject(i).getJSONObject("main").getString("temp_max"));
                Double MINtempForCurrentDayAtGivenTime = Double.parseDouble(testAndmedJsonis.getJSONObject(i).getJSONObject("main").getString("temp_min"));
                mintempid.add(MINtempForCurrentDayAtGivenTime);
                maxtempid.add(MAXtempForCurrentDayAtGivenTime);

            }else {

                System.out.println(mintempid);
                System.out.println(maxtempid);
                //Miinimum ja maksimum temperatuuride summade leidmine
                double minsum = 0.0;
                double maxsum = 0.0;

                for (int x=0; x < mintempid.size(); x++) {
                    minsum += mintempid.get(x);
                }
                for (int v=0; v < maxtempid.size(); v++) {
                    maxsum += maxtempid.get(v);
                }


                // Aritmeetilise keskmise lisamine arraylisti
                minmaxtempid.add(minsum/mintempid.size());
                minmaxtempid.add(maxsum/maxtempid.size());

                //Minmax array ja kuupäeva lisamine hashmappi
                kolmpaevakoosilmaga.put(minmaxtempid.clone(),testAndmedJsonis.getJSONObject(i).getString("dt_txt").substring(0,10));

                //Vahelistide tühjendamine
                minmaxtempid.clear();
                maxtempid.clear();
                mintempid.clear();
            }
        }

        //Testiks välja prinditud
        System.out.println(kolmpaevakoosilmaga);

    }
}
