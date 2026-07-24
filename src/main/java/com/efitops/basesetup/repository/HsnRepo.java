package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.HsnVO;
import com.efitops.basesetup.entity.ListOfValuesVO;

public interface HsnRepo extends JpaRepository<HsnVO, Long> {
	
	List<HsnVO> findByOrgId(Long orgId);

	boolean existsByOrgIdAndListofvaluesAndHsnIgnoreCase(Long orgId, ListOfValuesVO listOfValuesVO, String hsn);


}
