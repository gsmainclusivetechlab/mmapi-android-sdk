package com.gsmaSdk.gsma.interfaces;


import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.transaction.Transaction;

/**
 * Interface for clients to retrieve Transaction
 * */
@SuppressWarnings("ALL")
public interface RetrieveTransactionInterface extends BaseInterface{

    void onRetrieveTransactionSuccess(Transaction transaction);

    void onRetrieveTransactionFailure(GSMAError gsmaError);
}
