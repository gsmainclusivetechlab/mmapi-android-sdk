package com.gsmaSdk.gsma.controllers;

import androidx.annotation.NonNull;

import com.gsmaSdk.gsma.interfaces.BalanceInterface;
import com.gsmaSdk.gsma.models.account.Identifier;
import com.gsmaSdk.gsma.models.account.Balance;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.network.callbacks.APIRequestCallback;
import com.gsmaSdk.gsma.network.retrofit.GSMAApi;
import com.gsmaSdk.gsma.utils.Utils;

import java.util.ArrayList;


public class AccountBalanceController {

    /**
     * View Account Balance-Get the balance of a particular account
     *
     * @param identifierArrayList - List of identifiers to identify a particular account
     */
    public  void viewAccountBalance(ArrayList<Identifier> identifierArrayList, @NonNull BalanceInterface balanceInterface) {
        if (identifierArrayList == null) {
            balanceInterface.onValidationError(Utils.setError(1));
            return;
        }
        else if(identifierArrayList.size()==0){
            balanceInterface.onValidationError(Utils.setError(1));
            return;
        }
        else if (!Utils.isOnline()) {
            balanceInterface.onValidationError(Utils.setError(0));
            return;
        }else if (identifierArrayList.size() != 0) {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().checkBalance(uuid, Utils.getIdentifiers(identifierArrayList), new APIRequestCallback<Balance>() {
                @Override
                public void onSuccess(int responseCode, Balance serializedResponse) {
                    balanceInterface.onBalanceSuccess(serializedResponse);
                }

                @Override
                public void onFailure(GSMAError errorDetails) {
                    balanceInterface.onBalanceFailure(errorDetails);
                }
            });

        }
    }

    public static AccountBalanceController getInstance() {
        return AccountBalanceController.SingletonCreationAdmin.INSTANCE;
    }

    private static class SingletonCreationAdmin {
        private static final AccountBalanceController INSTANCE = new AccountBalanceController();
    }

}
