package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.GateInwardEntryVO;

public interface GateInwardEntryRepo extends JpaRepository<GateInwardEntryVO, Long> {

	List<GateInwardEntryVO> findByBranchIdAndOrgIdAndCancelFalse(
	        Long branch,
	        Long orgId);

//	customername dropdown 
	@Query(value = "SELECT " +
	        "c.customer_name, " +
	        "c.address, " +
	        "c.customer_id, " +
	        "c.customer_code " +
	        "FROM customer_header c " +
	        "WHERE c.cancel = FALSE " +
	        "AND c.active = TRUE " +
	        "AND c.branch = :branch " +
	        "AND c.org_id = :orgId " +
	        "ORDER BY c.customer_name",
	        nativeQuery = true)
	List<Object[]> getCustomerNameDropdownForGateInwardEntry(
	        @Param("branch") Long branch,
	        @Param("orgId") Long orgId);
}
