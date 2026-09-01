package com.efitops.basesetup.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.StockTransferGrnVO;

@Repository
public interface StockTransferGrnRepo extends JpaRepository<StockTransferGrnVO, Long> {

	@Query(nativeQuery = true, value = "select * from stock_transfer_grn_basic where org_id=?1 and branch=?2 and active=1 and cancel=0")
	List<StockTransferGrnVO> getStockTransferGrnByOrgId(Long orgId, Long branch);

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
	String getStockTransferGrnDocId(Long orgId, String financialYear, String screenCode);

	@Query(nativeQuery = true, value = "select * from stock_transfer_grn_basic where stock_transfer_grn_basic_id=?1 and active=1 and cancel=0")
	StockTransferGrnVO getStockTransferGrnById(Long id);

	@Query(nativeQuery = true, value = "select multiplication_factor from uomconversion where org_id=?1 and from_unit=?2 and to_unit=?3")
	Set<Object[]> getConversionFactorAmount(Long orgId, BigDecimal poQty, BigDecimal receivedQty);

	@Query(nativeQuery = true, value = "SELECT      g.doc_id,      g.doc_date, \r\n"
			+ "			    g.gate_inward_entry_basic_id,g.invoice_number FROM     gate_inward_entry_basic g WHERE\r\n"
			+ "			    g.org_id = ?1     AND g.branch = ?2     AND g.customer = ?3  \r\n"
			+ "			    AND g.active = 1     AND g.cancel = 0     AND (\r\n"
			+ "			        g.doc_id NOT IN (             SELECT DISTINCT gate_pass_no\r\n"
			+ "			            FROM stock_transfer_grn_basic             WHERE org_id = ?1 \r\n"
			+ "			                AND branch = ?2                 AND supplier_code = ?3  \r\n"
			+ "			                AND cancel = 0                 AND active = 1\r\n"
			+ "			                AND gate_pass_no IS NOT NULL)OR         g.doc_id IN (\r\n"
			+ "			            SELECT DISTINCT gate_pass_no             FROM stock_transfer_grn_basic\r\n"
			+ "			            WHERE org_id = ?1                 AND branch = ?2\r\n"
			+ "			                AND supplier_code =?3                   AND cancel = 1\r\n"
			+ "			                AND active = 1                 AND gate_pass_no IS NOT NULL))")
	Set<Object[]> getGatePassDocIdDetailsForStockTransfer(Long orgId, Long branch, Long supplierCode);

}
