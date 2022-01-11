package com.gsmaSdk.gsma.merchantpay;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import com.gsmaSdk.gsma.enums.NotificationMethod;
import com.gsmaSdk.gsma.interfaces.BalanceInterface;
import com.gsmaSdk.gsma.interfaces.RequestStateInterface;
import com.gsmaSdk.gsma.manager.SDKManager;
import com.gsmaSdk.gsma.models.account.Balance;
import com.gsmaSdk.gsma.models.account.Identifier;
import com.gsmaSdk.gsma.models.common.ErrorObject;
import com.gsmaSdk.gsma.models.common.GSMAError;
import com.gsmaSdk.gsma.models.common.RequestStateObject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class MerchantPaymentUnitTest {


    CountDownLatch countDownLatch;

    @Before
    public void setUp() {
        countDownLatch = new CountDownLatch(1);
    }

    @Test
    public void balanceEmptyIdentifierSuccess() throws InterruptedException {
        ArrayList<Identifier> identifierArrayList = new ArrayList<>();
        SDKManager.merchantPayment.viewAccountBalance(identifierArrayList, new BalanceInterface() {
            @Override
            public void onBalanceSuccess(Balance balance) {
            }

            @Override
            public void onBalanceFailure(GSMAError gsmaError) {
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                countDownLatch.countDown();
                assertEquals(errorObject.getErrorCode(), "GenericError");
                assertEquals(errorObject.getErrorCategory(), "validation");
                assertEquals(errorObject.getErrorDescription(), "Invalid account identifier");
            }
        });

        countDownLatch.await();
    }

    @Test
    public void balanceEmptyIdentifierFailure() throws InterruptedException {
        ArrayList<Identifier> identifierArrayList = new ArrayList<>();
        SDKManager.merchantPayment.viewAccountBalance(identifierArrayList, new BalanceInterface() {
            @Override
            public void onBalanceSuccess(Balance balance) {
            }

            @Override
            public void onBalanceFailure(GSMAError gsmaError) {
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                countDownLatch.countDown();
                assertNotEquals(errorObject.getErrorDescription(), "Invalid Json Format");
            }
        });

        countDownLatch.await();
    }

    @Test
    public void balanceNullIdentifierSuccess() throws InterruptedException {

        SDKManager.merchantPayment.viewAccountBalance(null, new BalanceInterface() {
            @Override
            public void onBalanceSuccess(Balance balance) {
            }

            @Override
            public void onBalanceFailure(GSMAError gsmaError) {
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                countDownLatch.countDown();
                assertEquals(errorObject.getErrorCode(), "GenericError");
                assertEquals(errorObject.getErrorCategory(), "validation");
                assertEquals(errorObject.getErrorDescription(), "Invalid account identifier");
            }
        });

        countDownLatch.await();
    }

    @Test
    public void balanceNullIdentifierFailure() throws InterruptedException {

        SDKManager.merchantPayment.viewAccountBalance(null, new BalanceInterface() {
            @Override
            public void onBalanceSuccess(Balance balance) {
            }

            @Override
            public void onBalanceFailure(GSMAError gsmaError) {
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {
                countDownLatch.countDown();
                assertNotEquals(errorObject.getErrorDescription(), "Invalid Json");
            }
        });

        countDownLatch.await();
    }

    @Test
    public void merchantTransactionCorrelationIdSuccess() {
        SDKManager.merchantPayment.createMerchantTransaction(NotificationMethod.POLLING, "", null, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {

            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {

            }

            @Override
            public void getCorrelationId(String correlationID) {
                countDownLatch.countDown();
                assertNotNull(correlationID);
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {

            }
        });

    }

    @Test
    public void merchantTransactionCorrelationIdFailure() {
        SDKManager.merchantPayment.createMerchantTransaction(NotificationMethod.POLLING, "", null, new RequestStateInterface() {
            @Override
            public void onRequestStateSuccess(RequestStateObject requestStateObject) {

            }

            @Override
            public void onRequestStateFailure(GSMAError gsmaError) {

            }

            @Override
            public void getCorrelationId(String correlationID) {
                countDownLatch.countDown();
                assertNotEquals(correlationID, "");
            }

            @Override
            public void onValidationError(ErrorObject errorObject) {

            }
        });

    }

    @Test
    public void merchantTransactionNullTransactionRequestSuccess() {
        SDKManager.merchantPayment.createMerchantTransaction(NotificationMethod.POLLING, "", null, new RequestStateInterface() {
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
                countDownLatch.countDown();
                assertEquals(errorObject.getErrorCode(), "GenericError");
                assertEquals(errorObject.getErrorCategory(), "validation");
                assertEquals(errorObject.getErrorDescription(), "Invalid json format");

            }
        });

    }

    @Test
    public void merchantTransactionNullTransactionRequestFailure() {
        SDKManager.merchantPayment.createMerchantTransaction(NotificationMethod.POLLING, "", null, new RequestStateInterface() {
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
                countDownLatch.countDown();
                assertNotEquals(errorObject.getErrorDescription(), "Invalid Identifier");

            }
        });

    }


}
