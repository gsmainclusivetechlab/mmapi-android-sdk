package com.gsmaSdk.gsma.network.deserializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.gsmaSdk.gsma.models.MissingResponse;

import java.lang.reflect.Type;

public class MissingResponseDeserializer implements JsonDeserializer<MissingResponse> {

    @Override
    public MissingResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject=json.getAsJsonObject();
        MissingResponse missingResponse=new MissingResponse();
        missingResponse.setJsonObject(jsonObject.getAsJsonObject());
        return missingResponse;
    }
}
