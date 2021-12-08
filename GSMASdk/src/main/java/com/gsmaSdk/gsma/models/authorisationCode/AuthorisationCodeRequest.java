package com.gsmaSdk.gsma.models.authorisationCode;

import com.google.gson.annotations.SerializedName;

/**
 * Model class for Authorisation Code Request
 * */
public class AuthorisationCodeRequest {

	@SerializedName("requestDate")
	private String requestDate;

	@SerializedName("currency")
	private String currency;

	@SerializedName("amount")
	private String amount;

	@SuppressWarnings("unused")
	public String getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}

	@SuppressWarnings("unused")
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@SuppressWarnings("unused")
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
}