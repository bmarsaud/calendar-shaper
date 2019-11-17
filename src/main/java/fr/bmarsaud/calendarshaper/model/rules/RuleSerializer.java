package fr.bmarsaud.calendarshaper.model.rules;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.apache.log4j.helpers.Transform;

import java.lang.reflect.Type;

import fr.bmarsaud.calendarshaper.model.transformations.Transformation;
import fr.bmarsaud.calendarshaper.model.transformations.TransformationSerializer;

public class RuleSerializer implements JsonSerializer<ShaperRule>, JsonDeserializer<ShaperRule> {
    private Gson gson;

    public RuleSerializer() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Transformation.class, new TransformationSerializer());
        gson = gsonBuilder.create();
    }

    @Override
    public JsonElement serialize(ShaperRule shaperRule, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject obj = gson.toJsonTree(shaperRule).getAsJsonObject();
        obj.addProperty("type", shaperRule.getClass().getSimpleName());

        return obj;
    }

    @Override
    public ShaperRule deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        String className = jsonElement.getAsJsonObject().get("type").getAsString();

        Class objClass = type.getClass();
        try {
            objClass = Class.forName(getClass().getPackageName() + "." + className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        JsonObject jsonObject = jsonElement.getAsJsonObject();
        jsonObject.remove("type");

        return (ShaperRule) gson.fromJson(jsonObject, objClass);
    }
}
