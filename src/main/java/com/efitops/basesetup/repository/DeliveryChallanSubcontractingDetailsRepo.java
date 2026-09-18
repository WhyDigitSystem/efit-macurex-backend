package com.efitops.basesetup.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DeliveryChallanSubcontractingDetailsVO;
import com.efitops.basesetup.entity.DeliveryChallanSubcontractingVO;

@Repository
public interface DeliveryChallanSubcontractingDetailsRepo extends JpaRepository<DeliveryChallanSubcontractingDetailsVO, Long>{

	List<DeliveryChallanSubcontractingDetailsVO> findByDeliveryChallanSubcontracting(
			DeliveryChallanSubcontractingVO deliveryChallanSubcontractingVO);

	@Query(value = """
	        SELECT COALESCE(SUM(d.issue_qty), 0)
	        FROM deliverychallan_subcontracting_details d
	        INNER JOIN deliverychallan_subcontracting h
	            ON h.deliverychallan_subcontracting_id =
	               d.deliverychallan_subcontracting_id
	        WHERE h.org_id = :orgId
	          AND h.branch = :branch
	          AND h.job_order_no = :jobOrderNo
	          AND d.outgoing_item = :item
	        """, nativeQuery = true)
	BigDecimal getTotalSuppliedQtyforJobOrderClose(
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch,
	        @Param("jobOrderNo") String jobOrderNo,
	        @Param("item") Long item);
	

}
