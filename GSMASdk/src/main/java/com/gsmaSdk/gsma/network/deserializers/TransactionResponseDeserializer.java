package com.gsmaSdk.gsma.network.deserializers;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.gsmaSdk.gsma.models.transaction.transactions.Transactions;
import com.gsmaSdk.gsma.models.transaction.transactions.Transaction;

import java.lang.reflect.Type;
import java.util.List;

@SuppressWarnings("ALL")
public class TransactionResponseDeserializer implements JsonDeserializer<Transactions> {

    @Override
    public Transactions deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Transaction>>(){}.getType();
        List<Transaction> transactionItems = gson.fromJson(json,listType);
        Transactions transaction = new Transactions();
        transaction.setTransaction(transactionItems);
        return transaction;
    }
}
