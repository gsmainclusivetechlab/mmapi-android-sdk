package com.gsmaSdk.gsma;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.google.gson.Gson;
import com.gsmaSdk.gsma.enums.AuthenticationType;
import com.gsmaSdk.gsma.enums.Environment;
import com.gsmaSdk.gsma.enums.NotificationMethod;
import com.gsmaSdk.gsma.interfaces.PaymentInitialiseInterface;
import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.manager.PreferenceManager;
import com.gsmaSdk.gsma.manager.SDKManager;
import com.gsmaSdk.gsma.models.account.AccountIdentifier;
import com.gsmaSdk.gsma.models.common.ErrorObject;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.RequestStateObject;
import com.gsmaSdk.gsma.models.common.Token;
import com.gsmaSdk.gsma.models.transaction.transactions.Transaction;
import com.gsmaSdk.gsma.network.retrofit.PaymentConfiguration;

import java.util.ArrayList;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MerchantPaymentInstrumentedTest {

    @Before
    public void setUp() {

        PaymentConfiguration.init("59vthmq3f6i15v6jmcjskfkm",
                      "", AuthenticationType.STANDARD_LEVEL,
                        "ef8tl4gihlpfd7r8jpc1t1nda33q5kcnn32cj375lq6mg2nv7rb",
                        "https://e0943004-803f-485d-8f9d-b5acebb0d153.mock.pstmn.io",
                        Environment.SANDBOX);

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        PreferenceManager.getInstance().init(appContext);
        SDKManager.getInstance().init(appContext, new PaymentInitialiseInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {

            }

            @Override
            public void onSuccess(Token token) {
            }

            @Override
            public void onFailure(GSMAError gsmaError) {
            }
        });
    }

    @Test
    public void createMerchantTransactionTestSuccess() {
        Transaction transaction = getTransactionObject();
        SDKManager.merchantPayment.createMerchantTransaction(NotificationMethod.POLLING, "", transaction, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {

            }
            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {

            }

            @Override
            public void getCorrelationId(String correlationID) {

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {

            }
        });



    }


    public Transaction getTransactionObject() {
        Transaction transactionRequest = new Transaction();
        ArrayList<AccountIdentifier> debitPartyList = new ArrayList<>();
        ArrayList<AccountIdentifier> creditPartyList = new ArrayList<>();

        AccountIdentifier debitPartyItem = new AccountIdentifier();
        AccountIdentifier creditPartyItem = new AccountIdentifier();

        debitPartyItem.setKey("walletid");
        debitPartyItem.setValue("1");
        debitPartyList.add(debitPartyItem);

        creditPartyItem.setKey("msisdn");
        creditPartyItem.setValue("+44012345678");
        creditPartyList.add(creditPartyItem);

        transactionRequest.setDebitParty(debitPartyList);
        transactionRequest.setCreditParty(creditPartyList);
        transactionRequest.setAmount("200.00");
        transactionRequest.setCurrency("RWF");
        return transactionRequest;
    }

}