package com.gsmaSdk.gsma.models.transaction.batchrejection;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.network.responses.BaseResponse;

@SuppressWarnings({"ALL", "unused"})
public class BatchRejections extends BaseResponse {

	@SerializedName("BatchTransactionRejection")
	private List<BatchRejection> batchTransactionRejection;

	public void setBatchTransactionRejection(List<BatchRejection> batchTransactionRejection){
		this.batchTransactionRejection = batchTransactionRejection;
	}

	public List<BatchRejection> getBatchTransactionRejection(){
		return batchTransactionRejection;
	}
}