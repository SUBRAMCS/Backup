package com.nucigent.elms.serviceimpl;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nucigent.elms.account.dto.BankAccountDto;
import com.nucigent.elms.account.dto.BankAccountMapDto;
import com.nucigent.elms.account.dto.BankDtlsDto;
import com.nucigent.elms.account.dto.DocumentDetailDto;
import com.nucigent.elms.account.dto.PersonalDetailDto;
import com.nucigent.elms.dao.UserBankAcntDao;
import com.nucigent.elms.service.UserBankAcntService;

@Service
public class UserBankAcntServiceImpl implements UserBankAcntService  {

	@Autowired
	private UserBankAcntDao userBankAcntDao;
	
	@Override
	public Map<String,BankDtlsDto> getBankAccntDetail() {
			
		return userBankAcntDao.getBankAccntDetail();
	}

	@Override
	public Integer saveUserBankAccnt(@Valid BankDtlsDto bankDtlsDto) {
		// TODO Auto-generated method stub
		return userBankAcntDao.saveUserBankAccnt(bankDtlsDto);
	}

	@Override
	public List<BankDtlsDto> getBankAccntList() {
		// TODO Auto-generated method stub
		return userBankAcntDao.getBankDetail();
	}

	@Override
	public Integer saveUserBankPersonalAccnt(@Valid PersonalDetailDto personalDtlsDto) {
		// TODO Auto-generated method stub
		return userBankAcntDao.saveUserBankPersonalAccnt(personalDtlsDto);
	}

	@Override
	public int removeBankinfo(Integer valAccountId) {
		// TODO Auto-generated method stub
		return userBankAcntDao.removeBankinfo(valAccountId);
	}

	@Override
	public Integer saveUserBankAccntDtlInfo(@Valid BankAccountDto bankAccountDto) {
		// TODO Auto-generated method stub
		return userBankAcntDao.saveUserBankAccntDtlInfo(bankAccountDto);
	}

	@Override
	public Map<String, BankDtlsDto> getBankAccntDetailFrItem(Integer valAccountId) {
		// TODO Auto-generated method stub
		return userBankAcntDao.getBankAccntDetailFrItem(valAccountId);
	}

	@Override
	public Integer saveorupdateDocumentDetails(DocumentDetailDto documentDetailDto, List<MultipartFile> fileLs) {
		
		return userBankAcntDao.saveorupdateDocumentDetails(documentDetailDto, fileLs);
	}

	@Override
	public Integer updateBankAccntAddInfo(@Valid PersonalDetailDto personalDtlsDto) {
		
		return userBankAcntDao.updateBankAccntAddInfo(personalDtlsDto);
	}

	
}
