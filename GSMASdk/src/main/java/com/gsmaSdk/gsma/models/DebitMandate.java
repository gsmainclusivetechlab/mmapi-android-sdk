package com.gsmaSdk.gsma.models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.models.transaction.CustomDataItem;

public class DebitMandate{

	@SerializedName("payee")
	private List<PayeeItem> payee;

	@SerializedName("amountLimit")
	private String amountLimit;

	@SerializedName("numberOfPayments")
	private String numberOfPayments;

	@SerializedName("endDate")
	private String endDate;

	@SerializedName("frequencyType")
	private String frequencyType;

	@SerializedName("requestDate")
	private String requestDate;

	@SerializedName("currency")
	private String currency;

	@SerializedName("customData")
	private ArrayList<CustomDataItem> customData;

	@SerializedName("startDate")
	private String startDate;

	public void setPayee(ArrayList<PayeeItem> payee) {
		this.payee = payee;
	}

	public void setAmountLimit(String amountLimit) {
		this.amountLimit = amountLimit;
	}

	public void setNumberOfPayments(String numberOfPayments) {
		this.numberOfPayments = numberOfPayments;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setFrequencyType(String frequencyType) {
		this.frequencyType = frequencyType;
	}

	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public void setCustomData(ArrayList<CustomDataItem> customData) {
		this.customData = customData;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public List<PayeeItem> getPayee(){
		return payee;
	}

	public String getAmountLimit(){
		return amountLimit;
	}

	public String getNumberOfPayments(){
		return numberOfPayments;
	}

	public String getEndDate(){
		return endDate;
	}

	public String getFrequencyType(){
		return frequencyType;
	}

	public String getRequestDate(){
		return requestDate;
	}

	public String getCurrency(){
		return currency;
	}

	public ArrayList<CustomDataItem> getCustomData(){
		return customData;
	}

	public String getStartDate(){
		return startDate;
	}
}