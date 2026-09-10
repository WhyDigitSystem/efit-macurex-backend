package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.OperationMasterVO;

public interface OperationMasterRepo extends JpaRepository<OperationMasterVO, Long> {

	@Query("SELECT om FROM OperationMasterVO om WHERE om.orgId = ?1 " +
		       "AND om.cancel = false AND om.active = true")
		List<OperationMasterVO> getOperationMasterByOrgId(Long orgId);
	
	
	@Query(value = """
	        SELECT ob.operation_master_basic_id,
	               ob.operation_id,
	               ob.description,
	               m.machine_instrument_no,
	               m.machine_instrument_name,
	               t.tool_no,
	               t.tool_description
	        FROM operation_master_basic ob
	        JOIN operation_master_machine_details md
	             ON md.operation_master_basic_id =
	                ob.operation_master_basic_id
	        JOIN machine_equipments_master m
	             ON m.machine_equipments_master_id = md.machine
	        LEFT JOIN operation_master_tool_details td
	             ON td.operation_master_basic_id =
	                ob.operation_master_basic_id
	        LEFT JOIN tool_master_basic t
	             ON t.tool_master_basic_id = td.tool_id
	        WHERE ob.cancel = 0
	          AND ob.active = 1
	          AND m.org_id = :orgId
	          AND m.branch = :branch
	        ORDER BY ob.operation_id
	        """, nativeQuery = true)
	List<Object[]> getOperationDropdownforProcessSheetCompRouting(
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch);
}