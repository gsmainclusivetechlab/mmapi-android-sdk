package com.gsmaSdk.gsma.network.deserializers;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.gsmaSdk.gsma.models.transaction.batchrejection.BatchTransactionRejection;
import com.gsmaSdk.gsma.models.transaction.batchrejection.BatchTransactionRejectionItem;

import java.lang.reflect.Type;
import java.util.List;

@SuppressWarnings("ALL")
public class BatchRejectionsDeserializer implements JsonDeserializer<BatchTransactionRejection> {
    @Override
    public BatchTransactionRejection deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        Gson gson = new Gson();
        Type listType = new TypeToken<List<BatchTransactionRejectionItem>>(){}.getType();
        List<BatchTransactionRejectionItem> transactionItems = gson.fromJson(json,listType);
        BatchTransactionRejection transaction = new BatchTransactionRejection();
        transaction.setBatchTransactionRejection(transactionItems);
        return transaction;
    }
}
