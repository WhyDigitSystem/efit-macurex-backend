package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.efitops.basesetup.entity.CityVO;

public interface CityRepo extends JpaRepository<CityVO, Long> {
	@Query("select a from CityVO a where a.orgId=?1")
	List<CityVO> findAll(Long orgid);

	@Query(value = "SELECT * FROM city WHERE org_id = ?1 AND state = ?2", nativeQuery = true)
	List<CityVO> getAllCitiesByState(Long orgid, Long state);

//	boolean existsByCityCodeAndCityNameAndOrgId(String cityCode, String cityName, Long orgId);

	boolean existsByCityCodeAndOrgId(String cityCode, Long orgId);

	boolean existsByCityNameAndOrgId(String cityName, Long orgId);
	
	@Query(value = "SELECT * FROM city WHERE city_id = ?1", nativeQuery = true)
	CityVO getCityById(Long id);
}


