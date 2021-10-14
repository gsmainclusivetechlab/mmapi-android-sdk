package com.gsmaSdk.gsma.interfaces;


import com.gsmaSdk.gsma.models.RequestStateObject;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.transaction.TransactionObject;

/**
 * Interface for clients to receive Transaction Response
 * */
public interface TransactionInterface extends BaseInterface{

    void onTransactionSuccess(TransactionObject transactionObject);

    void onTransactionFailure(GSMAError gsmaError);
}
