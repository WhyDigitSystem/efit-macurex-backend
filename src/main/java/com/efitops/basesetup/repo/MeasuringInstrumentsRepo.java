package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ItemVO;
import com.efitops.basesetup.entity.MeasuringInstrumentsVO;

@Repository
public interface MeasuringInstrumentsRepo extends JpaRepository<MeasuringInstrumentsVO, Long>{



	@Query(nativeQuery = true,value="select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and  screencode=?4")
	String getMeasuringInstrumentsDocId(Long orgId, String finyear, String branchCode, String screenCode);

	boolean existsByInstrumentNameAndOrgId(String instrumentName, Long orgId);

	boolean existsByInstrumentCodeAndOrgId(String instrumentCode, Long orgId);

	@Query(nativeQuery = true, value = "select itemname,itemdesc from item where orgid=?1 and materialtype='INSTRUMENT'  and  active=1")
	Set<Object[]> findInstrumentNameFromItemMaster(Long orgId);

	@Query(nativeQuery = true, value = "select * from measuringinstruments where orgid=?1 and branchcode=?2 ")
	List<MeasuringInstrumentsVO> findMeasuringInstrumentsByOrgId(Long orgId, String branchCode);

	@Query(nativeQuery = true, value = "select * from measuringinstruments where measuringinstrumentsid=?1")
	List<MeasuringInstrumentsVO> findMeasuringInstrumentsById(Long id);

	boolean existsByInstrumentNameIgnoreCaseAndInstrumentCodeIgnoreCaseAndOrgId(String instrumentName,
			String instrumentCode, Long orgId);

	boolean existsByInstrumentNameIgnoreCaseAndInstrumentCodeIgnoreCaseAndOrgIdAndIdNot(String instrumentName,
			String instrumentCode, Long orgId, Long id);


}
