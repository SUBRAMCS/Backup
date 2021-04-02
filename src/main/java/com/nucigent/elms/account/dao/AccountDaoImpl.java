package com.nucigent.elms.account.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.nucigent.elms.account.domain.AccountProfile;
import com.nucigent.elms.account.domain.AddressDetail;
import com.nucigent.elms.account.domain.ContactDetail;
import com.nucigent.elms.account.domain.EmailDetail;
import com.nucigent.elms.account.domain.EmailRowMapper;
import com.nucigent.elms.account.domain.PersonalDetail;
import com.nucigent.elms.account.domain.PhoneDetail;
import com.nucigent.elms.account.domain.PhoneDetailRowMapper;
import com.nucigent.elms.account.helper.AccountQueryConstants;

@Repository
public class AccountDaoImpl implements AccountDao {

	Logger logger = LoggerFactory.getLogger(AccountDaoImpl.class);

	@Autowired private JdbcTemplate jdbcTemplate;
	@Autowired private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public Integer createAccountProfile(AccountProfile accountProfile) {

		KeyHolder holder = new GeneratedKeyHolder();
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("Registration_ID", accountProfile.getRegistrationId())
				.addValue("Individual_ID", accountProfile.getIndividualId())
				.addValue("Address_ID", accountProfile.getAddressId())
				.addValue("Document_ID", accountProfile.getDocumentId());
		namedParameterJdbcTemplate.update(AccountQueryConstants.SQL_INSERT_ACCOUNT_PROFILE, parameters, holder);
		return holder.getKey().intValue();

	}

	@Override
	public void updatePersonalDetail(PersonalDetail personalDetail) {

		Calendar calendar = Calendar.getInstance();
		java.sql.Timestamp updatetedDate = new java.sql.Timestamp(calendar.getTime().getTime());

		jdbcTemplate.update(AccountQueryConstants.SQL_UPDATE_INDIVIDUAL_MASTER_EDITABLE_INFO, personalDetail.getTitle(), personalDetail.getMiddleName(),
				personalDetail.getGender(), updatetedDate, personalDetail.getIndividualId());

	}

	@Override
	public Integer saveAddressDetail(AddressDetail addressDetail) {

		KeyHolder holder = new GeneratedKeyHolder();

		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("Individual_ID", addressDetail.getIndividualId())
				.addValue("Address_Line1", addressDetail.getAddressLine1())
				.addValue("Address_Line2", addressDetail.getAddressLine2())
				.addValue("Address_Line3", addressDetail.getAddressLine3())
				.addValue("Address_Line4", addressDetail.getAddressLine4())
				.addValue("County_State",  addressDetail.getCountryState())
				.addValue("PostCode",      addressDetail.getPostCode())
				.addValue("Country",       addressDetail.getCountry());

		namedParameterJdbcTemplate.update(AccountQueryConstants.SQL_INSERT_ADDRESS_DETAIL, parameters, holder);
		return holder.getKey().intValue();
	}
	
	@Override
	public Integer getAccountProfileIdIfExists(Integer registeredId) {
		return jdbcTemplate.queryForObject(AccountQueryConstants.SQL_SELECT_ACCOUNT_PROFILE_BY_REGISTERED_ID, new Object[] { registeredId }, Integer.class);
	}

	@Override
	public void updateAddressIdByAccountProfileId(Integer addressId, Integer accountProfileId) {

		Calendar calendar = Calendar.getInstance();
		java.sql.Timestamp updatetedDate = new java.sql.Timestamp(calendar.getTime().getTime());
		jdbcTemplate.update(AccountQueryConstants.SQL_UPDATE_ACCOUNT_PROFILE_BY_ID, addressId, updatetedDate, accountProfileId);

	}

