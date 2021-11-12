package com.gsmaSdk.gsma.network.responses;


import androidx.annotation.RestrictTo;

import com.google.gson.annotations.SerializedName;

/**
 * Marker for response classes
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public abstract class BaseResponse {

   private MetaData metaData;

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }
}
