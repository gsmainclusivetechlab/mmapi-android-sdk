package com.gsmaSdk.gsma.models.transaction;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class InternationalTransferInformation{

	@SuppressWarnings("unused")
    @SerializedName("quotationReference")
	private String quotationReference;

	@SuppressWarnings("unused")
    @SerializedName("deliveryMethod")
	private String deliveryMethod;

	@SuppressWarnings("unused")
    @SerializedName("receivingCountry")
	private String receivingCountry;

	@SuppressWarnings("unused")
    @SerializedName("originCountry")
	private String originCountry;

	@SuppressWarnings("unused")
    @SerializedName("relationshipSender")
	private String relationshipSender;

	@SuppressWarnings("unused")
    @SerializedName("remittancePurpose")
	private String remittancePurpose;

	@SuppressWarnings("unused")
    @SerializedName("sendingServiceProviderCountry")
	private String sendingServiceProviderCountry;

	@SuppressWarnings("unused")
    @SerializedName("quoteId")
	private String quoteId;

	@SuppressWarnings("unused")
    public String getQuotationReference(){
		return quotationReference;
	}

	@SuppressWarnings("unused")
    public String getDeliveryMethod(){
		return deliveryMethod;
	}

	@SuppressWarnings("unused")
    public String getReceivingCountry(){
		return receivingCountry;
	}

	@SuppressWarnings("unused")
    public String getOriginCountry(){
		return originCountry;
	}

	@SuppressWarnings("unused")
    public String getRelationshipSender(){
		return relationshipSender;
	}

	@SuppressWarnings("unused")
    public String getRemittancePurpose(){
		return remittancePurpose;
	}

	@SuppressWarnings("unused")
    public String getSendingServiceProviderCountry(){
		return sendingServiceProviderCountry;
	}

	@SuppressWarnings("unused")
    public String getQuoteId(){
		return quoteId;
	}
}