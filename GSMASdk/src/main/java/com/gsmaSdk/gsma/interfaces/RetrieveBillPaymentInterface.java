package com.gsmaSdk.gsma.interfaces;

import com.gsmaSdk.gsma.models.bills.Bills;
import com.gsmaSdk.gsma.models.common.GSMAError;

public interface RetrieveBillPaymentInterface extends BaseInterface {

    void onRetrieveBillPaymentSuccess(Bills bills);

    void onRetrieveBillPaymentFailure(GSMAError gsmaError);
}
