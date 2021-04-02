package com.nucigent.elms.countrycode.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.nucigent.elms.countrycode.domain.CountryCode;
import com.nucigent.elms.countrycode.domain.CountryCodeRowMapper;
import com.nucigent.elms.countrycode.helper.CountryCodeQueryConstants;

@Repository
public class CountryCodeDaoImpl implements CountryCodeDao {

	Logger logger = LoggerFactory.getLogger(CountryCodeDaoImpl.class);

	@Autowired private JdbcTemplate jdbcTemplate;
	@Autowired private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


	@Override
	public List<CountryCode> getAllCountryCodes() {
		return jdbcTemplate.query(CountryCodeQueryConstants.SQL_SELECT_ALL_COUNTRY_PHONE_CODE,  new CountryCodeRowMapper());
	}

	@Override
	public CountryCode getCountryPhoneCodeById(Integer countryId) {
		return jdbcTemplate.queryForObject(CountryCodeQueryConstants.SQL_SELECT_COUNTRY_PHONE_CODE_BY_ID,
				(rs, rowNum) -> {
					CountryCode countryCode = new CountryCode();
					countryCode.setCountryId(rs.getInt("country_id"));
					countryCode.setCountryPhoneCode(rs.getInt("country_ph_code"));
					return countryCode;
				}, countryId);
	}

	@Override
	public List<CountryCode> getCountryIdByCountryCodes(List<String> countryCodes) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("countryCodeParam", countryCodes);
		//return jdbcTemplate.query(CountryCodeQueryConstants.SQL_SELECT_COUNTRY_ID_BY_COUNTRY_CODES,  new CountryCodeRowMapper(), countryCodes);
		List<CountryCode> list = namedParameterJdbcTemplate.query(CountryCodeQueryConstants.SQL_SELECT_COUNTRY_ID_BY_COUNTRY_CODES,
				parameters, new RowMapper<CountryCode>() {
			@Override
			public CountryCode mapRow(ResultSet resultSet, int i) throws SQLException {
				return toCountryCode(resultSet);
			}
		});
		return list;
	}

	private CountryCode toCountryCode(ResultSet resultSet) throws SQLException {
		CountryCode countryCode = new CountryCode();
		countryCode.setCountryId(resultSet.getInt("country_id"));
		countryCode.setCountryPhoneCode(resultSet.getInt("country_ph_code"));
		return countryCode;
	}

	@Override
	public List<CountryCode> getCountryIdByCountryPhoneCodeAndCountry(Integer countryPhCode, String capital) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("countryCodeParam", countryPhCode);
		parameters.addValue("capitalParam", capital);
		//return jdbcTemplate.query(CountryCodeQueryConstants.SQL_SELECT_COUNTRY_ID_BY_COUNTRY_CODES,  new CountryCodeRowMapper(), countryCodes);
		List<CountryCode> list = namedParameterJdbcTemplate.query(CountryCodeQueryConstants.SQL_SELECT_COUNTRY_ID_BY_COUNTRY_PHONE_CODE_AND_CAPITAL,
				parameters, new RowMapper<CountryCode>() {
			@Override
			public CountryCode mapRow(ResultSet resultSet, int i) throws SQLException {
				return toCountryCode(resultSet);
			}
		});
		return list;
	}
	
	@Override
	public List<CountryCode> getCountryIdByCountryId(List<String> countryCodes) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("countryCodeParam", countryCodes);
		//return jdbcTemplate.query(CountryCodeQueryConstants.SQL_SELECT_COUNTRY_ID_BY_COUNTRY_CODES,  new CountryCodeRowMapper(), countryCodes);
		List<CountryCode> list = namedParameterJdbcTemplate.query(CountryCodeQueryConstants.SQL_SELECT_COUNTRY_ID_BY_COUNTRY_ID,
				parameters, new RowMapper<CountryCode>() {
			@Override
			public CountryCode mapRow(ResultSet resultSet, int i) throws SQLException {
				return toCountryCode(resultSet);
			}
		});
		return list;
	}

}
