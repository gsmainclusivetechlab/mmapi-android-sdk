package com.gsmaSdk.gsma.models.transaction.transactions;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.network.responses.BaseResponse;

@SuppressWarnings("unused")
public class Transactions extends BaseResponse {

	@SerializedName("Transaction")
	private List<Transaction> transaction;


	public void setTransaction(List<Transaction> transaction) {
		this.transaction = transaction;
	}

	public List<Transaction> getTransaction(){
		return transaction;
	}
}