package com.gsmaSdk.gsma.interfaces;

import com.gsmaSdk.gsma.models.Token;
import com.gsmaSdk.gsma.models.common.GSMAError;

/**
 * Interface for clients to receive Token creation response
 * */
public interface TokenInterface {

    void onTokenSuccess(Token token);

    void onTokenFailure(GSMAError gsmaError);
}
