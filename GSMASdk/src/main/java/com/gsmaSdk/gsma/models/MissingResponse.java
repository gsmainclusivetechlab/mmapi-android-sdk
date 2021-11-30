package com.gsmaSdk.gsma.models;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.network.responses.BaseResponse;

import org.json.JSONObject;

public class MissingResponse extends BaseResponse {

    @SerializedName("data")
    private JsonObject jsonObject;

    public JsonObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
