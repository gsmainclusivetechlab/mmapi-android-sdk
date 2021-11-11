package com.gsmaSdk.gsma.models.transaction;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.network.responses.BaseResponse;

@SuppressWarnings("unused")
public class Transaction implements BaseResponse {

	@SerializedName("Transaction")
	private List<TransactionRequest> transaction;


	public void setTransaction(List<TransactionRequest> transaction) {
		this.transaction = transaction;
	}

	public List<TransactionRequest> getTransaction(){
		return transaction;
	}
}