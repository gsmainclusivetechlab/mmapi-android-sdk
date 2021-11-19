package com.gsmaSdk.gsma.interfaces;


import com.gsmaSdk.gsma.models.AccountHolderObject;
import com.gsmaSdk.gsma.models.common.GSMAError;

/**
 * Interface for clients to receive Account Holder information
 * */
@SuppressWarnings("ALL")
public interface AccountHolderInterface extends BaseInterface{

    void onRetrieveAccountInfoSuccess(AccountHolderObject accountHolderObject, String correlationID);

    void onRetrieveAccountInfoFailure(GSMAError gsmaError);
}
