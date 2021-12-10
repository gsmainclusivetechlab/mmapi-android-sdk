package com.gsmaSdk.gsma.controllers;

import androidx.annotation.NonNull;

import com.gsmaSdk.gsma.interfaces.AccountHolderInterface;
import com.gsmaSdk.gsma.models.account.AccountHolderName;
import com.gsmaSdk.gsma.models.account.Identifier;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.network.callbacks.APIRequestCallback;
import com.gsmaSdk.gsma.network.retrofit.GSMAApi;
import com.gsmaSdk.gsma.utils.Utils;

import java.util.ArrayList;


public class AccountNameController {


    /**
     * View Account Name-Get name of  particular account holder
     *
     * @param identifierArrayList account identifiers of the user
     */

   public void viewAccountName(ArrayList<Identifier> identifierArrayList, @NonNull AccountHolderInterface accountHolderInterface) {
        if (!Utils.isOnline()) {
            accountHolderInterface.onValidationError(Utils.setError(0));
            return;
        }

        if (identifierArrayList == null) {
            accountHolderInterface.onValidationError(Utils.setError(1));
        } else if (identifierArrayList.size() != 0) {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().viewAccountName(uuid, Utils.getIdentifiers(identifierArrayList), new APIRequestCallback<AccountHolderName>() {
                @Override
                public void onSuccess(int responseCode, AccountHolderName serializedResponse) {
                    accountHolderInterface.onRetrieveAccountInfoSuccess(serializedResponse);
                }

                @Override
                public void onFailure(GSMAError errorDetails) {
                    accountHolderInterface.onRetrieveAccountInfoFailure(errorDetails);
                }
            });

        } else {
            accountHolderInterface.onValidationError(Utils.setError(1));
        }
    }

    public static AccountNameController getInstance() {
        return SingletonCreationAdmin.INSTANCE;
    }

    private static class SingletonCreationAdmin {
        private static final AccountNameController INSTANCE = new AccountNameController();
    }


}
