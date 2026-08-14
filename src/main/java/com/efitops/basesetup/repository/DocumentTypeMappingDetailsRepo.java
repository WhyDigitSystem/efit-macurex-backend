package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;
import com.efitops.basesetup.entity.DocumentTypeMappingVO;

@Repository
public interface DocumentTypeMappingDetailsRepo extends JpaRepository<DocumentTypeMappingDetailsVO, Long> {

	List<DocumentTypeMappingDetailsVO> findByDocumentTypeMappingMasterVOId(Long id);

	@Query(nativeQuery = true, value = "select * from documenttypemapping_details where org_id=?1 and screen_code=?2")
	DocumentTypeMappingDetailsVO findByOrgIdScreenCode(Long orgId, String screenCode);

	List<DocumentTypeMappingDetailsVO> findByDocumentTypeMappingMasterVO(DocumentTypeMappingVO masterVO);

	@Query(nativeQuery = true, value = "select * from documenttypemapping_details where org_id=?1 and fin_year=?2 and screen_code=?3")
	DocumentTypeMappingDetailsVO findByOrgIdSAndFinYearAndScreenCode(Long orgId, String financialYear,
			String screenCode);

}