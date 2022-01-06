package com.gsmaSdk.gsma.network.deserializers;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.gsmaSdk.gsma.models.transaction.batchcompletion.BatchCompletions;
import com.gsmaSdk.gsma.models.transaction.batchcompletion.BatchCompletion;

import java.lang.reflect.Type;
import java.util.List;

@SuppressWarnings("ALL")
public class BatchCompletionsDeserializer implements JsonDeserializer<BatchCompletions> {
    @Override
    public BatchCompletions deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        Gson gson = new Gson();
        Type listType = new TypeToken<List<BatchCompletion>>(){}.getType();
        List<BatchCompletion> transactionItems = gson.fromJson(json,listType);
        BatchCompletions transaction = new BatchCompletions();
        transaction.setBatchTransactionCompletion(transactionItems);
        return transaction;
    }
}
