package com.gsmaSdk.gsma.interfaces;


import com.gsmaSdk.gsma.models.RequestStateObject;
import com.gsmaSdk.gsma.models.common.GSMAError;

/**
 * Interface for clients to receive Merchant Payment response
 * */
public interface RequestStateInterface extends BaseInterface{

    void onRequestStateSuccess(RequestStateObject requestStateObject);

    void onRequestStateFailure(GSMAError gsmaError);
}
