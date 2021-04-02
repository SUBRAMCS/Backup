package com.nucigent.elms.account.dto;

import java.util.List;

import com.nucigent.elms.account.dto.CountrycodeDto;
import com.nucigent.elms.account.dto.CountrycodephoneDto;
import com.nucigent.elms.account.dto.GenderDto;
import com.nucigent.elms.account.dto.ListObjectDto;
import com.nucigent.elms.account.dto.RelationshipDto;
import com.nucigent.elms.account.dto.TitleDto;


public class ListObjectDto {
	
	private List<TitleDto> Title;
	private List<GenderDto> Gender;
	private List<RelationshipDto> Relationship;
	private List<CountrycodeDto> Countrycode;
	private List<CountrycodephoneDto> Countrycodephone;
	private List<DocumentExtDto> documentExt;
		
	public List<TitleDto> getTitle() {
		return Title;
	}
	public void setTitle(List<TitleDto> title) {
		Title = title;
	}
	public List<GenderDto> getGender() {
		return Gender;
	}
	public void setGender(List<GenderDto> gender) {
		Gender = gender;
	}
	public List<RelationshipDto> getRelationship() {
		return Relationship;
	}
	public void setRelationship(List<RelationshipDto> relationship) {
		Relationship = relationship;
	}
	public List<CountrycodeDto> getCountrycode() {
		return Countrycode;
	}
	public void setCountrycode(List<CountrycodeDto> countrycode) {
		Countrycode = countrycode;
	}
	public List<CountrycodephoneDto> getCountrycodephone() {
		return Countrycodephone;
	}
	public void setCountrycodephone(List<CountrycodephoneDto> countrycodephone) {
		Countrycodephone = countrycodephone;
	}
	public List<DocumentExtDto> getDocumentExt() {
		return documentExt;
	}
	public void setDocumentExt(List<DocumentExtDto> documentExt) {
		this.documentExt = documentExt;
	}
}
