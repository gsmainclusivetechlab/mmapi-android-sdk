package com.gsmaSdk.gsma.network.retrofit;

import com.gsmaSdk.gsma.network.callbacks.APIRequestCallback;
import com.gsmaSdk.gsma.network.callbacks.BaseCallback;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.network.responses.BaseResponse;

import java.io.IOException;
import java.util.ArrayList;

import androidx.annotation.RestrictTo;
import okio.Buffer;
import retrofit2.Call;

/**
 * The type Request manager - for managing Requests.
 */
@SuppressWarnings({"ALL", "rawtypes"})
@RestrictTo(RestrictTo.Scope.LIBRARY)
class RequestManager {

    //all requests are wrapped in DelayedRequest, until init() would be finished
    private final ArrayList<DelayedRequest> delayedRequests;
    /**
     * Instantiates a new Request manager.
     *
     * @param apiHelper the api helper
     */
    RequestManager() {
        delayedRequests = new ArrayList<>();
    }
    /**
     * Request.
     *
     * @param delayedRequest the delayed request
     */
    void request(DelayedRequest delayedRequest) {
        delayedRequests.add(delayedRequest);
                init();
    }

    private void init() {
        runDelayedRequests();
    }

    private void runDelayedRequests() {
        for (DelayedRequest delayedRequest : delayedRequests) {
            try {
                final Buffer buffer = new Buffer();
                if (delayedRequest.getRequest().request().body() != null) {
                    delayedRequest.getRequest().request().body().writeTo(buffer);
                }
            } catch (IOException s) {
                System.out.println("ex : " + s.getLocalizedMessage());
            }
            delayedRequest.run();
        }
        delayedRequests.clear();
    }
    private void failDelayedRequests(GSMAError errorDetails) {
        for (DelayedRequest delayedRequest : delayedRequests) {
            delayedRequest.fail(errorDetails);
        }
        delayedRequests.clear();
    }
    /**
     * The type Delayed request.
     *
     * @param <T> the type parameter
     */
    @SuppressWarnings("rawtypes")
    static class DelayedRequest<T extends BaseResponse> {
        private final Call<T> request;
        private final APIRequestCallback<T> requestCallback;
        /**
         * Instantiates a new Delayed request.
         *
         * @param request         the request
         * @param requestCallback the request callback
         */
        DelayedRequest(Call<T> request, APIRequestCallback<T> requestCallback) {
            this.request = request;
            this.requestCallback = requestCallback;
        }
        /**
         * Run.
         */
        void run() {
            request.enqueue(new BaseCallback<>(requestCallback));
        }
        /**
         * Fail.
         *
         * @param errorDetails the error details
         */
        void fail(GSMAError errorDetails) {

            requestCallback.onFailure(errorDetails);
        }

        /**
         * Get request call.
         *
         * @return the call
         */
        Call getRequest() {
            return this.request;
        }
    }
}
