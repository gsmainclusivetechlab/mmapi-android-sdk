package com.gsmaSdk.gsma.models.common;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("ALL")
public class KYCInformation {


	@SerializedName("birthCountry")
	private String birthCountry;

	@SerializedName("dateOfBirth")
	private String dateOfBirth;

	@SerializedName("contactPhone")
	private String contactPhone;

	@SerializedName("emailAddress")
	private String emailAddress;

	@SerializedName("employerName")
	private String employerName;

	@SerializedName("gender")
	private String gender;

	@SerializedName("idDocument")
	private List<IdDocument> idDocument;

	@SerializedName("nationality")
	private String nationality;

	@SerializedName("postalAddress")
	private Address postalAddress;

	@SerializedName("occupation")
	private String occupation;

	@SerializedName("subjectName")
	private SubjectName subjectName;


	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public void setPostalAddress(Address postalAddress) {
		this.postalAddress = postalAddress;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setIdDocument(List<IdDocument> idDocument) {
		this.idDocument = idDocument;
	}

	public void setBirthCountry(String birthCountry) {
		this.birthCountry = birthCountry;
	}

	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public void setSubjectName(SubjectName subjectName) {
		this.subjectName = subjectName;
	}

	public String getEmailAddress(){
		return emailAddress;
	}

	public String getOccupation(){
		return occupation;
	}

	public Address getPostalAddress(){
		return postalAddress;
	}

	public String getNationality(){
		return nationality;
	}

	public String getGender(){
		return gender;
	}

	public List<IdDocument> getIdDocument(){
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