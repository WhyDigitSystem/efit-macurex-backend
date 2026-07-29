package com.efitops.basesetup.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.efitops.basesetup.entity.CountryVO;
import com.efitops.basesetup.entity.CurrencyVO;

public interface CurrencyRepo extends JpaRepository<CurrencyVO, Long> {

	@Query(value = "select * FROM currency where org_id=?1 and active=1 and cancel=0", nativeQuery = true)
	List<CurrencyVO> findAll(Long orgid);

	@Query(value = "SELECT \r\n" + "    ROW_NUMBER() OVER () AS id,\r\n" + "    currency,\r\n" + "    currencydesc \r\n"
			+ "FROM \r\n" + "    currency \r\n" + "WHERE \r\n" + "    orgid =?1\r\n"
			+ "    AND active = 1 group by currency,currencydesc", nativeQuery = true)
	Set<Object[]> findCurrencyForFullGrid(Long orgId);

	boolean existsByOrgIdAndCountryAndCurrencyIgnoreCase(Long orgId, CountryVO countryVO, String currency);

	boolean existsByOrgIdAndCountryAndCurrencyDescriptionIgnoreCase(Long orgId, CountryVO country,
			String currencyDescription);

	boolean existsByOrgIdAndCountryAndSubCurrencyIgnoreCase(Long orgId, CountryVO country, String subCurrency);

	@Query(value = "select currency,country FROM currency where org_id=?1 and country=?2", nativeQuery = true)
	Set<Object[]> getCurrencyForPartyMaster(Long orgId, String country);
	
	@Query(value = "SELECT currency_id, currency, main_currencysymbol " +
	        "FROM currency " +
	        "WHERE org_id = ?1 " +
	        "AND active = 1",
	        nativeQuery = true)
	List<Object[]> getCurrency(Long orgId);

}
