package com.gsmaSdk.gsma.enums;

import com.google.gson.annotations.SerializedName;

/**
 * The enum Notification Method - different types of Notification methods provided.
 */
@SuppressWarnings("unused")
public enum NotificationMethod {

    /**
     * Callback notification method.
     */
    @SerializedName("callback")         CALLBACK,
    /**
     * Polling method
            */
    @SerializedName("polling")   POLLING
}
