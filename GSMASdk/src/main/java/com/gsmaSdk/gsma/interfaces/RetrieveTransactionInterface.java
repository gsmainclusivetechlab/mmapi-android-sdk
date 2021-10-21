package com.gsmaSdk.gsma.interfaces;


import com.google.gson.JsonElement;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.transaction.Transaction;
import com.gsmaSdk.gsma.models.transaction.TransactionItem;

import java.util.List;

/**
 * Interface for clients to retrieve Transaction
 * */
public interface RetrieveTransactionInterface extends BaseInterface{

    void onRetrieveTransactionSuccess(Transaction transaction);

    void onRetrieveTransactionFailure(GSMAError gsmaError);
}
