package com.gsmaSdk.gsma.interfaces;


import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.transaction.transactions.Transactions;

/**
 * Interface for clients to retrieve Transaction
 * */
@SuppressWarnings("ALL")
public interface RetrieveTransactionInterface extends BaseInterface{

    void onRetrieveTransactionSuccess(Transactions transaction);

    void onRetrieveTransactionFailure(GSMAError gsmaError);
}
