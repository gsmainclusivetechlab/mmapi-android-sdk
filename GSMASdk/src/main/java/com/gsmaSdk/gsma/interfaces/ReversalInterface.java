package com.gsmaSdk.gsma.interfaces;

import com.gsmaSdk.gsma.models.RequestStateObject;
import com.gsmaSdk.gsma.models.Reversal;
import com.gsmaSdk.gsma.models.common.GSMAError;

public interface ReversalInterface extends  BaseInterface{

    void onReversalSuccess(Reversal reversal);

    void onReversalFailure(GSMAError gsmaError);
}
