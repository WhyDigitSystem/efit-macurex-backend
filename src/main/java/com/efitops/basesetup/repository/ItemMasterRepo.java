package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ItemMasterVO;

@Repository
public interface ItemMasterRepo extends JpaRepository<ItemMasterVO, Long> {

	@Query(nativeQuery = true, value = "select * from item where item_id=?1 and active=1 and cancel=0")
	ItemMasterVO getItemMasterById(Long id);

	@Query(nativeQuery = true, value = "select * from item where org_id=?1  and branch=?2 and active=1 and cancel=0")
	List<ItemMasterVO> getItemMasterByOrgId(Long orgId, Long branchId);

	boolean existsByItemCodeAndOrgIdAndBranchId(String itemCode, Long orgId, Long branchId);

	// dropdown for item

//	@Query(value = "SELECT item_id, item_code " + "FROM item " + "WHERE cancel = false "
//			+ "ORDER BY item_code", nativeQuery = true)
//	List<Object[]> getItem();

	@Query(value = "SELECT item_id, item_code, item_description, customer_part_no FROM item "
			+ "WHERE org_id = ?1  and branch=?2 AND cancel = false and active=1 ", nativeQuery = true)
	List<Object[]> getItemDetailsforCustomerComplaint(Long orgId, Long branch);

	@Query(value = """
				SELECT
			    i.item_id,
			    i.item_code,
			    i.item_description,
			    u.unit_id,
			    i.min_sell_price,
			    h.hsn,
			    i.customer_part_no,
			    gr.rate,
			    gr.cgst,
			    gr.sgst,
			    gr.igst,u.unitmaster_id
			FROM item i
			INNER JOIN unitmaster u
			    ON u.unitmaster_id = i.primary_unit
			INNER JOIN hsn h
			    ON h.hsn_id = i.hsn_code
			LEFT JOIN gstratemaster gr
			    ON gr.hsn_sac_code = h.hsn_id
			    AND gr.active = 1
			    AND gr.cancel = 0
			    AND gr.org_id = i.org_id
			    AND gr.branch = i.branch
			WHERE i.cancel = 0
			  AND i.org_id = :orgId
			  AND i.branch = :branch
			  AND EXISTS (
			        SELECT 1
			        FROM listofvaluesdetails l
			        WHERE l.listofvaluesdetails_id = i.item_type
			          AND l.value_code = 'FG'
			  )
			ORDER BY i.item_code;
								    """, nativeQuery = true)
	List<Object[]> getFinishedGoodsItems(@Param("orgId") Long orgId, @Param("branch") Long branch);

	@Query(value = "SELECT * FROM item WHERE item_id = ?1", nativeQuery = true)
	ItemMasterVO getItemById(Long itemId);

}
