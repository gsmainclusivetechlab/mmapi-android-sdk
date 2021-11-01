package com.gsmaSdk.gsma.interfaces;


import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.transaction.Transaction;

/**
 * Interface for clients to retrieve Transaction
 * */
public interface RetrieveTransactionInterface extends BaseInterface{

    void onRetrieveTransactionSuccess(Transaction transaction, String correlationId);

    void onRetrieveTransactionFailure(GSMAError gsmaError);
}
