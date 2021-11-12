package com.gsmaSdk.gsma.interfaces;


import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.transaction.TransactionRequest;

/**
 * Interface for clients to receive Transaction Response
 * */
@SuppressWarnings("ALL")
public interface TransactionInterface extends BaseInterface{

    void onTransactionSuccess(TransactionRequest transactionObject, String correlationId);

    void onTransactionFailure(GSMAError gsmaError);
}
