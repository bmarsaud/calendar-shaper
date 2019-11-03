package fr.bmarsaud.calendarshaper.model.rules;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class RuleSerializer implements JsonSerializer<CalendarRule>, JsonDeserializer<CalendarRule> {
    private Gson gson;

    public RuleSerializer() {
        gson = new Gson();
    }

    @Override
    public JsonElement serialize(CalendarRule calendarRule, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject obj = new JsonObject();
        obj.addProperty("type", calendarRule.getClass().getSimpleName().replaceAll("Rule", ""));

        JsonObject params = gson.toJsonTree(calendarRule).getAsJsonObject();
        obj.add("params", params);

        return obj;
    }

    @Override
    public CalendarRule deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        String className = jsonElement.getAsJsonObject().get("type").getAsString();
        className = className.substring(0,1).toUpperCase() + className.substring(1) + "Rule";

        Class objClass = type.getClass();
        try {
            objClass = Class.forName(getClass().getPackageName() + "." + className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        JsonObject jsonObject = jsonElement.getAsJsonObject().get("params").getAsJsonObject();
        return (CalendarRule) gson.fromJson(jsonObject, objClass);
    }
}
