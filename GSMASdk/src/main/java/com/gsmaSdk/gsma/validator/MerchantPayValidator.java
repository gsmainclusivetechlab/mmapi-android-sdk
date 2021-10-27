
package com.gsmaSdk.gsma.validator;

import com.google.gson.Gson;
import com.gsmaSdk.gsma.models.common.ErrorObject;
import com.gsmaSdk.gsma.models.common.ErrorParameter;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.transaction.TransactionRequest;

import java.util.ArrayList;

public class MerchantPayValidator {
    static ErrorObject gsmaError;
    static ArrayList<ErrorParameter> errorParameters;
    static String MANDATORY="This field is mandatory";

    public static ErrorObject checkMerchantPaymentValidation(TransactionRequest transactionRequest, String transactionType) {
        initialiaseMerchantPayValidator();
        if (transactionType != null) {
            validateField("type", transactionType, ValidationConstants.MANDATORY);
        } else {
            addError("type", MANDATORY);
        }
        if (transactionRequest.getAmount() != null) {
            validateField("amount", transactionRequest.getAmount(), ValidationConstants.MANDATORY);
        } else {
            addError("amount", MANDATORY);
        }
        if (transactionRequest.getCurrency() != null) {
            validateField("currency", transactionRequest.getCurrency()
                    , ValidationConstants.MANDATORY);
        } else {
            addError("currency", "This field is mandatory");
        }

        if (errorParameters.size() == 0) {
            return null;
        } else {
            return gsmaError;
        }
    }


    public static ErrorObject checkRefundValidation(TransactionRequest transactionRequest) {
        initialiaseMerchantPayValidator();
        if (transactionRequest.getAmount() != null) {
            validateField("amount", transactionRequest.getAmount(), ValidationConstants.MANDATORY);
        } else {
            addError("amount", MANDATORY);
        }
        if (transactionRequest.getCurrency() != null) {
            validateField("currency", transactionRequest.getCurrency()
                    , ValidationConstants.MANDATORY);
        } else {
            addError("currency", "This field is mandatory");
        }
        if (errorParameters.size() == 0) {
            return null;
        } else {
            return gsmaError;
        }
    }

    public static ErrorObject checkBalancePaymentValidation(String accountId){
        initialiaseMerchantPayValidator();
        if(accountId!=null){
            validateField("accountId",accountId,ValidationConstants.MANDATORY);
        }else{
            addError("accountId", MANDATORY);
        }
        if (errorParameters.size() == 0) {
            return null;
        } else {
            return gsmaError;
        }
    }

    public static ErrorObject checkRequestStatePaymentValidation(String serverCorrelationID){
        initialiaseMerchantPayValidator();
        if(serverCorrelationID!=null){
            validateField("serverCorrelationId",serverCorrelationID,ValidationConstants.MANDATORY);
        }else{
            addError("serverCorrelationId", MANDATORY);
        }
        if (errorParameters.size() == 0) {
            return null;
        } else {
            return gsmaError;
        }
    }

    public static ErrorObject checkViewTransactionValidation(String transactionReference){
        initialiaseMerchantPayValidator();
        if(transactionReference!=null){
            validateField("transactionReference",transactionReference,ValidationConstants.MANDATORY);
        }else{
            addError("transactionReference", MANDATORY);
        }
        if (errorParameters.size() == 0) {
            return null;
        } else {
            return gsmaError;
        }
    }

    public static void initialiaseMerchantPayValidator() {
        gsmaError = new ErrorObject("validation", "formatError", "Invalid JSON Field");
        errorParameters = new ArrayList<>();
        errorParameters.clear();
    }

    public static void validateField(String fieldName, String value, String validationType) {
        switch (validationType) {
            case ValidationConstants.MANDATORY:
                if (value.isEmpty()) {
                    addError(fieldName, MANDATORY);
                }
                break;


            default:
                throw new IllegalStateException("Unexpected value: " + validationType);
        }
    }

    public static void addError(String fieldName, String message) {
        errorParameters.add(new ErrorParameter(fieldName, message));
        gsmaError.setErrorParameterList(errorParameters);
    }
}
