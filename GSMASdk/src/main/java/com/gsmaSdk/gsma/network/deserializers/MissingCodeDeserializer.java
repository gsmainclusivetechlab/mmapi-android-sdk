package com.gsmaSdk.gsma.network.deserializers;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.gsmaSdk.gsma.models.authorisationCode.AuthorisationCodes;
import com.gsmaSdk.gsma.models.authorisationCode.AuthorisationCode;

import java.lang.reflect.Type;
import java.util.List;

@SuppressWarnings("ALL")
public class MissingCodeDeserializer implements JsonDeserializer<AuthorisationCodes> {
    @Override
    public AuthorisationCodes deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<AuthorisationCode>>(){}.getType();
        List<AuthorisationCode> authorisationCodesItems = gson.fromJson(json,listType);
        AuthorisationCodes authorisationCode = new AuthorisationCodes();
        authorisationCode.setAuthCode(authorisationCodesItems);
        return authorisationCode;
    }
}
