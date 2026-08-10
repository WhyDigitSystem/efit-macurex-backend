package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.BranchVO;

@Repository
public interface BranchRepo extends JpaRepository<BranchVO, Long>{

	boolean existsByBranchNameAndOrgId(String branchName, Long orgId);

	boolean existsByBranchCodeAndOrgId(String branchCode, Long orgId);

	@Query(value = """
	        SELECT *
	        FROM branch
	        WHERE org_id = :orgId
	          AND cancel = false and active = 1
	        ORDER BY code 
	        """, nativeQuery = true)
	List<BranchVO> getBranchByOrgId(Long orgId);
	
	@Query(value = "SELECT branch_id, branch, branch_name " +
            "FROM branch " +
            "WHERE org_id=?1 and cancel = false  and active=1 " +
            "ORDER BY branch",
    nativeQuery = true)
List<Object[]> getAllBranch(Long orgId);

}
