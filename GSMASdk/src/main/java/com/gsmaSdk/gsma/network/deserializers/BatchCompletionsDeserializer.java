package com.gsmaSdk.gsma.network.deserializers;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.gsmaSdk.gsma.models.transaction.BatchTransactionCompletion;
import com.gsmaSdk.gsma.models.transaction.BatchTransactionCompletionItem;

import java.lang.reflect.Type;
import java.util.List;

@SuppressWarnings("ALL")
public class BatchCompletionsDeserializer implements JsonDeserializer<BatchTransactionCompletion> {
    @Override
    public BatchTransactionCompletion deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        Gson gson = new Gson();
        Type listType = new TypeToken<List<BatchTransactionCompletionItem>>(){}.getType();
        List<BatchTransactionCompletionItem> transactionItems = gson.fromJson(json,listType);
        BatchTransactionCompletion transaction = new BatchTransactionCompletion();
        transaction.setBatchTransactionCompletion(transactionItems);
        return transaction;
    }
}
