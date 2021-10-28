package com.gsmaSdk.gsma.interfaces;


import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.MissingResponse;

/**
 * Interface for clients to receive missing response
 * */
public interface MissingResponseInterface extends BaseInterface{

    void onMissingResponseSuccess(MissingResponse missingResponse, String correlationID);

    void onMissingResponseFailure(GSMAError gsmaError);
}
