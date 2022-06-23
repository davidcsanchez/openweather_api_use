import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Serialize {
    public String weatherToJsonFormat(Weather Object)  {

        Gson gson = new GsonBuilder().registerTypeAdapter(Instant.class, new JsonSerializer<Instant>() {
            @Override
            public JsonElement serialize(Instant src, Type typeOfSrc, JsonSerializationContext context) {
                return new JsonPrimitive(src.toString());
            }
        }).create();
        return gson.toJson(Object);
    }
}
