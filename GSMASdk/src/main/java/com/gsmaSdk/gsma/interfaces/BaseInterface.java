package com.gsmaSdk.gsma.interfaces;


import com.gsmaSdk.gsma.models.common.ErrorObject;

/**
 * Base interface
 * */
public interface BaseInterface {

    void onValidationError(ErrorObject errorObject);
}
