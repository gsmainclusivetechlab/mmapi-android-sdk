package com.gsmaSdk.gsma.interfaces;

import android.service.autofill.Validator;

import com.gsmaSdk.gsma.models.bills.Bills;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.transaction.transactions.Transactions;

public interface RetrieveBillPaymentInterface extends BaseInterface {

    void onRetrieveBillPaymentSuccess(Bills bills);

    void onRetrieveBillPaymentFailure(GSMAError gsmaError);
}
