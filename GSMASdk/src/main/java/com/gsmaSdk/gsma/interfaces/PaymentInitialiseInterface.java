package com.gsmaSdk.gsma.interfaces;

import com.gsmaSdk.gsma.models.Token;
import com.gsmaSdk.gsma.models.common.GSMAError;

public interface PaymentInitialiseInterface extends BaseInterface{

     void onSuccess(Token token);

     void onFailure(GSMAError gsmaError);
}
