
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.client.utils.URIBuilder;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DataExtractor implements Sensor {

    private String city;
    private JsonObject jObject;

    DataExtractor(String city) {
        this.city = city;
    }

    public Weather extractData() throws IOException, InterruptedException, URISyntaxException {
        HttpClient client = HttpClient.newHttpClient();
        String url = createUrl();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .build();
        this.jObject = getJsonDataFromRequest(client, request);
        return new Deserialize(this.jObject).buildWeatherFromJson();
    }

    private String createUrl() throws URISyntaxException {
        final String appid = "bd394e4a9cb45bd8df7d41a2be02da08";
        URIBuilder processURL = new URIBuilder("https://api.openweathermap.org/data/2.5/weather");
        processURL.addParameter("q", this.city);
        processURL.addParameter("appid", appid);
        processURL.addParameter("units", "metric");
        return processURL.build().toString();
    }

    private JsonObject getJsonDataFromRequest(HttpClient client, HttpRequest request) throws IOException, InterruptedException {
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return new Gson().fromJson(response.body(), JsonObject.class);
    }

}
