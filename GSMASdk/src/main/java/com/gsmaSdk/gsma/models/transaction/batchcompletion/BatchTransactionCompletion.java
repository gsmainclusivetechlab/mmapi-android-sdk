package com.gsmaSdk.gsma.models.transaction.batchcompletion;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.network.responses.BaseResponse;

@SuppressWarnings({"ALL", "unused"})
public class BatchTransactionCompletion extends BaseResponse {

	@SerializedName("BatchTransactionCompletion")
	private List<BatchTransactionCompletionItem> batchTransactionCompletion;

	public void setBatchTransactionCompletion(List<BatchTransactionCompletionItem> batchTransactionCompletion){
		this.batchTransactionCompletion = batchTransactionCompletion;
	}

	public List<BatchTransactionCompletionItem> getBatchTransactionCompletion(){
		return batchTransactionCompletion;
	}
}