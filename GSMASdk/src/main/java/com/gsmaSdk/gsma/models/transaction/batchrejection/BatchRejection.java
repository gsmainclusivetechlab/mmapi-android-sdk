package com.gsmaSdk.gsma.models.transaction.batchrejection;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.network.responses.BaseResponse;

@SuppressWarnings({"ALL", "unused"})
public class BatchRejection extends BaseResponse {

	@SerializedName("BatchTransactionRejection")
	private List<BatchRejectionItem> batchTransactionRejection;

	public void setBatchTransactionRejection(List<BatchRejectionItem> batchTransactionRejection){
		this.batchTransactionRejection = batchTransactionRejection;
	}

	public List<BatchRejectionItem> getBatchTransactionRejection(){
		return batchTransactionRejection;
	}
}