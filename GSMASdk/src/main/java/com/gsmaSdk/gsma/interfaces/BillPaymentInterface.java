package com.gsmaSdk.gsma.interfaces;


import com.gsmaSdk.gsma.models.account.Link;
import com.gsmaSdk.gsma.models.bills.BillPayment;
import com.gsmaSdk.gsma.models.common.GSMAError;

/**
 * Interface for clients to view bill payments
 * */
@SuppressWarnings("ALL")
public interface BillPaymentInterface extends BaseInterface{

    void onBillPaymentSuccess(BillPayment billPayment);

    void onBillPaymentFailure(GSMAError gsmaError);
}
