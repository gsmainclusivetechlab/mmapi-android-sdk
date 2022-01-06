package com.gsmaSdk.gsma.network.deserializers;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.gsmaSdk.gsma.models.transaction.batchrejection.BatchRejections;
import com.gsmaSdk.gsma.models.transaction.batchrejection.BatchRejection;

import java.lang.reflect.Type;
import java.util.List;

@SuppressWarnings("ALL")
public class BatchRejectionsDeserializer implements JsonDeserializer<BatchRejections> {
    @Override
    public BatchRejections deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        Gson gson = new Gson();
        Type listType = new TypeToken<List<BatchRejection>>(){}.getType();
        List<BatchRejection> transactionItems = gson.fromJson(json,listType);
        BatchRejections transaction = new BatchRejections();
        transaction.setBatchTransactionRejection(transactionItems);
        return transaction;
    }
}
