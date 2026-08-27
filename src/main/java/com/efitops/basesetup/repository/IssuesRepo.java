package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.IssuesVO;

public interface IssuesRepo extends JpaRepository<IssuesVO, Long> {

	List<IssuesVO> findByOrgIdAndBranch(Long orgId, BranchVO branchVO);
	
	
	@Query(value = """
	        SELECT
	            l.id,
	            l.location_id,
	            l.location_name
	        FROM location l
	        LEFT JOIN listofvaluesdetails lov
	            ON lov.listofvaluesdetails_id = l.location_type
	        WHERE l.cancel = 0
	          AND l.branch = :branch
	          AND UPPER(lov.value_description) = 'STORES'
	        ORDER BY l.location_id
	        """, nativeQuery = true)
	List<Object[]> getIssueFromLocationDropdown(
	        @Param("branch") Long branch);
	        
	
	
	@Query(value = """
	        SELECT
	            l.id,
	            l.location_id,
	            l.location_name
	        FROM location l
	        LEFT JOIN listofvaluesdetails lov
	            ON lov.listofvaluesdetails_id = l.location_type
	        WHERE l.cancel = 0
	          AND l.branch = :branch
	          AND UPPER(lov.value_description) <> 'SUB CONTRACTOR'
	          AND l.id <> :issueFrom
	        ORDER BY l.location_id
	        """, nativeQuery = true)
	List<Object[]> getIssueToLocationDropdown(
	        @Param("branch") Long branch,
	        @Param("issueFrom") Long issueFrom);

}
