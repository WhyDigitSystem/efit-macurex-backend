package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.QualityScrapNoteVO;

public interface QualityScrapNoteRepo extends JpaRepository<QualityScrapNoteVO, Long>{

	  @Query(value = """
	            SELECT CONCAT(
	                dtmd.prefix,
	                LPAD(dtmd.last_no + 1, 5, '0')
	            )
	            FROM documenttypemapping_details dtmd
	            WHERE dtmd.org_id = :orgId
	              AND dtmd.fin_year = :financialYear
	              AND dtmd.screen_code = :screenCode
	            """, nativeQuery = true)
	    String getQualityScrapNoteDocId(
	            @Param("orgId") Long orgId,
	            @Param("financialYear") String financialYear,
	            @Param("screenCode") String screenCode);


	    @Query(value = """
	            SELECT *
	            FROM quality_scrap_note_basic
	            WHERE active = true
	            AND cancel = false
	            AND branch = :branch 
	            AND org_id = :orgId
	            ORDER BY quality_scrap_note_basic_id DESC
	            """, nativeQuery = true)
	    List<QualityScrapNoteVO> findByOrgId(
	            @Param("orgId") Long orgId,
	            @Param("branch") Long branch);
}
