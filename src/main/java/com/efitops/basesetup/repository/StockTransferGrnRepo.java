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

	@Query(nativeQuery = true, value = "select p.doc_id,p.doc_date from purchase_order_basic p where p.org_id=?1\r\n"
			+ "			and p.branch=?2 and p.active=1 and p.cancel=0 and p.supplier_code=?3 and p.po_type=1 group by p.doc_id,p.doc_date\r\n"
			+ "            union\r\n"
			+ "            select p.doc_id,p.doc_date from purchase_contract_basic p where\r\n"
			+ "            p.org_id=?1\r\n"
			+ "			and p.branch=?2 and p.active=1 and p.cancel=0 and p.supplier=?3\r\n"
			+ "            group by p.doc_id,p.doc_date")
	Set<Object[]> getPurchaseOrderNumberStockTransfer(Long orgId, Long branch, Long supplierCode);

	@Query(nativeQuery = true, value = "select doc_id,doc_date,schedule_start_date,schedule_end_date,purchase_delivery_schedule_basic_id from purchase_delivery_schedule_basic\r\n"
			+ "where org_id=?1 and branch=?2 and supplier=?3 and purchase_order_no=?4 and active=1 and cancel=0")
	Set<Object[]> getScheduleDocIdStockTransfer(Long orgId, Long branch, Long supplierCode, String purchaseOrderNo);

	@Query(nativeQuery = true, value = "select i.item_id,i.item_code,i.item_description,u.unitmaster_id,u.unit_id,i.inspection,l.value_description,p1.qty_in_primary_unit,p1.rate_in_inr from purchase_order_basic p join purchase_order_local_details p1 \r\n"
			+ "			on p.purchase_order_basic_id=p1.purchase_order_basic_id left join item i on i.item_id=p1.item \r\n"
			+ "			left join unitmaster u on u.unitmaster_id=i.primary_unit left join listofvaluesdetails l on l.listofvaluesdetails_id=i.inspection  where p.org_id=?1\r\n"
			+ "			and p.branch=?2 and p.active=1 and p.cancel=0 and p.doc_id=?3\r\n" + "            union\r\n"
			+ "            select i.item_id,i.item_code,i.item_description,u.unitmaster_id,u.unit_id,i.inspection,l.value_description,1 as qty ,p1.rate_in_currency from purchase_contract_basic p join purchase_contract_details p1 \r\n"
			+ "			on p.purchase_contract_basic_id=p1.purchase_contract_basic_id left join item i on i.item_id=p1.item_id \r\n"
			+ "			left join unitmaster u on u.unitmaster_id=p1.unit left join listofvaluesdetails l on l.listofvaluesdetails_id=i.inspection where p.org_id=?1\r\n"
			+ "			and p.branch=?2 and p.active=1 and p.cancel=0 and p.doc_id=?3")
	Set<Object[]> getItemDetailsForStockTransfer(Long orgId, Long branch, String purchaseOrderNo);

	@Query(nativeQuery = true, value = "SELECT a.location_id, A.location_name, A.location_type,a.id,concat(A.location_name,' - ','MACUREX') as type FROM location A, branch B,listofvaluesdetails l1\r\n"
			+ "WHERE A.CANCEL='F' AND\r\n" + "B.branch_id=A.branch and l1.listofvaluesdetails_id=a.location_type\r\n"
			+ "AND B.org_id=?1 and B.branch_id=?2   \r\n" + "AND l1.value_description='Stores'")
	Set<Object[]> getLocationDetails(Long orgId, Long branch);

}
