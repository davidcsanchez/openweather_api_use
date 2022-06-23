
import com.google.gson.JsonObject;
import java.time.Instant;

public class Deserializer {
    private JsonObject jObject;

    public Deserializer(JsonObject jObject) {
        this.jObject=jObject;
    }

    public Weather buildWeatherFromJson(){
        return new Weather.Builder()
                .location(selectLocation())
                .humidity(this.jObject.get("humidity").getAsDouble())
                .pressure(this.jObject.get("pressure").getAsDouble())
                .temp(this.jObject.get("temp").getAsDouble())
                .ts(Instant.parse(this.jObject.get("ts").getAsString()))
                .build();
    }

    private Location selectLocation(){
        double lat = this.jObject.get("location").getAsJsonObject().get("lat").getAsDouble();
        double lon = this.jObject.get("location").getAsJsonObject().get("lon").getAsDouble();
        return new Location(lat,lon);
    }




}
