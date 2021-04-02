package com.nucigent.elms.search.dto;

import java.io.Serializable;

public class Result implements Serializable {

	private static final long serialVersionUID = 1L;

	private String postcode;
	private String post_town;
	private String line_1;
	private String line_2;
	private String line_3;
	private String county;
	private String postal_county;



	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getPost_town() {
		return post_town;
	}

	public void setPost_town(String post_town) {
		this.post_town = post_town;
	}

	public String getLine_1() {
		return line_1;
	}

	public void setLine_1(String line_1) {
		this.line_1 = line_1;
	}

	public String getLine_2() {
		return line_2;
	}

	public void setLine_2(String line_2) {
		this.line_2 = line_2;
	}

	public String getLine_3() {
		return line_3;
	}

	public void setLine_3(String line_3) {
		this.line_3 = line_3;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getPostal_county() {
		return postal_county;
	}

	public void setPostal_county(String postal_county) {
		this.postal_county = postal_county;
	}


}

