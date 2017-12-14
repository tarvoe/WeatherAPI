package ResponseFunctionsTests;


import ResponseFunctions.ResponseController;
import UrlRequests.UrlBuilder;
import org.json.JSONObject;
import org.junit.Test;
import java.net.URL;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

public class ResponseControllerTests {

    private ResponseController responseController = new ResponseController();
    private UrlBuilder urlBuilder = new UrlBuilder();


    @Test
    public void didItMakeStringToJSONObject () throws Exception {
        String city = "Tallinn";
        String APPID = "0786e4e1ae01d4e119f0260e53a683d0";
        URL url = urlBuilder.buildNewForecastRequestURL(city, APPID);
        String dataFromURLBodyInStringForm = responseController.getResponseBodyFromURL(url);
        final JSONObject jsonObject = responseController.makeStringResponseToJSONObject(dataFromURLBodyInStringForm);
        assertThat(jsonObject, instanceOf(JSONObject.class));
    }

}
