package com.gsmaSdk.gsma.interfaces;


import com.gsmaSdk.gsma.models.bills.BillPay;
import com.gsmaSdk.gsma.models.common.GSMAError;

/**
 * Interface for clients to view bill payments
 * */
@SuppressWarnings("ALL")
public interface BillPaymentInterface extends BaseInterface{

    void onBillPaymentSuccess(BillPay billPayment);

    void onBillPaymentFailure(GSMAError gsmaError);
}
