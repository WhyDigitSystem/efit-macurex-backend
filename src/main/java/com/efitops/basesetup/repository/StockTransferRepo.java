package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.StockTransferVO;

@Repository
public interface StockTransferRepo extends JpaRepository<StockTransferVO, Long> {

    @Query(nativeQuery = true, value = "select * from stock_transfer_basic where stock_transfer_basic_id=?1 and active=1 and cancel=0")
	StockTransferVO getStockTransferById(Long id);

    @Query(nativeQuery = true, value = "select * from stock_transfer_basic where org_id=?1 and branch=?2 and active=1 and cancel=0")
	List<StockTransferVO> getStockTransferByOrgId(Long orgId, Long branch);

    @Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
	String getStockTransferDocId(Long orgId, String financialYear, String screenCode);

}
