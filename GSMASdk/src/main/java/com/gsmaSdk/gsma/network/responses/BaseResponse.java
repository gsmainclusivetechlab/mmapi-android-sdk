package com.gsmaSdk.gsma.network.responses;


import androidx.annotation.RestrictTo;

/**
 * Marker for response classes
 */
@SuppressWarnings("unused")
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
