package com.gsmaSdk.gsma.interfaces;


import com.gsmaSdk.gsma.models.account.AccountLinks;
import com.gsmaSdk.gsma.models.common.GSMAError;

/**
 * Interface for clients to view account links
 * */
@SuppressWarnings("ALL")
public interface AccountLinkInterface extends BaseInterface{

    void onAccountLinkSuccess(AccountLinks accountLinks);

    void onAccountLinkFailure(GSMAError gsmaError);
}
