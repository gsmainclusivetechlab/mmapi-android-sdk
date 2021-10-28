package com.gsmaSdk.gsma.interfaces;

import com.gsmaSdk.gsma.models.Refund;
import com.gsmaSdk.gsma.models.common.GSMAError;

public interface RefundInterface  extends BaseInterface{

    void onRefundSuccess(Refund refund, String correlationId);

    void onRefundFailure(GSMAError gsmaError);
}
