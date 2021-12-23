package com.gsmaSdk.gsma.models.bills;

import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.network.responses.BaseResponse;

import java.util.List;

public class BillPayments extends BaseResponse {

    @SerializedName("billPayments")
    private List<BillPay> billPayments;

    public List<BillPay> getBillPayments() {
        return billPayments;
    }

    public void setBillPayments(List<BillPay> billPayments) {
        this.billPayments = billPayments;
    }
}
