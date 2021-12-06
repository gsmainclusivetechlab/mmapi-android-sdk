package com.gsmaSdk.gsma.models.account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("ALL")
public class Name{

	@SerializedName("firstName")
	@Expose
	private String firstName;

	@SerializedName("lastName")
	@Expose
	private String lastName;

	@SerializedName("fullName")
	@Expose
	private String fullName;

	@SerializedName("middleName")
	@Expose
	private String middleName;

	@SerializedName("title")
	@Expose
	private String title;

	public String getFirstName(){
		return firstName;
	}

	public String getLastName(){
		return lastName;
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