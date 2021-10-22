package com.gsmaSdk.gsma.models.transaction;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.gsmaSdk.gsma.models.transaction.CreditPartyItem;
import com.gsmaSdk.gsma.models.transaction.DebitPartyItem;

public class TransactionItem{

	@SerializedName("debitParty")
	private List<com.gsmaSdk.gsma.models.transaction.DebitPartyItem> debitParty;

	@SerializedName("amount")
	private String amount;

	@SerializedName("transactionReference")
	private String transactionReference;

	@SerializedName("transactionStatus")
	private String transactionStatus;

	@SerializedName("internationalTransferInformation")
	private InternationalTransferInformation internationalTransferInformation;

	@SerializedName("requestingOrganisation")
	private RequestingOrganisation requestingOrganisation;

	@SerializedName("senderKyc")
	private SenderKyc senderKyc;

	@SerializedName("type")
	private String type;

	@SerializedName("creationDate")
	private String creationDate;

	@SerializedName("modificationDate")
	private String modificationDate;

	@SerializedName("requestDate")
	private String requestDate;

	@SerializedName("currency")
	private String currency;

	@SerializedName("creditParty")
	private List<CreditPartyItem> creditParty;

	public List<DebitPartyItem> getDebitParty(){
		return debitParty;
	}

	public String getAmount(){
		return amount;
	}

	public String getTransactionReference(){
		return transactionReference;
	}

	public String getTransactionStatus(){
		return transactionStatus;
	}

	public InternationalTransferInformation getInternationalTransferInformation(){
		return internationalTransferInformation;
	}

	public void setDebitParty(List<DebitPartyItem> debitParty) {
		this.debitParty = debitParty;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public void setTransactionReference(String transactionReference) {
		this.transactionReference = transactionReference;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public void setInternationalTransferInformation(InternationalTransferInformation internationalTransferInformation) {
		this.internationalTransferInformation = internationalTransferInformation;
	}

	public void setRequestingOrganisation(RequestingOrganisation requestingOrganisation) {
		this.requestingOrganisation = requestingOrganisation;
	}

	public void setSenderKyc(SenderKyc senderKyc) {
		this.senderKyc = senderKyc;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public void setModificationDate(String modificationDate) {
		this.modificationDate = modificationDate;
	}

	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public void setCreditParty(List<CreditPartyItem> creditParty) {
		this.creditParty = creditParty;
	}

	public RequestingOrganisation getRequestingOrganisation(){
		return requestingOrganisation;
	}

	public SenderKyc getSenderKyc(){
		return senderKyc;
	}

	public String getType(){
		return type;
	}

	public String getCreationDate(){
		return creationDate;
	}

	public String getModificationDate(){
		return modificationDate;
	}

	public String getRequestDate(){
		return requestDate;
	}

	public String getCurrency(){
		return currency;
	}

	public List<CreditPartyItem> getCreditParty(){
		return creditParty;
	}
}