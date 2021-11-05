package com.gsmaSdk.gsma.interfaces;

import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.transaction.BatchTransactionItem;

@SuppressWarnings("ALL")
public interface BatchTransactionItemInterface extends BaseInterface {

    void batchTransactionSuccess(BatchTransactionItem batchTransactionItem, String correlationId);

    void onTransactionFailure(GSMAError gsmaError);
}
