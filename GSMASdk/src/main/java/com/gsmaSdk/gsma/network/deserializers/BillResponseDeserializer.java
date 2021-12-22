package com.gsmaSdk.gsma.network.deserializers;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.gsmaSdk.gsma.models.bills.Bill;
import com.gsmaSdk.gsma.models.bills.Bills;

import java.lang.reflect.Type;
import java.util.List;

public class BillResponseDeserializer  implements JsonDeserializer<Bills> {

    /**
     * Gson invokes this call-back method during deserialization when it encounters a field of the
     * specified type.
     * <p>In the implementation of this call-back method, you should consider invoking
     * {@link JsonDeserializationContext#deserialize(JsonElement, Type)} method to create objects
     * for any non-trivial field of the returned object. However, you should never invoke it on the
     * the same type passing {@code json} since that will cause an infinite loop (Gson will call your
     * call-back method again).
     *
     * @param json    The Json data being deserialized
     * @param typeOfT The type of the Object to deserialize to
     * @param context
     * @return a deserialized object of the specified type typeOfT which is a subclass of {@code T}
     * @throws JsonParseException if json is not in the expected format of {@code typeofT}
     */
    @Override
    public Bills deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        Gson gson = new Gson();
        Type listType = new TypeToken<List<Bill>>(){}.getType();
        List<Bill> billsListItems = gson.fromJson(json,listType);
        Bills bills = new Bills();
        bills.setBillList(billsListItems);
        return bills;

    }
}
