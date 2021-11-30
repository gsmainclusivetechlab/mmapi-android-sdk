package com.gsmaSdk.gsma.controllers;

import androidx.annotation.NonNull;

import com.gsmaSdk.gsma.interfaces.AuthorisationCodeInterface;
import com.gsmaSdk.gsma.interfaces.AuthorisationCodeItemInterface;
import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.models.Identifier;
import com.gsmaSdk.gsma.models.authorisationCode.AuthorisationCodeItem;
import com.gsmaSdk.gsma.models.authorisationCode.AuthorisationCodeRequest;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.GetLink;
import com.gsmaSdk.gsma.models.common.RequestStateObject;
import com.gsmaSdk.gsma.network.callbacks.APIRequestCallback;
import com.gsmaSdk.gsma.network.retrofit.GSMAApi;
import com.gsmaSdk.gsma.utils.Utils;

import java.util.ArrayList;

@SuppressWarnings("ConstantConditions")
public class AuthorisationCode {


    /**
     * Obtain Authorisation code for a transaction
     *
     * @param identifierArrayList List of account identifiers of a user
     * @param codeRequest         An Object containing required details for getting the authorisation code
     */
    @SuppressWarnings("rawtypes")
    public void createAuthorisationCode(ArrayList<Identifier> identifierArrayList, @NonNull Enum notificationMethod, @NonNull String callbackUrl, @NonNull AuthorisationCodeRequest codeRequest, @NonNull RequestStateInterface requestStateInterface) {
        if (!Utils.isOnline()) {
            requestStateInterface.onValidationError(Utils.setError(0));
            return;
        }
        if (identifierArrayList == null) {
            requestStateInterface.onValidationError(Utils.setError(1));
        } else if (identifierArrayList.size() != 0) {
            String uuid = Utils.generateUUID();
            requestStateInterface.getCorrelationId(uuid);
            GSMAApi.getInstance().obtainAuthorisationCode(uuid, notificationMethod, callbackUrl, Utils.getIdentifiers(identifierArrayList), codeRequest, new APIRequestCallback<RequestStateObject>() {
                        @Override
                        public void onSuccess(int responseCode, RequestStateObject serializedResponse) {
                            requestStateInterface.onRequestStateSuccess(serializedResponse);
                        }

                        @Override
                        public void onFailure(GSMAError errorDetails) {
                            requestStateInterface.onRequestStateFailure(errorDetails);
                        }
                    }
            );

        } else {
            requestStateInterface.onValidationError(Utils.setError(1));
        }
    }



    /**
     * Retrieve Missing Code - To retrieve a missing Authorisation code
     *
     * @param correlationId UUID that enables the client to correlate the API request with the resource created/updated by the provider
     */

    public void viewAuthorisationCodeResponse(String correlationId, @NonNull AuthorisationCodeInterface authorisationCodeInterface) {
        if (!Utils.isOnline()) {
            authorisationCodeInterface.onValidationError(Utils.setError(0));
            return;
        }

        if (correlationId == null) {
            authorisationCodeInterface.onValidationError(Utils.setError(6));
            return;
        }
        if (correlationId.isEmpty()) {
            authorisationCodeInterface.onValidationError(Utils.setError(6));
        } else {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().retrieveMissingResponse(uuid, correlationId, new APIRequestCallback<GetLink>() {
                        @Override
                        public void onSuccess(int responseCode, GetLink serializedResponse) {

                            GSMAApi.getInstance().getMissingCodes(serializedResponse.getLink(), new APIRequestCallback<com.gsmaSdk.gsma.models.authorisationCode.AuthorisationCode>() {
                                @Override
                                public void onSuccess(int responseCode, com.gsmaSdk.gsma.models.authorisationCode.AuthorisationCode serializedResponse) {
                                    authorisationCodeInterface.onAuthorisationCodeSuccess(serializedResponse);
                                }

                                @Override
                                public void onFailure(GSMAError errorDetails) {
                                    authorisationCodeInterface.onAuthorisationCodeFailure(errorDetails);

                                }
                            });
                        }

                        @Override
                        public void onFailure(GSMAError errorDetails) {
                            authorisationCodeInterface.onAuthorisationCodeFailure(errorDetails);
                        }
                    }
            );
        }
    }


    /**
     * View Authorization Code
     *
     * @param identifierArrayList List of account identifiers of a user
     * @param authorisationCode Created Authorisation Code
     *
     */

    public void viewAuthorisationCode(@NonNull ArrayList<Identifier> identifierArrayList, String authorisationCode, AuthorisationCodeItemInterface authorisationCodeInterface) {
        if (!Utils.isOnline()) {
            authorisationCodeInterface.onValidationError(Utils.setError(0));
            return;
        }
        if (identifierArrayList == null) {
            authorisationCodeInterface.onValidationError(Utils.setError(1));
            return;
        }
        if (authorisationCode == null) {
            authorisationCodeInterface.onValidationError(Utils.setError(9));
        } else if (identifierArrayList.size() != 0) {
            String uuid = Utils.generateUUID();
            GSMAApi.getInstance().viewAuthorizationCode(uuid, Utils.getIdentifiers(identifierArrayList), authorisationCode, new APIRequestCallback<AuthorisationCodeItem>() {
                        @Override
                        public void onSuccess(int responseCode, AuthorisationCodeItem serializedResponse) {
                            authorisationCodeInterface.onAuthorisationCodeSuccess(serializedResponse);
                        }

                        @Override
                        public void onFailure(GSMAError errorDetails) {
                            authorisationCodeInterface.onAuthorisationCodeFailure(errorDetails);
                        }
                    }
            );

        } else {
            authorisationCodeInterface.onValidationError(Utils.setError(1));
        }

    }


    public static AuthorisationCode getInstance() {
        return AuthorisationCode.SingletonCreationAdmin.INSTANCE;
    }

    private static class SingletonCreationAdmin {
        private static final AuthorisationCode INSTANCE = new AuthorisationCode();
    }


}
