package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.BomCorrectionRequestNoteVO;

@Repository
public interface BomCorrectionRequestNoteRepo extends JpaRepository<BomCorrectionRequestNoteVO, Long>{

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
	String getBomCorrectionRequestNoteDocId(Long orgId, String financialYear, String screenCode);

	@Query(value = """
	        SELECT *
	        FROM bom_correction_request_note
	        WHERE org_id = :orgId
	          AND branch = :branch
	          AND cancel = 0
	        ORDER BY bom_correction_request_note_id DESC
	        """, nativeQuery = true)
	List<BomCorrectionRequestNoteVO> findByOrgIdAndBranch(
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch);
}
