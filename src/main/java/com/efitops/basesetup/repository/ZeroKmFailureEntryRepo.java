package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.ZeroKmFailureEntryVO;

public interface ZeroKmFailureEntryRepo
        extends JpaRepository<ZeroKmFailureEntryVO, Long> {

	@Query(nativeQuery = true, value = """
	        SELECT concat(prefix, lpad(last_no, 5, 0)) AS docid
	        FROM documenttypemapping_details
	        WHERE org_id = ?1
	          AND finyearidentifier = ?2
	          AND screen_code = ?3
	        """)
	String getZeroKmFailureEntryDocId(
	        Long orgId,
	        String financialYear,
	        String screenCode);

	List<ZeroKmFailureEntryVO> findByOrgIdAndBranch_Id(Long orgId, Long branch);
}