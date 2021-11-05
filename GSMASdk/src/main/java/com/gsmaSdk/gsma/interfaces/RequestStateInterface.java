package com.gsmaSdk.gsma.interfaces;


import com.gsmaSdk.gsma.models.common.RequestStateObject;
import com.gsmaSdk.gsma.models.common.GSMAError;

/**
 * Interface for clients to receive Merchant Payment response
 * */
@SuppressWarnings("ALL")
public interface RequestStateInterface extends BaseInterface{

    void onRequestStateSuccess(RequestStateObject requestStateObject,String correlationId);

    void onRequestStateFailure(GSMAError gsmaError);
}
