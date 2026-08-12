package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SalesContractDetailsVO;
import com.efitops.basesetup.entity.SalesContractVO;

@Repository
public interface SalesContractDetailsRepo extends JpaRepository<SalesContractDetailsVO, Long> {

	List<SalesContractDetailsVO> findBySalesContract(SalesContractVO salesContractVO);

//	@Query(value = """
//	        SELECT
//	            i.item_id,
//	            i.item_code,
//	            i.item_description,
//	            u.description,
//	            scd.quantity
//	        FROM sales_contract_detail scd
//	        INNER JOIN item i
//	            ON scd.item = i.item_id
//	        INNER JOIN unitmaster u
//	            ON scd.unit = u.unitmaster_id
//	        WHERE scd.doc_id = ?1
//	        ORDER BY i.item_code
//	        """, nativeQuery = true)
//	    List<Object[]> getItemDropdown(String docId);
	
	@Query(value = """
		    SELECT
		        i.item_id,
		        i.item_code,
		        i.item_description,
		        u.description,
		        scd.quantity,
		        scd.item,
		        scd.unit
		    FROM sales_contract_basic scb
		    INNER JOIN sales_contract_detail scd
		        ON scb.salescontract_id = scd.salescontract_id
		    INNER JOIN item i
		        ON scd.item = i.item_id
		    INNER JOIN unitmaster u
		        ON scd.unit = u.unitmaster_id
		    WHERE scb.doc_id = ?1  and scb.org_id=?2 and scb.branch=?3 

		    UNION ALL

		    SELECT
		        i.item_id,
		        i.item_code,
		        i.item_description,
		        u.description,
		        oad.quantity,
		        oad.item,
		        oad.unit
		    FROM order_acceptance_basic oab
		    INNER JOIN order_acceptance_detail oad
		        ON oab.order_acceptance_basic_id = oad.order_acceptance_basic_id
		    INNER JOIN item i
		        ON oad.item = i.item_id
		    INNER JOIN unitmaster u
		        ON oad.unit = u.unitmaster_id
		    WHERE oab.doc_id = ?1 and oab.org_id=?2 and oab.branch=?3

		    ORDER BY item_code
		    """, nativeQuery = true)
		List<Object[]> getSalesDeliveryScheduleByItemDropdown(String docId, Long orgId, Long branch);




	

	
}
