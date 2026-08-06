package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SalesContractAmdDetailsVO;
import com.efitops.basesetup.entity.SalesContractAmendmentVO;
import com.efitops.basesetup.entity.SalesContractDetailsVO;
import com.efitops.basesetup.entity.SalesContractVO;

@Repository
public interface SalesContractDetailsRepo extends JpaRepository<SalesContractDetailsVO, Long>{




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
	        WHERE scd.salescontract_id = ?1
	        ORDER BY i.item_code
	        """, nativeQuery = true)
	    List<Object[]> getItemDropdown(Long salesContractId);

	
}
