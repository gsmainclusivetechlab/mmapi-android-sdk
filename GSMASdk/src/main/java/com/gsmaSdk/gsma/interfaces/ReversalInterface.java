package com.gsmaSdk.gsma.interfaces;

import com.gsmaSdk.gsma.models.Reversal;
import com.gsmaSdk.gsma.models.common.GSMAError;

public interface ReversalInterface extends  BaseInterface{

    void onReversalSuccess(Reversal reversal,String correlationId);

    void onReversalFailure(GSMAError gsmaError);
}