	@Override
	public void saveEmailAddress(List<EmailDetail> emailDetails) {

		jdbcTemplate.batchUpdate(AccountQueryConstants.SQL_INSERT_EMAIL_ADDRESS,
				new BatchPreparedStatementSetter() {

			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setInt(1, emailDetails.get(i).getIndividualId());
				ps.setString(2, emailDetails.get(i).getEmailAddress());
				ps.setString(3, emailDetails.get(i).getPrimaryEmailFlag());
			}

			public int getBatchSize() {
				return emailDetails.size();
			}

		});

	}

	@Override
	public void savePhoneNumber(List<PhoneDetail> phoneDetails) {

		jdbcTemplate.batchUpdate(AccountQueryConstants.SQL_INSERT_PHONE_NUMBER,
				new BatchPreparedStatementSetter() {

			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setInt(1, phoneDetails.get(i).getIndividualId());
				ps.setInt(2, phoneDetails.get(i).getCountryId());
				ps.setString(3, phoneDetails.get(i).getPhoneNumber());
				ps.setString(4, phoneDetails.get(i).getPrimaryPhoneFlag());
			}

			public int getBatchSize() {
				return phoneDetails.size();
			}

		});
	}

	@Override
	public AccountProfile getAccountProfileById(Integer accountProfileId) {
		return jdbcTemplate.queryForObject(AccountQueryConstants.SQL_SELECE_ACCOUNT_PROFILE_BY_ID,
				(rs, rowNum) -> {
					AccountProfile accountProfile = new AccountProfile();
					accountProfile.setAccountProfileId(rs.getInt("Account_Profile_ID"));
					accountProfile.setRegistrationId(rs.getInt("Registration_ID"));
					accountProfile.setIndividualId(rs.getInt("Individual_ID"));
					accountProfile.setAddressId(rs.getInt("Address_ID"));
					accountProfile.setDocumentId(rs.getInt("Document_ID"));
					return accountProfile;
				}, accountProfileId);
	}

	@Override
	public void updateAddressDetail(AddressDetail addressDetail) {

		Calendar calendar = Calendar.getInstance();
		java.sql.Timestamp updatetedDate = new java.sql.Timestamp(calendar.getTime().getTime());

		jdbcTemplate.update(AccountQueryConstants.SQL_UPDATE_ADDRESS_DETAIL_BY_ADDRESS_ID, addressDetail.getAddressLine1(), addressDetail.getAddressLine2(), 
				addressDetail.getAddressLine3(), addressDetail.getAddressLine4(), addressDetail.getCountryState(), addressDetail.getPostCode(), 
				addressDetail.getCountry(), updatetedDate, addressDetail.getAddressId(), addressDetail.getIndividualId());

	}

	@Override
	public void updatePrimaryEmailAddress(List<EmailDetail> emailDetails) {

		Calendar calendar = Calendar.getInstance();
		java.sql.Timestamp updatetedDate = new java.sql.Timestamp(calendar.getTime().getTime());

		jdbcTemplate.batchUpdate(AccountQueryConstants.SQL_UPDATE_PRIMARY_EMAIL_BY_INDIVIDUAL_ID,
				new BatchPreparedStatementSetter() {

			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, emailDetails.get(i).getEmailAddress());
				ps.setTimestamp(2, updatetedDate);
				ps.setInt(3, emailDetails.get(i).getIndividualId());
			}

			public int getBatchSize() {
				return emailDetails.size();
			}
		});

	}
	
	@Override
	public void updateSecondaryEmailAddress(List<EmailDetail> emailDetails) {

		Calendar calendar = Calendar.getInstance();
		java.sql.Timestamp updatetedDate = new java.sql.Timestamp(calendar.getTime().getTime());

		jdbcTemplate.batchUpdate(AccountQueryConstants.SQL_UPDATE_SECONDARY_EMAIL_BY_INDIVIDUAL_ID,
				new BatchPreparedStatementSetter() {

			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, emailDetails.get(i).getEmailAddress());
				ps.setTimestamp(2, updatetedDate);
				ps.setInt(3, emailDetails.get(i).getIndividualId());
			}

			public int getBatchSize() {
				return emailDetails.size();
			}
		});

	}

	@Override
	public void updatePrimaryPhoneNumber(List<PhoneDetail> phoneDetails) {

		Calendar calendar = Calendar.getInstance();
		java.sql.Timestamp updatetedDate = new java.sql.Timestamp(calendar.getTime().getTime());

		jdbcTemplate.batchUpdate(AccountQueryConstants.SQL_UPDATE_PRIMARY_PHONE_NUMBER_BY_INDIVIDUAL_ID,
				new BatchPreparedStatementSetter() {

			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setInt(1, phoneDetails.get(i).getCountryId());
				ps.setString(2, phoneDetails.get(i).getPhoneNumber());
				ps.setTimestamp(3, updatetedDate);
				ps.setInt(4, phoneDetails.get(i).getIndividualId());
			}

			public int getBatchSize() {
				return phoneDetails.size();
			}
		});
	}
	
	@Override
	public void updateSecondaryPhoneNumber(List<PhoneDetail> phoneDetails) {

		Calendar calendar = Calendar.getInstance();
		java.sql.Timestamp updatetedDate = new java.sql.Timestamp(calendar.getTime().getTime());

		jdbcTemplate.batchUpdate(AccountQueryConstants.SQL_UPDATE_SECONDARY_PHONE_NUMBER_BY_INDIVIDUAL_ID,
				new BatchPreparedStatementSetter() {

			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setInt(1, phoneDetails.get(i).getCountryId());
				ps.setString(2, phoneDetails.get(i).getPhoneNumber());
				ps.setTimestamp(3, updatetedDate);
				ps.setInt(4, phoneDetails.get(i).getIndividualId());
			}

			public int getBatchSize() {
				return phoneDetails.size();
			}
		});
	}

	@Override
	public PersonalDetail getPersonalDetailByIndividualId(Integer individualId) {
		return jdbcTemplate.queryForObject(AccountQueryConstants.SQL_SELECT_PERSONAL_DETAIL_BY_ID,
				(rs, rowNum) -> {
					PersonalDetail personalDetail = new PersonalDetail();
					personalDetail.setIndividualId(rs.getInt("Individual_ID"));
					personalDetail.setTitle(rs.getString("Title"));
					personalDetail.setFirstName(rs.getString("First_Name"));
					personalDetail.setMiddleName(rs.getString("Middle_Name"));
					personalDetail.setLastName(rs.getString("Last_Name"));
					personalDetail.setDateOfBirth(rs.getString("DOB"));
					personalDetail.setGender(rs.getString("Gender"));
					personalDetail.setPhoneNumber(rs.getString("phone_number"));
					personalDetail.setCountryCode(rs.getString("country_code"));
					personalDetail.setEmailAddress(rs.getString("email_id"));
					personalDetail.setMotherMaidenName(rs.getString("mother_maiden_name"));
					personalDetail.setRegistrationId(rs.getInt("Registration_ID"));
					return personalDetail;
				}, individualId);
	}

	@Override
	public AddressDetail getAddressDetailByIndividualId(Integer individualId) {

		return jdbcTemplate.queryForObject(AccountQueryConstants.SQL_SELECT_ADDRESS_DETAIL_BY_INDIVIDUAL_ID,
				(rs, rowNum) -> {
					AddressDetail addressDetail = new AddressDetail();
					addressDetail.setAddressId(rs.getInt("Address_ID"));
					addressDetail.setIndividualId(rs.getInt("Individual_ID"));
					addressDetail.setAddressLine1(rs.getString("Address_Line1"));
					addressDetail.setAddressLine2(rs.getString("Address_Line2"));
					addressDetail.setAddressLine3(rs.getString("Address_Line3"));
					addressDetail.setAddressLine4(rs.getString("Address_Line4"));
					addressDetail.setCountryState(rs.getString("County_State"));
					addressDetail.setPostCode(rs.getString("PostCode"));
					addressDetail.setCountry(rs.getString("Country"));
					return addressDetail;
				}, individualId);
	}
	
	@Override
	public List<AddressDetail> getAddressDetailsByIndividualId(Integer individualId) {
		return jdbcTemplate.query(AccountQueryConstants.SQL_SELECT_ADDRESS_DETAIL_BY_INDIVIDUAL_ID,  new AddressDetailRowMapper(), individualId);
	}

	@Override
	public List<EmailDetail> getEmailDetailByIndividualId(Integer individualId) {
		return jdbcTemplate.query(AccountQueryConstants.SQL_SELECT_EMAIL_ADDRESS_BY_INDIVIDUAL_ID,  new EmailRowMapper(), individualId);
	}

	@Override
	public List<PhoneDetail> getPhoneDetailByIndividualId(Integer individualId) {
		return jdbcTemplate.query(AccountQueryConstants.SQL_SELECT_PHONE_DETAIL_BY_INDIVIDUAL_ID,  new PhoneDetailRowMapper(), individualId);
	}

	@Override
	public AccountProfile getAccountDetailsByUserLoginId(String userLoginId) {
		return jdbcTemplate.queryForObject(AccountQueryConstants.SQL_SELECT_ACCOUNT_PROFILE_BY_USER_LOGIN_ID,
				(rs, rowNum) -> {
					AccountProfile accountProfile = new AccountProfile();
					accountProfile.setAccountProfileId(rs.getInt("Account_Profile_ID"));
					accountProfile.setRegistrationId(rs.getInt("Registration_ID"));
					accountProfile.setIndividualId(rs.getInt("Individual_ID"));
					accountProfile.setAddressId(rs.getInt("Address_ID"));
					accountProfile.setDocumentId(rs.getInt("Document_ID"));
					return accountProfile;
				}, userLoginId);
	}

	@Override
	public Integer getIndividualIdByRegisteredId(Integer registeredId) {
		return jdbcTemplate.queryForObject(AccountQueryConstants.SQL_SELECT_INDIVIDUAL_ID_BY_REGISTERED_ID, new Object[] { registeredId }, Integer.class);
	}

	@Override
	public ContactDetail getContactDetails(Integer individualId) {
		// TODO Auto-generated method stub
		return null;
	}

}
