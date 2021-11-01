package com.gsmaSdk.gsma.interfaces;


import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.transaction.BatchTransactionRejection;

/**
 * Interface for clients to receive rejected batch transactions
 * */
public interface BatchRejectionInterface extends BaseInterface {

    void batchTransactionRejections(BatchTransactionRejection batchTransactionRejection, String correlationId);

    void onTransactionFailure(GSMAError gsmaError);
}
