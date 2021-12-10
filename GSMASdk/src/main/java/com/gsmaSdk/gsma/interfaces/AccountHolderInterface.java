package com.gsmaSdk.gsma.interfaces;


import com.gsmaSdk.gsma.models.account.AccountHolderName;
import com.gsmaSdk.gsma.models.common.GSMAError;

/**
 * Interface for clients to receive Account Holder information
 * */
@SuppressWarnings("ALL")
public interface AccountHolderInterface extends BaseInterface{

    void onRetrieveAccountInfoSuccess(AccountHolderName accountHolderObject);

    void onRetrieveAccountInfoFailure(GSMAError gsmaError);
}
