package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.MachineToolsScrapNoteVO;

public interface MachineToolsScrapNoteRepo extends JpaRepository<MachineToolsScrapNoteVO, Long> {

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
	String getMachineToolsScrapNoteDocId(@Param("orgId") Long orgId, @Param("financialYear") String financialYear,
			@Param("screenCode") String screenCode);

	@Query(value = """
			SELECT *
			FROM machine_tools_scrap_note_basic
			WHERE cancel = false
			AND active = true
			AND org_id = :orgId
			AND branch = :branch
			ORDER BY machine_tools_scrap_note_basic_id DESC
			""", nativeQuery = true)
	List<MachineToolsScrapNoteVO> getMachineToolsScrapNoteByOrgId(@Param("orgId") Long orgId,
			@Param("branch") Long branch);

	@Query(nativeQuery = true, value = "select * from machine_tools_scrap_note_basic where machine_tools_scrap_note_basic_id=?1 and active=1 and cancel=0")
	MachineToolsScrapNoteVO getMachineToolsScrapNoteById(Long id);
}
