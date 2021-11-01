package com.gsmaSdk.gsma.models.transaction;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.network.responses.BaseResponse;

public class BatchTransactionCompletion implements BaseResponse {

	@SerializedName("BatchTransactionCompletion")
	private List<BatchTransactionCompletionItem> batchTransactionCompletion;

	public void setBatchTransactionCompletion(List<BatchTransactionCompletionItem> batchTransactionCompletion){
		this.batchTransactionCompletion = batchTransactionCompletion;
	}

	public List<BatchTransactionCompletionItem> getBatchTransactionCompletion(){
		return batchTransactionCompletion;
	}
}