package com.gsmaSdk.gsma.models;

import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.models.transaction.CreditPartyItem;
import com.gsmaSdk.gsma.models.transaction.DebitPartyItem;

import java.util.ArrayList;

/**
 * Model class for Authorisation Code Request
 * */
public class CodeRequest {

	@SerializedName("requestDate")
	private String requestDate;

	@SerializedName("currency")
	private String currency;

	@SerializedName("amount")
	private String amount;


	public String getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
}