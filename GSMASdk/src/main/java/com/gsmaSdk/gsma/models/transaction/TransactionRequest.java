package com.gsmaSdk.gsma.models.transaction;

import java.util.ArrayList;
import com.google.gson.annotations.SerializedName;

/**
 * Model class for Transaction Request
 * */
@SuppressWarnings("ALL")
public class TransactionRequest {

	@SerializedName("amount")
	private String amount;

	@SerializedName("debitParty")
	private ArrayList<DebitPartyItem> debitParty;

	@SerializedName("currency")
	private String currency;

	@SerializedName("creditParty")
	private ArrayList<CreditPartyItem> creditParty;

	public void setAmount(String amount){
		this.amount = amount;
	}

	public String getAmount(){
		return amount;
	}

	public void setDebitParty(ArrayList<DebitPartyItem> debitParty){
		this.debitParty = debitParty;
	}

	public ArrayList<DebitPartyItem> getDebitParty(){
		return debitParty;
	}

	public void setCurrency(String currency){
		this.currency = currency;
	}

	public String getCurrency(){
		return currency;
	}

	public void setCreditParty(ArrayList<CreditPartyItem> creditParty){
		this.creditParty = creditParty;
	}

	public ArrayList<CreditPartyItem> getCreditParty(){
		return creditParty;
	}

}