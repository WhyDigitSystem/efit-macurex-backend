package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.efitops.basesetup.entity.ProblemSolvingEntryVO;

public interface ProblemSolvingEntryRepo extends JpaRepository<ProblemSolvingEntryVO, Long> {

//	String getProblemSolvingEntryDocId(Long orgId, String financialYear, String screenCode);

	@Query(value = """
			SELECT CONCAT(prefix, LPAD(last_no, 5, '0')) AS docid
			FROM documenttypemapping_details
			WHERE org_id = ?1
			  AND fin_year = ?2
			  AND screen_code = ?3
			""", nativeQuery = true)
	String getProblemSolvingEntryDocId(Long orgId, String financialYear, String screenCode);

//	dropdown for team member1 team member2, and preparedby and responsible
	@Query(value = """
			SELECT
			    e.employeemaster_id,
			    e.employee_id,
			    e.emp_name
			FROM employeemaster e
			INNER JOIN branch b
			    ON e.branch = b.branch_id
			INNER JOIN department d
			    ON e.department = d.departmentid
			WHERE e.branch = ?1
			  AND e.department = ?2
			  AND e.org_id = ?3
			  AND e.active = TRUE
			  AND e.cancel = FALSE
			  AND b.active = TRUE
			  AND b.cancel = FALSE
			  AND d.active = TRUE
			  AND d.cancel = FALSE
			ORDER BY e.emp_name
			""", nativeQuery = true)
	List<Object[]> getTeamMemberDropdownForProblemSolvingEntry(Long branch, Long department, Long orgId);

}
