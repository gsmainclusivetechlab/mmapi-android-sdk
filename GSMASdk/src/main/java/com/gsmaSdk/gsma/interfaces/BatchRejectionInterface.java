package com.gsmaSdk.gsma.interfaces;


import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.transaction.batchrejection.BatchTransactionRejection;

/**
 * Interface for clients to receive rejected batch transactions
 * */
public interface BatchRejectionInterface extends BaseInterface {

    @SuppressWarnings("unused")
    void batchTransactionRejections(BatchTransactionRejection batchTransactionRejection);

    @SuppressWarnings("unused")
    void onTransactionFailure(GSMAError gsmaError);
}
