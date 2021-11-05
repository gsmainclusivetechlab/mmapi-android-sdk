package com.gsmaSdk.gsma.interfaces;


import com.gsmaSdk.gsma.models.common.ErrorObject;

/**
 * Base interface
 * */
@SuppressWarnings("UnusedReturnValue")
public interface BaseInterface {

    void onValidationError(ErrorObject errorObject);
}
