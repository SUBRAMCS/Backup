package com.nucigent.elms.countrycode.helper;

public class CountryCodeQueryConstants {

	public static final String SQL_SELECT_ALL_COUNTRY_PHONE_CODE = "SELECT country_id, country_name, country_ph_code, upper(top_level_domain) as top_level_domain FROM country_code order by country_ph_code";
	
	public static final String SQL_SELECT_COUNTRY_PHONE_CODE_BY_ID = "SELECT country_id, country_ph_code FROM country_code";

	public static final String SQL_SELECT_COUNTRY_ID_BY_COUNTRY_CODES = "SELECT country_id, country_ph_code FROM country_code where country_ph_code in (:countryCodeParam)";
	
	public static final String SQL_SELECT_COUNTRY_ID_BY_COUNTRY_PHONE_CODE_AND_CAPITAL = "SELECT * from country_code where country_ph_code = :countryCodeParam and capital = :capitalParam";
	
	public static final String SQL_SELECT_COUNTRY_ID_BY_COUNTRY_ID = "SELECT country_id, country_ph_code FROM country_code where country_Id in (:countryCodeParam)";
	
}
