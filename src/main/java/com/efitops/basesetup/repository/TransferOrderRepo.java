
package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.TransferOrderVO;

public interface TransferOrderRepo extends JpaRepository<TransferOrderVO, Long> {
	
	
	  List<TransferOrderVO> findByOrgIdAndCancelFalse(Long orgId);
	  
	  
	  
	  @Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid "
		        + "from documenttypemapping_details "
		        + "where org_id=?1 and fin_year=?2 and screen_code=?3")
		String getTransferOrderDocId(
		        Long orgId,
		        String financialYear,
		        String screenCode);
	  
	  
	  @Query(value = """
	            SELECT
	                i.item_id AS id,
	                i.item_code AS name,
	                i.item_description AS description,
	                um.unitmaster_id AS unitId,
	                um.unit_id AS unitCode,
	                um.description AS unitDescription
	            FROM item i
	            INNER JOIN unitmaster um
	                ON um.unitmaster_id = i.primary_unit
	            WHERE i.org_id = :orgId
	              AND um.org_id = :orgId
	              AND i.active = TRUE
	              AND i.cancel = FALSE
	              AND um.active = TRUE
	              AND um.cancel = FALSE
	            ORDER BY i.item_id DESC
	            """, nativeQuery = true)
	    List<Object[]> getTransferOrderItemDropdown(
	            @Param("orgId") Long orgId);
	    
	    @Query(value = """
	            SELECT
	                mtb.machine_tool_breakdown_basic_id AS id,
	                mtb.doc_id AS name
	            FROM machine_tool_breakdown_basic mtb
	            WHERE mtb.org_id = :orgId
	              AND mtb.branch = :branch
	              AND mtb.active = TRUE
	              AND mtb.cancel = FALSE
	            ORDER BY mtb.machine_tool_breakdown_basic_id DESC
	            """, nativeQuery = true)
	    List<Object[]> getBreakdownNoDropdownForGateOutwardEntry(
	            @Param("orgId") Long orgId,
	            @Param("branch") Long branch);
	    
	    
	    @Query(value = """
	            SELECT 'SS' AS id, 'SS' AS name
	            FROM dual
	            WHERE :orderType = 'Sub Contracted'
	              AND :orgId IS NOT NULL

	            UNION ALL

	            SELECT 'Production' AS id, 'Production' AS name
	            FROM dual
	            WHERE :orderType = 'Manufactured'
	              AND :orgId IS NOT NULL

	            UNION ALL

	            SELECT 'PO' AS id, 'PO' AS name
	            FROM dual
	            WHERE :orderType = 'Bought Out'
	              AND :orgId IS NOT NULL
	            """, nativeQuery = true)
	    List<Object[]> getTypeDropdownByOrderTypeForTransferOrder(
	            @Param("orderType") String orderType,
	            @Param("orgId") Long orgId);
	    
	    
	    

}
