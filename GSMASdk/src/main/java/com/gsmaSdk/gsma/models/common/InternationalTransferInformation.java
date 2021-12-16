package com.gsmaSdk.gsma.models.common;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class InternationalTransferInformation{

	@SerializedName("quotationReference")
	private String quotationReference;

	@SerializedName("receivingCountry")
	private String receivingCountry;

	@SerializedName("deliveryMethod")
	private String deliveryMethod;

	@SerializedName("originCountry")
	private String originCountry;

	@SerializedName("relationshipSender")
	private String relationshipSender;

	@SerializedName("remittancePurpose")
	private String remittancePurpose;


	@SerializedName("sendingServiceProviderCountry")
	private String sendingServiceProviderCountry;

	@SerializedName("recipientBlockingReason")
	private String recipientBlockingReason;

	@SerializedName("senderBlockingReason")
	private String senderBlockingReason;

	@SerializedName("quoteId")
	private String quoteId;

	public String getRecipientBlockingReason() {
		return recipientBlockingReason;
	}

	public void setRecipientBlockingReason(String recipientBlockingReason) {
		this.recipientBlockingReason = recipientBlockingReason;
	}

	public String getSenderBlockingReason() {
		return senderBlockingReason;
	}

	public void setSenderBlockingReason(String senderBlockingReason) {
		this.senderBlockingReason = senderBlockingReason;
	}

	public String getQuotationReference(){
		return quotationReference;
	}

	public String getReceivingCountry(){
		return receivingCountry;
	}

	public String getDeliveryMethod(){
		return deliveryMethod;
	}

	public String getOriginCountry(){
		return originCountry;
	}

	public String getRelationshipSender(){
		return relationshipSender;
	}

	public String getRemittancePurpose(){
		return remittancePurpose;
	}

	public String getSendingServiceProviderCountry(){
		return sendingServiceProviderCountry;
	}

	public String getQuoteId(){
		return quoteId;
	}

	public void setQuotationReference(String quotationReference) {
		this.quotationReference = quotationReference;
	}

	public void setReceivingCountry(String receivingCountry) {
		this.receivingCountry = receivingCountry;
	}

	public void setDeliveryMethod(String deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}

	public void setOriginCountry(String originCountry) {
		this.originCountry = originCountry;
	}

	public void setRelationshipSender(String relationshipSender) {
		this.relationshipSender = relationshipSender;
	}

	public void setRemittancePurpose(String remittancePurpose) {
		this.remittancePurpose = remittancePurpose;
	}

	public void setSendingServiceProviderCountry(String sendingServiceProviderCountry) {
		this.sendingServiceProviderCountry = sendingServiceProviderCountry;
	}

	public void setQuoteId(String quoteId) {
		this.quoteId = quoteId;
	}
}