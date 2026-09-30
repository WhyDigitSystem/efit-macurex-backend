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

}
