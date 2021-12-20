
package com.gsmaSdk.gsma.manager;

import android.content.Context;

import com.gsmaSdk.gsma.controllers.AccountLinkingController;
import com.gsmaSdk.gsma.controllers.BillPaymentController;
import com.gsmaSdk.gsma.controllers.Disbursement;
import com.gsmaSdk.gsma.controllers.InternationalTransfer;
import com.gsmaSdk.gsma.controllers.MerchantPayment;
import com.gsmaSdk.gsma.controllers.P2PTransfer;
import com.gsmaSdk.gsma.controllers.RecurringPayment;
import com.gsmaSdk.gsma.enums.AuthenticationType;
import com.gsmaSdk.gsma.interfaces.PaymentInitialiseInterface;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.Token;
import com.gsmaSdk.gsma.network.callbacks.APIRequestCallback;
import com.gsmaSdk.gsma.network.retrofit.GSMAApi;
import com.gsmaSdk.gsma.network.retrofit.PaymentConfiguration;
import com.gsmaSdk.gsma.utils.Utils;

/**
 * Class for managing sdk function calls
 */

@SuppressWarnings("ALL")
public class SDKManager {
    /**
     * Singleton getInstance method
     *
     * @return the instance
     */

    public static MerchantPayment merchantPayment=new MerchantPayment();
    public static Disbursement disbursement=new Disbursement();
    public static InternationalTransfer internationalTransfer=new InternationalTransfer();
    public static P2PTransfer p2PTransfer=new P2PTransfer();
    public static RecurringPayment recurringPayment=new RecurringPayment();
    public static AccountLinkingController accountLinking=new AccountLinkingController();
    public static BillPaymentController billPayment=new BillPaymentController();

    private SDKManager() {
    }

    public static SDKManager getInstance() {
        return SingletonCreationAdmin.INSTANCE;
    }

    private static class SingletonCreationAdmin {
        private static final SDKManager INSTANCE = new SDKManager();
    }

    /**
     * Level of authentication check,if the level is NO_AUTH then access token is not generated
     */
    public void init(Context context, PaymentInitialiseInterface paymentInitialiseInterface) {
        if (PaymentConfiguration.getAuthType() != AuthenticationType.NO_AUTH) {
            if (Utils.isOnline()) {
                GSMAApi.getInstance().createToken(new APIRequestCallback<Token>() {
                                                      @Override
                                                      public void onSuccess(int responseCode, Token serializedResponse) {
                                                          paymentInitialiseInterface.onSuccess(serializedResponse);
                                                          PaymentConfiguration.setAuthToken(serializedResponse.getAccessToken());

                                                          PreferenceManager.getInstance().saveToken(serializedResponse.getAccessToken());
                                                      }

                                                      @Override
                                                      public void onFailure(GSMAError errorDetails) {
                                                          PreferenceManager.getInstance().saveToken("");
                                                          paymentInitialiseInterface.onFailure(errorDetails);
                                                      }
                                                  }
                );
            } else {
                paymentInitialiseInterface.onValidationError(Utils.setError(0));
            }
        }
    }

}
