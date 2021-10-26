
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

    public static ErrorObject checkMerchantPaymentValidation(TransactionRequest transactionRequest, String transactionType) {
        initialiaseMerchantPayValidator();
        if (transactionType != null) {
            validateField("type", transactionType, ValidationConstants.MANDATORY);
        } else {
            addError("amount", "This field is mandatory");
        }
        if(transactionRequest.getAmount()!=null){
            validateField("amount", transactionType, ValidationConstants.MANDATORY);
        }else{
            addError("amount", "This field is mandatory");
        }
       if(transactionRequest.getCurrency()!=null){
           validateField("amount", transactionType, ValidationConstants.MANDATORY);
       }else{
           addError("country", "This field is mandatory");

       }
       if(errorParameters.size()==0){
            return null;
        }else{
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
                    addError(fieldName, "This field is mandatory");
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
