
import com.google.gson.JsonObject;
import java.time.Instant;

public class Deserialize {
    private JsonObject jObject;

    public Deserialize(JsonObject jObject) {
        this.jObject=jObject;
    }

    public Weather buildWeatherFromJson(){
        return new Weather.Builder()
                .location(selectLocation())
                .weather(this.jObject.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("main").getAsString())
                .humidity(this.jObject.get("main").getAsJsonObject().get("humidity").getAsDouble())
                .pressure(this.jObject.get("main").getAsJsonObject().get("pressure").getAsDouble())
                .temp(this.jObject.get("main").getAsJsonObject().get("temp").getAsDouble())
                .wind(this.jObject.get("wind").getAsJsonObject().get("speed").getAsDouble())
                .winddirection(this.jObject.get("wind").getAsJsonObject().get("speed").getAsDouble())
                .ts(Instant.ofEpochSecond(this.jObject.get("dt").getAsLong()))
                .build();
    }

    private Location selectLocation(){
        double lat = this.jObject.get("coord").getAsJsonObject().get("lat").getAsDouble();
        double lon = this.jObject.get("coord").getAsJsonObject().get("lon").getAsDouble();
        return new Location(lat,lon);
    }
}
