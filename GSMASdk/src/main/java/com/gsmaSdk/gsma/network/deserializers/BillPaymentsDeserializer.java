package com.gsmaSdk.gsma.network.deserializers;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.gsmaSdk.gsma.models.bills.BillPay;
import com.gsmaSdk.gsma.models.bills.BillPayments;

import java.lang.reflect.Type;
import java.util.List;

public class BillPaymentsDeserializer implements JsonDeserializer<BillPayments> {

    @Override
    public BillPayments deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        Gson gson = new Gson();
        Type listType = new TypeToken<List<BillPay>>(){}.getType();
        List<BillPay> billPaymentItems = gson.fromJson(json,listType);
        BillPayments billPayments = new BillPayments();
        billPayments.setBillPayments(billPaymentItems);
        return billPayments;

    }
}
