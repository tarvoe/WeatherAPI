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


    public static final Double ESIALGNE_VAARTUS_MINTEMPERATUURILE = 200.0;
    public static final Double ESIALGNE_VAARTUS_MAXTEMPERATUURILE = -150.0;

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
    public JSONArray makeStringToJSONArray (String dataFromURLBodyInStringForm) throws JSONException {
        JSONObject jsonObject = new JSONObject(dataFromURLBodyInStringForm);
        JSONArray dataFromURLBodyInJSONArrayForm = jsonObject.getJSONArray("list");
        return dataFromURLBodyInJSONArrayForm;
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


    @Override
    public HashMap<Object, String> createHashMapOfThreeDayForecast (JSONArray jsonAndmed) throws JSONException {
        double minTemperatuur = ESIALGNE_VAARTUS_MINTEMPERATUURILE;
        double maxTemperatuur = ESIALGNE_VAARTUS_MAXTEMPERATUURILE;
        HashMap<Object, String> kolmPaevaKoosIlmaga = new HashMap<>();
        ArrayList<Double> minmaxtempid = new ArrayList<>();

        //Kogu array läbi itereerimine, et saada kätte just järgmised kolm päeva koos nende miinimum ja maksimum temperatuuridega

        for(int i=0; i < jsonAndmed.length(); i++) {

            /* Kontrollime ega number i jsonobjekti kuupäev pole sama mis esimese objekti kuupäev, sest esimese jsonobjekti kuupäev on alati esimene
             päev ja esimese päeva kohta me andmeid ei taha */

            if (Objects.equals(jsonAndmed.getJSONObject(i).getString("dt_txt").substring(0, 10),
                    jsonAndmed.getJSONObject(0).getString("dt_txt").substring(0, 10))) {
                i = i;

            /* Kontrollime kas järgmine i-st järgmine jsonobjekt on olemas - see annab meile teada, kas me oleme array lõppu jõudnud või mitte
               Seejärel vaatame veel kas i-st järgmine jsonobjekt on sama kuupäevaga - see annab meile aimu kas me oleme arrays kõik ühe päeva
               piires olevad ennustused läbi käinud (kuna ennustused on iga 3h tagant)    */

            } else if (!jsonAndmed.isNull(i + 1) &&
                    Objects.equals(jsonAndmed.getJSONObject(i).getString("dt_txt").substring(0, 10),
                            jsonAndmed.getJSONObject(i + 1).getString("dt_txt").substring(0, 10))
                    ) {
                if (Double.parseDouble(jsonAndmed.getJSONObject(i).getJSONObject("main").getString("temp_max")) > maxTemperatuur) {
                    maxTemperatuur = Double.parseDouble(jsonAndmed.getJSONObject(i).getJSONObject("main").getString("temp_max"));
                }
                if (Double.parseDouble(jsonAndmed.getJSONObject(i).getJSONObject("main").getString("temp_min")) < minTemperatuur) {
                    minTemperatuur = Double.parseDouble(jsonAndmed.getJSONObject(i).getJSONObject("main").getString("temp_min"));
                }

            /* Kui näeme, et me oleme arrays ühe konkreetse päeva piires viimase jsonobjekti juures, vaatame viimast korda ega seal mintemp
               või maxtemp ei muutu, kui muutub siis teeme muudatuse, seejärel lisame min- ja maxtempid minmaxtemp arraysse ja lisame Hashmapi
               minmaxtemp array ja kuupäeva mis nende temperatuuridega kokku käib    */

            } else {
                if (Double.parseDouble(jsonAndmed.getJSONObject(i).getJSONObject("main").getString("temp_max")) > maxTemperatuur) {
                    maxTemperatuur = Double.parseDouble(jsonAndmed.getJSONObject(i).getJSONObject("main").getString("temp_max"));
                }
                if (Double.parseDouble(jsonAndmed.getJSONObject(i).getJSONObject("main").getString("temp_min")) < minTemperatuur) {
                    minTemperatuur = Double.parseDouble(jsonAndmed.getJSONObject(i).getJSONObject("main").getString("temp_min"));
                }
                minmaxtempid.add(maxTemperatuur);
                minmaxtempid.add(minTemperatuur);
                //Minmax array ja kuupäeva lisamine hashmappi
                if (kolmPaevaKoosIlmaga.size() < 3) {
                    kolmPaevaKoosIlmaga.put(minmaxtempid.clone(), jsonAndmed.getJSONObject(i).getString("dt_txt").substring(0, 10));
                }
                //Vahemuutujate tühjendamine
                minmaxtempid.clear();
                maxTemperatuur = ESIALGNE_VAARTUS_MAXTEMPERATUURILE;
                minTemperatuur = ESIALGNE_VAARTUS_MINTEMPERATUURILE;
            }
        }
        return kolmPaevaKoosIlmaga;
    }


    public static void main(String[] args) throws IOException, JSONException {
        System.out.println("Jõhkralt kole funktsioon ma tean");
    }
}











// Võibolla läheb veel vaja
/*
    @Override
    public HashMap<String, ArrayList> getThreeDaysForecastFromWeb(String url) throws IOException, JSONException {
        OpenWeatherAPI threeDayForecast = new OpenWeatherAPI();
        String response = threeDayForecast.getResponseBodyFromURL(url);
        HashMap<String,ArrayList> days= new HashMap<>();
        JSONArray responseInJSONArray = makeStringToJSONArray(response);
        for (int i = 0; i<3; i++){
            ArrayList<String> minmaxtemp = threeDayForecast.getHighestAndLowestTemperatureForTheNextThreeDays(url, i);
            String oneDay = responseInJSONArray.getJSONObject(i).getString("dt_txt");
            days.put(oneDay, minmaxtemp);
        }
        return days;
    }

    /*@Override
    public ArrayList<String> getHighestAndLowestTemperatureForTheNextThreeDays (String url, int indexOfTheDay) throws IOException, JSONException{
        OpenWeatherAPI highestLowest = new OpenWeatherAPI();
        ArrayList<String> hilotemperatures = new ArrayList<>();

        String response = highestLowest.getResponseBodyFromURL(url);
        JSONArray responseInJSONArray = makeStringToJSONArray(response);

        String MAXtemp = responseInJSONArray.getJSONObject(indexOfTheDay).getJSONObject("main").getString("temp_max");
        String MINtemp = responseInJSONArray.getJSONObject(indexOfTheDay).getJSONObject("main").getString("temp_min");

        hilotemperatures.add(MAXtemp);
        hilotemperatures.add(MINtemp);
        return hilotemperatures;
    }*/