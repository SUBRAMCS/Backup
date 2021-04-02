package com.nucigent.elms.account.dto;

import java.util.List;

public class BankAccountMapDto {
	
	private long valAcntId;
	private BankDtlsDto bankDtlsDto;
	
	public long getValAcntId() {
		return valAcntId;
	}
	public void setValAcntId(long valAcntId) {
		this.valAcntId = valAcntId;
	}
	public BankDtlsDto getBankDtlsDto() {
		return bankDtlsDto;
	}
	public void setBankDtlsDto(BankDtlsDto bankDtlsDto) {
		this.bankDtlsDto = bankDtlsDto;
	}
	
}
