package com.gsmaSdk.gsma.interfaces;

import com.gsmaSdk.gsma.models.DebitMandate;
import com.gsmaSdk.gsma.models.common.GSMAError;

public interface DebitMandateInterface extends BaseInterface {

    void onDebitMandateSuccess(DebitMandate debitMandate);

    void onDebitMandateFailure(GSMAError gsmaError);
}
