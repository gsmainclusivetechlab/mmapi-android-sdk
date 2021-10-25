package com.gsmaSdk.gsma.network.callbacks;

import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.network.responses.BaseResponse;

import androidx.annotation.RestrictTo;

/**
 * Base callback to process API responses
 *
 * @param <T> {@link Class} implementing {@link BaseResponse} interface
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public interface APIRequestCallback<T extends BaseResponse> {
    /**
     * Success callback. Request is considered as successful when response code is between 200 and 299
     *
     * @param responseCode       Response code, from 200 to 299
     * @param serializedResponse Serialized response of {@link T} type or null in case when response could not be serialized into {@link T} type
     */
    void onSuccess(@SuppressWarnings("unused") int responseCode, T serializedResponse);

    /**
     * General failure callback
     *
     * @param errorDetails {@link GSMAError} representing a failure reason
     */
    void onFailure(GSMAError errorDetails);
}