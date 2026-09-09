package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.EngineeringChangeNoteVO;

@Repository
public interface EngineeringChangeNoteRepo extends JpaRepository<EngineeringChangeNoteVO, Long> {

	@Query(nativeQuery = true, value = "select * from engineering_change_note_basic whereengineering_change_note_basic_id=?1 and active=1 and cancel=0")
	EngineeringChangeNoteVO getEngineeringChangeNoteById(Long id);

	@Query(nativeQuery = true, value = "select * from engineering_change_note_basic where org_id=?1 and branch=?2 and active=1 and cancel=0")
	List<EngineeringChangeNoteVO> getEngineeringChangeNoteByOrgId(Long orgId, Long branch);

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
	String getEngineeringChangeNoteDocId(Long orgId, String financialYear, String screenCode);

}
