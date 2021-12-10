package com.gsmaSdk.gsma.network.deserializers;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.gsmaSdk.gsma.models.transaction.batchcompletion.BatchCompletion;
import com.gsmaSdk.gsma.models.transaction.batchcompletion.BatchCompletionItem;

import java.lang.reflect.Type;
import java.util.List;

@SuppressWarnings("ALL")
public class BatchCompletionsDeserializer implements JsonDeserializer<BatchCompletion> {
    @Override
    public BatchCompletion deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        Gson gson = new Gson();
        Type listType = new TypeToken<List<BatchCompletionItem>>(){}.getType();
        List<BatchCompletionItem> transactionItems = gson.fromJson(json,listType);
        BatchCompletion transaction = new BatchCompletion();
        transaction.setBatchTransactionCompletion(transactionItems);
        return transaction;
    }
}
