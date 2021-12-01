package com.gsmaSdk.gsma.interfaces;


import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.transaction.BatchTransactionCompletion;

/**
 * Interface for clients to receive completed batch transactions
 * */
@SuppressWarnings("ALL")
public interface BatchCompletionInterface extends BaseInterface {

    void batchTransactionCompleted(BatchTransactionCompletion batchTransactionCompletion);

    void onTransactionFailure(GSMAError gsmaError);
}
