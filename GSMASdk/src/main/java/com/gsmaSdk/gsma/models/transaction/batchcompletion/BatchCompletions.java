package com.gsmaSdk.gsma.models.transaction.batchcompletion;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.network.responses.BaseResponse;

@SuppressWarnings({"ALL", "unused"})
public class BatchCompletions extends BaseResponse {

	@SerializedName("BatchTransactionCompletion")
	private List<BatchCompletion> batchTransactionCompletion;

	public void setBatchTransactionCompletion(List<BatchCompletion> batchTransactionCompletion){
		this.batchTransactionCompletion = batchTransactionCompletion;
	}

	public List<BatchCompletion> getBatchTransactionCompletion(){
		return batchTransactionCompletion;
	}
}