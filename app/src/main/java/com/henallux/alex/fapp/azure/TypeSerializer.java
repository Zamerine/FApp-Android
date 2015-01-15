package com.henallux.alex.fapp.azure;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;


public class TypeSerializer
        implements JsonSerializer<com.henallux.alex.fapp.model.Type>, JsonDeserializer<com.henallux.alex.fapp.model.Type> {

    @Override
    public JsonElement serialize(com.henallux.alex.fapp.model.Type value, Type type,
                                 JsonSerializationContext context) {
        String serialized = new Gson().toJson(value);
        return new JsonPrimitive(serialized);
    }

    @Override
    public com.henallux.alex.fapp.model.Type deserialize(JsonElement element, Type type,
                                                         JsonDeserializationContext context) throws JsonParseException {
        String serialized = element.getAsString();
        return new Gson().fromJson(serialized, com.henallux.alex.fapp.model.Type.class);
    }

}
