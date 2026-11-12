package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DeliveryChallanCapitalItemsVO;

@Repository
public interface DeliveryChallanCapitalItemsRepo extends JpaRepository<DeliveryChallanCapitalItemsVO, Long>{

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
	String getDeliveryChallanCapitalItemsDocId(Long orgId, String financialYear, String screenCode);

	@Query(value = """
	        SELECT *
	        FROM deliverychallan_capital_items
	        WHERE org_id = :orgId
	          AND branch = :branch
	          AND active = 1
	          AND cancel = 0
	        ORDER BY deliverychallan_capital_items_id DESC
	        """, nativeQuery = true)
	List<DeliveryChallanCapitalItemsVO> findByOrgIdAndBranch(
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch);
	
}
