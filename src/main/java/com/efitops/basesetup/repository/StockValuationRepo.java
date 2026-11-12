package com.efitops.basesetup.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.StockValuationVO;
@Repository
public interface StockValuationRepo extends JpaRepository<StockValuationVO, Long> {

	@Query(value = """
		    SELECT COALESCE(
		        SUM(
		            CASE
		                WHEN plus_or_minus = 'p' THEN quantity
		                WHEN plus_or_minus = 'm' THEN -quantity
		                ELSE 0
		            END
		        ),
		        0
		    )
		    FROM stock_value
		    WHERE stock_part_no = :itemId
		      AND locdetails_id = :locationId
		      AND org_id = :orgId
		      AND branch_id = :branchId
		      AND active = 1
		      AND cancel = 0
		    """, nativeQuery = true)
		BigDecimal getAvailableStockQtyForGRN(
		        @Param("itemId") Long itemId,
		        @Param("locationId") Long locationId,
		        @Param("orgId") Long orgId,
		        @Param("branchId") Long branchId);

}
