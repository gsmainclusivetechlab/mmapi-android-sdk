package com.gsmaSdk.gsma.models.common;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class SubjectName{

	@SerializedName("title")
	private String title;

	@SerializedName("firstName")
	private String firstName;

	@SerializedName("middleName")
	private String middleName;

	@SerializedName("lastName")
	private String lastName;

	@SerializedName("fullName")
	private String fullName;

    @SerializedName("nativeName")
	private String nativeName;


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setNativeName(String nativeName) {
		this.nativeName = nativeName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public void setTitle(String title) {
		this.title = title;
	}


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