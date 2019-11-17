package fr.bmarsaud.calendarshaper.model.transformations;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class TransformationSerializer implements JsonSerializer<Transformation>, JsonDeserializer<Transformation> {
    private Gson gson;

    public TransformationSerializer() {
        this.gson = new Gson();
    }

    @Override
    public JsonElement serialize(Transformation transformation, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject obj = new JsonObject();
        obj.addProperty("type", transformation.getClass().getSimpleName().replaceAll("Transformation", ""));

        JsonObject params = gson.toJsonTree(transformation).getAsJsonObject();
        obj.add("params", params);

        return obj;
    }

    @Override
    public Transformation deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        String className = jsonElement.getAsJsonObject().get("type").getAsString();
        className = className.substring(0,1).toUpperCase() + className.substring(1) + "Transformation";

        Class objClass = type.getClass();
        try {
            objClass = Class.forName(getClass().getPackageName() + "." + className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        JsonObject jsonObject = jsonElement.getAsJsonObject().get("params").getAsJsonObject();
        return (Transformation) gson.fromJson(jsonObject, objClass);
    }
}
