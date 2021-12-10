package com.gsmaSdk.gsma.interfaces;


import com.gsmaSdk.gsma.models.authorisationCode.AuthorisationCodes;
import com.gsmaSdk.gsma.models.common.GSMAError;

/**
 * Interface for clients to receive Authorisation codes
 * */
@SuppressWarnings("ALL")
public interface AuthorisationCodeInterface extends BaseInterface{

    void onAuthorisationCodeSuccess(AuthorisationCodes authorisationCode);

    void onAuthorisationCodeFailure(GSMAError gsmaError);
}
