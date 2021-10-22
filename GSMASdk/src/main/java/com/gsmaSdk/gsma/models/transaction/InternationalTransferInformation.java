package com.gsmaSdk.gsma.models.transaction;

import com.google.gson.annotations.SerializedName;

public class InternationalTransferInformation{

	@SerializedName("quotationReference")
	private String quotationReference;

	@SerializedName("deliveryMethod")
	private String deliveryMethod;

	@SerializedName("receivingCountry")
	private String receivingCountry;

	@SerializedName("originCountry")
	private String originCountry;

	@SerializedName("relationshipSender")
	private String relationshipSender;

	@SerializedName("remittancePurpose")
	private String remittancePurpose;

	@SerializedName("sendingServiceProviderCountry")
	private String sendingServiceProviderCountry;

	@SerializedName("quoteId")
	private String quoteId;

	public String getQuotationReference(){
		return quotationReference;
	}

	public String getDeliveryMethod(){
		return deliveryMethod;
	}

	public String getReceivingCountry(){
		return receivingCountry;
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
}