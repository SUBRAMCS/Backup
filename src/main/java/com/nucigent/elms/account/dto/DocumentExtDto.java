package com.nucigent.elms.account.dto;

import java.io.Serializable;

public class DocumentExtDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String Doc_Extension_Value;
	private String Max_Size_Allowed;
	private String IsActive;
	
	public String getDoc_Extension_Value() {
		return Doc_Extension_Value;
	}
	public void setDoc_Extension_Value(String doc_Extension_Value) {
		Doc_Extension_Value = doc_Extension_Value;
	}
	public String getMax_Size_Allowed() {
		return Max_Size_Allowed;
	}
	public void setMax_Size_Allowed(String max_Size_Allowed) {
		Max_Size_Allowed = max_Size_Allowed;
	}
	public String getIsActive() {
		return IsActive;
	}
	public void setIsActive(String isActive) {
		IsActive = isActive;
	}
	
	
	
}
