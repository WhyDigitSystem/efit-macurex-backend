package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import com.efitops.basesetup.entity.CustomerComplaintEntryVO;
import com.efitops.basesetup.entity.TransportMasterVO;

public interface CustomerComplaintRepo extends JpaRepository<CustomerComplaintEntryVO, Long> {

	CustomerComplaintEntryVO getCustomerComplaintById(Long id);

	@Query(value = """
	        SELECT *
	        FROM customercomplaintmaster
	        WHERE org_id = :orgId
	          AND branch = :branch
	          AND cancel = false and active = 1
	        ORDER BY customercomplaintmaster_id
	        """, nativeQuery = true)
	List<CustomerComplaintEntryVO> getCustomerComplaintByOrgId(@Param("orgId") Long orgId,
			@Param("branch") Long branch);

//	@Query(value =
//		       "SELECT 'APPLIANCES' AS type " +
//		       "UNION " +
//		       "SELECT 'BOSCH' AS type " +
//		       "ORDER BY type",
//		       nativeQuery = true)
//		List<Object[]> getTypeDropdown();

	
}
