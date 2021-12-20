package com.gsmaSdk.gsma.models.bills;

import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.models.transaction.transactions.Transaction;
import com.gsmaSdk.gsma.network.responses.BaseResponse;

import java.util.List;

public class Bills extends BaseResponse {

    @SerializedName("bills")
    private List<Bill> billList;

    public List<Bill> getBillList() {
        return billList;
    }

    public void setBillList(List<Bill> billList) {
        this.billList = billList;
    }
}
