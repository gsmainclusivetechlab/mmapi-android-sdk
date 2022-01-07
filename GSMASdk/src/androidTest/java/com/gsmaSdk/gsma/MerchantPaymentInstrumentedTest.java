package com.gsmaSdk.gsma;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MerchantPaymentInstrumentedTest {


    @Before
    @DisplayName("Initial setup")
    public void setUp() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);


        PaymentConfiguration.init("59vthmq3f6i15v6jmcjskfkmh",
                "ef8tl4gihlpfd7r8jpc1t1nda33q5kcnn32cj375lq6mg2nv7rb",
                AuthenticationType.STANDARD_LEVEL,
                "https://e0943004-803f-485d-8f9d-b5acebb0d153.mock.pstmn.io",
                "oVN89kXyTx1cKT3ZohH7h6foEmQmjqQm3OK2U8Ue",
                Environment.SANDBOX);

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        PreferenceManager.getInstance().init(appContext);
        SDKManager.getInstance().init(appContext, new PaymentInitialiseInterface() {
            @Override
            public void onValidationError(ErrorObject errorObject) {
                countDownLatch.countDown();
            }

            @Override
            public void onSuccess(Token token) {
                Assert.assertNotNull(token);
                countDownLatch.countDown();
            }

            @Override
            public void onFailure(GSMAError gsmaError) {
                countDownLatch.countDown();

            }
        });
         countDownLatch.await();
    }

    @Test
    @DisplayName("Payee initiate Merchant Payment")
    public void createMerchantTransactionTestSuccess() throws InterruptedException {

        Transaction transaction = getTransactionObject();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        SDKManager.merchantPayment.createMerchantTransaction(NotificationMethod.CALLBACK, "", transaction, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {
                assertNotNull(requestStateObject);
                assertNotNull(requestStateObject.getServerCorrelationId());
                assertEquals(requestStateObject.getNotificationMethod(), "callback");
                assertEquals(requestStateObject.getStatus(),"pending");
                countDownLatch.countDown();
            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {
                countDownLatch.countDown();
            }

            @Override
            public void getCorrelationId(String correlationID) {

            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                countDownLatch.countDown();
            }
        });

        countDownLatch.await();

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