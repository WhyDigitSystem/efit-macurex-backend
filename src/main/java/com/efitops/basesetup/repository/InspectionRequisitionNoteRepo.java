package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.InspectionRequisitionNoteVO;

@Repository
public interface InspectionRequisitionNoteRepo extends JpaRepository<InspectionRequisitionNoteVO, Long>{

	@Query(value = """
	        SELECT *
	        FROM inspection_requisition_note
	        WHERE org_id = :orgId
	          AND branch = :branch
	          AND cancel = 0
	        ORDER BY inspection_requisition_note_id DESC
	        """, nativeQuery = true)
	List<InspectionRequisitionNoteVO> findByOrgIdAndBranch(
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch);
}
