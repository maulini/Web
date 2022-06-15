package net.minedof.web.deserializable;

import com.google.gson.*;
import net.minedof.web.model.Location;

import java.util.ArrayList;
import java.util.List;

public final class JsonUtils {

    private JsonUtils(){}

    public static List<String> getCity(String json) {
        return get(json, "city");
    }

    public static List<String> getCityCode(String json) {
        return get(json, "postcode");
    }

    public static List<String> getCityAddress(String json) {
        return get(json, "label");
    }

    public static List<String> getName(String json) {
        return get(json, "name");
    }

    private static List<String> get(String json, String jsonStrElement) {
        JsonElement jsonElement = JsonParser.parseString(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        List<String> lst = new ArrayList<>();
        JsonArray jsonArray = jsonObject.getAsJsonArray("features").getAsJsonArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonElement j = jsonArray.get(i);
            lst.add(j.getAsJsonObject().getAsJsonObject("properties").get(jsonStrElement).getAsString());
        }
        return lst;
    }

    public static List<Location> getLocation(String json) {
        List<Location> lst = new ArrayList<>();
        JsonElement jsonElement = JsonParser.parseString(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray("features").getAsJsonArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            Location location = new Location();
            JsonArray jj = jsonArray.get(i).getAsJsonObject().getAsJsonObject("geometry").getAsJsonArray("coordinates");

            location.setLat(jj.get(0).getAsJsonPrimitive().getAsDouble());
            location.setLon(jj.get(1).getAsJsonPrimitive().getAsDouble());
            lst.add(location);
        }
        return lst;
    }


}
