package com.gsmaSdk.gsma.interfaces;

import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.transaction.batchtransaction.BatchTransaction;

@SuppressWarnings("ALL")
public interface BatchTransactionItemInterface extends BaseInterface {

    void batchTransactionSuccess(BatchTransaction batchTransactionItem);

    void onTransactionFailure(GSMAError gsmaError);
}
