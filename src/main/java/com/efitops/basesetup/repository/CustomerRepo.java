package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.CustomerVO;

@Repository
public interface CustomerRepo extends JpaRepository<CustomerVO, Long>{

	boolean existsByCustomerNameAndOrgId(String customerName, Long orgId);

	boolean existsByGstNoAndOrgId(String gstNo, Long orgId);

	@Query(value = """
	        SELECT *
	        FROM customer_header
	        WHERE org_id = :orgId
	          AND branch = :branch
	          AND active = 1
	          AND cancel = 0
	        ORDER BY customer_id DESC
	        """, nativeQuery = true)
	List<CustomerVO> findByOrgIdAndBranch(@Param("orgId") Long orgId,
	                                      @Param("branch") Long branch);
}
