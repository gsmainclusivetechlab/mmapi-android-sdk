package com.gsmaSdk.gsma.interfaces;

import com.gsmaSdk.gsma.models.MissingResponse;
import com.gsmaSdk.gsma.models.common.GSMAError;

public interface MissingResponseInterface  extends BaseInterface{

    void onMissingResponseSuccess(MissingResponse missingResponse);

    void onMissingResponseFailure(GSMAError gsmaError);

}
