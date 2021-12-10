package com.gsmaSdk.gsma.models.transaction.batchcompletion;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.network.responses.BaseResponse;

@SuppressWarnings({"ALL", "unused"})
public class BatchCompletion extends BaseResponse {

	@SerializedName("BatchTransactionCompletion")
	private List<BatchCompletionItem> batchTransactionCompletion;

	public void setBatchTransactionCompletion(List<BatchCompletionItem> batchTransactionCompletion){
		this.batchTransactionCompletion = batchTransactionCompletion;
	}

	public List<BatchCompletionItem> getBatchTransactionCompletion(){
		return batchTransactionCompletion;
	}
}