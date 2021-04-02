package com.nucigent.elms.account.service;

import java.util.List;

import com.nucigent.elms.account.dto.ListObjectDto;

public interface UtilityService {
	
	ListObjectDto getListObject();

	List<String> getBanksLookUp();

}
