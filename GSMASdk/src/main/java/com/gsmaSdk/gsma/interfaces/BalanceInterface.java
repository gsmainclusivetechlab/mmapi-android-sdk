package com.gsmaSdk.gsma.interfaces;


import com.gsmaSdk.gsma.models.Balance;
import com.gsmaSdk.gsma.models.common.GSMAError;

/**
 * Interface for clients to receive Account Balance response
 * */
@SuppressWarnings("unused")
public interface BalanceInterface extends BaseInterface{

    void onBalanceSuccess(Balance balance, String correlationId);

    void onBalanceFailure(GSMAError gsmaError);
}
