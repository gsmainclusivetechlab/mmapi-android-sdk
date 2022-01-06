package com.gsmaSdk.gsma.interfaces;


import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.transaction.batchcompletion.BatchCompletions;

/**
 * Interface for clients to receive completed batch transactions
 * */
@SuppressWarnings("ALL")
public interface BatchCompletionInterface extends BaseInterface {

    void batchTransactionCompleted(BatchCompletions batchTransactionCompletion);

    void onTransactionFailure(GSMAError gsmaError);
}
