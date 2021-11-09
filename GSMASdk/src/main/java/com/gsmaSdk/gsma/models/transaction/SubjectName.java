package com.gsmaSdk.gsma.models.transaction;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class SubjectName{

	@SuppressWarnings("unused")
    @SerializedName("firstName")
	private String firstName;

	@SuppressWarnings("unused")
    @SerializedName("lastName")
	private String lastName;

	@SuppressWarnings("unused")
    @SerializedName("nativeName")
	private String nativeName;

	@SuppressWarnings("unused")
    @SerializedName("fullName")
	private String fullName;

	@SuppressWarnings("unused")
    @SerializedName("middleName")
	private String middleName;

	@SuppressWarnings("unused")
    @SerializedName("title")
	private String title;

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

	@SuppressWarnings("unused")

    public String getFirstName(){
		return firstName;
	}

	@SuppressWarnings("unused")
    public String getLastName(){
		return lastName;
	}

	@SuppressWarnings("unused")
    public String getNativeName(){
		return nativeName;
	}

	@SuppressWarnings("unused")
    public String getFullName(){
		return fullName;
	}

	@SuppressWarnings("unused")
    public String getMiddleName(){
		return middleName;
	}

	@SuppressWarnings("unused")
    public String getTitle(){
		return title;
	}
}