package com.gsmaSdk.gsma.network.retrofit;

import android.util.Log;

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
@RestrictTo(RestrictTo.Scope.LIBRARY)
class RequestManager {
    private APIService apiHelper;
    //all requests are wrapped in DelayedRequest, until init() would be finished
    private ArrayList<DelayedRequest> delayedRequests;
    /**
     * Instantiates a new Request manager.
     *
     * @param apiHelper the api helper
     */
    RequestManager(APIService apiHelper) {
        this.apiHelper = apiHelper;
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
//            System.out.println("delayedRequest.toString() : " + delayedRequest.getRequest().request());
            try {
                final Buffer buffer = new Buffer();
                if (delayedRequest.getRequest().request().body() != null) {
                    delayedRequest.getRequest().request().body().writeTo(buffer);
//                System.out.println("delayedRequest.toString() :" + buffer.readUtf8().toString());
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
    static class DelayedRequest<T extends BaseResponse> {
        private Call<T> request;
        private APIRequestCallback<T> requestCallback;
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
            Log.d("Request Url", "****  "+request.request().url() + "");
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
