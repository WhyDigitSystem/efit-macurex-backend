package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.StockTransferChallanVO;
import com.efitops.basesetup.entity.TransportMasterVO;

public interface StockTransferChallanRepo extends JpaRepository<StockTransferChallanVO, Long>{

	@Query(value = """
	        SELECT *
	        FROM stock_transfer_chellan_basic
	        WHERE org_id = :orgId
	          AND branch = :branch
	          AND cancel = false and active = 1
	        ORDER BY stock_transfer_chellan_basic_id
	        """, nativeQuery = true)
	List<StockTransferChallanVO> getStockTransferChallanByOrgId(@Param("orgId") Long orgId,
	                                             @Param("branch") Long branch);

}
