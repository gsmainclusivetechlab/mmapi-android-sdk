package com.gsmaSdk.gsma.models.transaction;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.network.responses.BaseResponse;

@SuppressWarnings({"ALL", "unused"})
public class BatchTransactionRejection extends BaseResponse {

	@SerializedName("BatchTransactionRejection")
	private List<BatchTransactionRejectionItem> batchTransactionRejection;

	public void setBatchTransactionRejection(List<BatchTransactionRejectionItem> batchTransactionRejection){
		this.batchTransactionRejection = batchTransactionRejection;
	}

	public List<BatchTransactionRejectionItem> getBatchTransactionRejection(){
		return batchTransactionRejection;
	}
}