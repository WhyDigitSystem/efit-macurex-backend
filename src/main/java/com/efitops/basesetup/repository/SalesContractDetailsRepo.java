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
//	        WHERE scd.salescontract_id = ?1
//	        ORDER BY i.item_code
//	        """, nativeQuery = true)
//	    List<Object[]> getItemDropdown(Long salesContractId);

	@Query(value = "SELECT " + "i.item_id, " + "i.item_code, " + "i.item_description, " + "u.description AS unit, "
			+ "scd.quantity " + "FROM sales_contract_detail scd "
			+ "INNER JOIN sales_contract_basic scb ON scd.salescontract_id = scb.salescontract_id "
			+ "INNER JOIN item i ON scd.item = i.item_id " + "INNER JOIN unitmaster u ON scd.unit = u.unitmaster_id "
			+ "WHERE scb.customer_contract_no = :docId " +

			"UNION ALL " +

			"SELECT " + "i.item_id, " + "i.item_code, " + "i.item_description, " + "u.description AS unit, "
			+ "oad.quantity " + "FROM orderacceptance_detail oad "
			+ "INNER JOIN orderacceptance oa ON oad.orderacceptance_id = oa.orderacceptance_id "
			+ "INNER JOIN item i ON oad.item = i.item_id " + "INNER JOIN unitmaster u ON oad.unit = u.unitmaster_id "
			+ "WHERE oa.doc_id = :docId " + "ORDER BY item_code", nativeQuery = true)
	List<Object[]> getItemDropdown(@Param("docId") String docId);

}
