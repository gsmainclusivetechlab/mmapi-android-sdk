package com.gsmaSdk.gsma.interfaces;


import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.ServiceAvailability;

/**
 * Interface for clients to receive Service Availability response
 * */
public interface ServiceAvailabilityInterface extends BaseInterface{

    void onServiceAvailabilitySuccess(ServiceAvailability serviceAvailability);

    void onServiceAvailabilityFailure(GSMAError gsmaError);
}
