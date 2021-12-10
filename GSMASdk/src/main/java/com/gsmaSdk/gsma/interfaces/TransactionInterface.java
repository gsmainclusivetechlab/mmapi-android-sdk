package com.gsmaSdk.gsma.interfaces;


import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.transaction.transactions.Transaction;

/**
 * Interface for clients to receive Transaction Response
 * */
@SuppressWarnings("ALL")
public interface TransactionInterface extends BaseInterface{

    void onTransactionSuccess(Transaction transactionObject);

    void onTransactionFailure(GSMAError gsmaError);
}
