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

	@Query(value = """
	        SELECT
	            i.item_id,
	            i.item_code,
	            i.item_description,
	            u.description,
	            scd.quantity
	        FROM sales_contract_detail scd
	        INNER JOIN item i
	            ON scd.item = i.item_id
	        INNER JOIN unitmaster u
	            ON scd.unit = u.unitmaster_id
	        WHERE scd.doc_id = ?1
	        ORDER BY i.item_code
	        """, nativeQuery = true)
	    List<Object[]> getItemDropdown(String docId);

	@Query(value = """
			SELECT
			    d.item,
			    i.item_code,
			    i.item_description
			FROM sales_contract_details d
			INNER JOIN item_master i
			        ON i.item_id = d.item
			INNER JOIN sales_contract_basic s
			        ON s.salescontract_id = d.sales_contract
			WHERE s.salescontract_id = :salesContractId
			  AND s.org_id = :orgId
			  AND s.branch = :branch
			  AND s.cancel = false
			  AND s.active = true
			ORDER BY i.item_code
			""", nativeQuery = true)
			List<Object[]> getSalesContractItemDropdown(
			        @Param("salesContractId") Long salesContractId,
			        @Param("orgId") Long orgId,
			        @Param("branch") Long branch);


	

	
}
