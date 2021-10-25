package com.gsmaSdk.gsma.enums;

import com.google.gson.annotations.SerializedName;

/**
 * The enum Authentication type - different types of Authentications provided.
 */
@SuppressWarnings("unused")
public enum AuthenticationType {

    /**
     * Basic type.
     */
    @SerializedName("NO_AUTH")         NO_AUTH,
    /**
     * Development level Authentication
     */
    @SerializedName("DEVELOPMENT_LEVEL")      DEVELOPMENT_LEVEL,
    /**
     * Standard level Authentication
     */
    @SerializedName("STANDARD_LEVEL")   STANDARD_LEVEL,
    /**
     * Enhanced level Authentication
            */
    @SerializedName("ENHANCED_LEVEL")   ENHANCED_LEVEL
}
