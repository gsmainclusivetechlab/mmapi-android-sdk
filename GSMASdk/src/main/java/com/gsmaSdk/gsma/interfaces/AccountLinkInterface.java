package com.gsmaSdk.gsma.interfaces;


import com.gsmaSdk.gsma.models.account.Link;
import com.gsmaSdk.gsma.models.common.GSMAError;

/**
 * Interface for clients to view account links
 * */
@SuppressWarnings("ALL")
public interface AccountLinkInterface extends BaseInterface{

    void onAccountLinkSuccess(Link accountLinks);

    void onAccountLinkFailure(GSMAError gsmaError);
}
