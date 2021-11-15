package com.gsmaSdk.gsma.interfaces;


import com.gsmaSdk.gsma.models.authorisationCode.AuthorisationCode;
import com.gsmaSdk.gsma.models.authorisationCode.AuthorisationCodeItem;
import com.gsmaSdk.gsma.models.common.GSMAError;

/**
 * Interface for clients to receive Authorisation codes
 * */
@SuppressWarnings("ALL")
public interface AuthorisationCodeItemInterface extends BaseInterface{

    void onAuthorisationCodeSuccess(AuthorisationCodeItem authorisationCodeItem, String correlationId);

    void onAuthorisationCodeFailure(GSMAError gsmaError);
}
