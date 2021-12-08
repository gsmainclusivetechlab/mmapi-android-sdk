package com.gsmaSdk.gsma.models.transaction.transactions;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.network.responses.BaseResponse;

@SuppressWarnings("unused")
public class Transaction extends BaseResponse {

	@SerializedName("Transaction")
	private List<TransactionRequest> transaction;


	public void setTransaction(List<TransactionRequest> transaction) {
		this.transaction = transaction;
	}

	public List<TransactionRequest> getTransaction(){
		return transaction;
	}
}