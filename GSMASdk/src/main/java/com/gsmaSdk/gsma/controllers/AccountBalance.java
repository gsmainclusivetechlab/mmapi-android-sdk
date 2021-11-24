package com.gsmaSdk.gsma.controllers;

import androidx.annotation.NonNull;

import com.gsmaSdk.gsma.interfaces.BalanceInterface;
import com.gsmaSdk.gsma.models.Identifier;
import com.gsmaSdk.gsma.models.common.Balance;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.network.callbacks.APIRequestCallback;
import com.gsmaSdk.gsma.network.retrofit.GSMAApi;
import com.gsmaSdk.gsma.utils.Utils;

import java.util.ArrayList;

public class AccountBalance {

    /**
     * View Account Balance-Get the balance of a particular acccount
     *
     * @param identifierArrayList - List of identifierts to identify a particular account
     */

    public  void viewAccountBalance(@NonNull ArrayList<Identifier> identifierArrayList, @NonNull BalanceInterface balanceInterface) {
        if (!Utils.isOnline()) {
            balanceInterface.onValidationError(Utils.setError(0));
            return;
        }
        if (identifierArrayList == null) {
            balanceInterface.onValidationError(Utils.setError(1));
            return;
        } else if (identifierArrayList.size() != 0) {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().checkBalance(uuid, Utils.getIdentifiers(identifierArrayList), new APIRequestCallback<Balance>() {
                @Override
                public void onSuccess(int responseCode, Balance serializedResponse) {
                    balanceInterface.onBalanceSuccess(serializedResponse, uuid);
                }

                @Override
                public void onFailure(GSMAError errorDetails) {
                    balanceInterface.onBalanceFailure(errorDetails);
                }
            });

        } else {
            balanceInterface.onValidationError(Utils.setError(1));
        }
    }

    public static AccountBalance getInstance() {
        return AccountBalance.SingletonCreationAdmin.INSTANCE;
    }

    private static class SingletonCreationAdmin {
        private static final AccountBalance INSTANCE = new AccountBalance();
    }

}
