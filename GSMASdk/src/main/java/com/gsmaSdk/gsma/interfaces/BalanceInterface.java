package com.gsmaSdk.gsma.interfaces;


import com.gsmaSdk.gsma.models.common.Balance;
import com.gsmaSdk.gsma.models.common.GSMAError;

/**
 * Interface for clients to receive Account Balance response
 * */
@SuppressWarnings("ALL")
public interface BalanceInterface extends BaseInterface{

    void onBalanceSuccess(Balance balance);

    void onBalanceFailure(GSMAError gsmaError);
}
