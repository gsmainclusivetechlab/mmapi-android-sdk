package com.gsmaSdk.gsma.models.transaction;

import com.google.gson.annotations.SerializedName;

public class SubjectName{

	@SerializedName("firstName")
	private String firstName;

	@SerializedName("lastName")
	private String lastName;

	@SerializedName("nativeName")
	private String nativeName;

	@SerializedName("fullName")
	private String fullName;

	@SerializedName("middleName")
	private String middleName;

	@SerializedName("title")
	private String title;

	public String getFirstName(){
		return firstName;
	}

	public String getLastName(){
		return lastName;
	}

	public String getNativeName(){
		return nativeName;
	}

	public String getFullName(){
		return fullName;
	}

	public String getMiddleName(){
		return middleName;
	}

	public String getTitle(){
		return title;
	}
}