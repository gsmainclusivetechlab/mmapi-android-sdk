package com.gsmaSdk.gsma.interfaces;

import com.gsmaSdk.gsma.models.common.Token;
import com.gsmaSdk.gsma.models.common.GSMAError;

@SuppressWarnings("ALL")
public interface PaymentInitialiseInterface extends BaseInterface{

     @SuppressWarnings("UnusedReturnValue")
     void onSuccess(Token token);

     @SuppressWarnings("UnusedReturnValue")
     void onFailure(GSMAError gsmaError);
}
