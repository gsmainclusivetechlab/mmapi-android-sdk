package com.gsmaSdk.gsma.models.transaction;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("ALL")
public class SenderKyc{

	@SerializedName("emailAddress")
	private String emailAddress;

	@SerializedName("occupation")
	private String occupation;

	@SerializedName("postalAddress")
	private PostalAddress postalAddress;

	@SerializedName("nationality")
	private String nationality;

	@SerializedName("gender")
	private String gender;

	@SerializedName("idDocument")
	private List<IdDocumentItem> idDocument;

	@SerializedName("birthCountry")
	private String birthCountry;

	@SerializedName("employerName")
	private String employerName;

	@SerializedName("dateOfBirth")
	private String dateOfBirth;

	@SerializedName("contactPhone")
	private String contactPhone;

	@SerializedName("subjectName")
	private SubjectName subjectName;

	public String getEmailAddress(){
		return emailAddress;
	}

	public String getOccupation(){
		return occupation;
	}

	public PostalAddress getPostalAddress(){
		return postalAddress;
	}

	public String getNationality(){
		return nationality;
	}

	public String getGender(){
		return gender;
	}

	public List<IdDocumentItem> getIdDocument(){
		return idDocument;
	}

	public String getBirthCountry(){
		return birthCountry;
	}

	public String getEmployerName(){
		return employerName;
	}

	public String getDateOfBirth(){
		return dateOfBirth;
	}

	public String getContactPhone(){
		return contactPhone;
	}

	public SubjectName getSubjectName(){
		return subjectName;
	}
}