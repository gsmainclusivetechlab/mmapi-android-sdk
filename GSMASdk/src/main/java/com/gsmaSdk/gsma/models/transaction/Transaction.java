package com.gsmaSdk.gsma.models.transaction;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.network.responses.BaseResponse;

public class Transaction implements BaseResponse {

	@SerializedName("Transaction")
	private List<TransactionItem> transaction;


	public void setTransaction(List<TransactionItem> transaction) {
		this.transaction = transaction;
	}

	public List<TransactionItem> getTransaction(){
		return transaction;
	}
}