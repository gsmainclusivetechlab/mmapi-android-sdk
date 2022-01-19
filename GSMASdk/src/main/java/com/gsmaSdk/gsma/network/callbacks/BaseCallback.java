package com.gsmaSdk.gsma.network.callbacks;


import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.network.responses.BaseResponse;
import com.gsmaSdk.gsma.network.responses.MetaData;
import com.gsmaSdk.gsma.utils.Utils;

import java.io.IOException;
import java.util.Objects;

import androidx.annotation.NonNull;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Base callback with response handling
 *
 * @param <K> the type parameter
 */
public final class BaseCallback<K extends BaseResponse> implements Callback<K> {
    private static final String UNABLE_TO_FETCH_ERROR_INFO = "Unable to fetch error information";
    private final APIRequestCallback<K> requestCallback;

    /**
     * Instantiates a new Base callback.
     *
     * @param requestCallback the request callback
     */
    public BaseCallback(APIRequestCallback<K> requestCallback) {
        this.requestCallback = requestCallback;
    }

    @Override
    public void onResponse(@NonNull Call<K> call, @NonNull Response<K> response) {
        if (response.isSuccessful()) {
            if (response.headers().get("X-Records-Available-Count") != null || response.headers().get("X-Records-Returned-Count") != null) {
                MetaData metaData = new MetaData();
                if ((response.headers().get("X-Records-Available-Count") != null)) {
                    metaData.setAvailableCount(response.headers().get("X-Records-Available-Count"));
                }
                if (response.headers().get("X-Records-Returned-Count") != null) {
                    metaData.setTotalCount(response.headers().get("X-Records-Returned-Count"));
                }
                Objects.requireNonNull(response.body()).setMetaData(metaData);
            }

            requestCallback.onSuccess(response.code(), response.body());
        } else {
            ResponseBody errorBody = response.errorBody();

            if (errorBody != null) {
                try {
                    requestCallback.onFailure(new GSMAError(response.code(), Utils.parseError(errorBody.string()), null));
                } catch (IOException e) {
                    requestCallback.onFailure(new GSMAError(GSMAError.ERROR_CODE_UNAVAILABLE, null, e));
                }
            } else {
                requestCallback.onFailure(new GSMAError(response.code(), Utils.parseError(UNABLE_TO_FETCH_ERROR_INFO), null));
            }
        }
    }

    @Override
    public void onFailure(@NonNull Call<K> call, @NonNull Throwable t) {
        requestCallback.onFailure(new GSMAError(GSMAError.ERROR_CODE_UNAVAILABLE, null, t));
    }
}
