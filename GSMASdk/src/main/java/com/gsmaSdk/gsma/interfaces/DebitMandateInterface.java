package com.gsmaSdk.gsma.interfaces;

import com.gsmaSdk.gsma.models.DebitMandate;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.RequestStateObject;

public interface DebitMandateInterface extends BaseInterface {

    void onDebitMandateSuccess(DebitMandate debitMandate);

    void onDebitMandateFailure(GSMAError gsmaError);
}
