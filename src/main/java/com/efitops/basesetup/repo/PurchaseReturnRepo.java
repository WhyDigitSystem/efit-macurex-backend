package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseReturnVO;

@Repository
public interface PurchaseReturnRepo extends JpaRepository<PurchaseReturnVO, Long> {

	@Query(nativeQuery = true, value = "select * from  purchasereturn where orgid=?1 and finyear=?2 and branchcode=?3")
	List<PurchaseReturnVO> getAllPurchaseReturnByOrgId(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select * from purchasereturn  where purchasereturnid=?1")
	PurchaseReturnVO getPurchaseReturnById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getPurchaseReturnDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "select a.docid,a.docdate,a.grncleartime,a.pono,a.gst_no,\r\n"
			+ "a.address,a.inwardno,a.currency,a.exchangerate,a.invdcno,a.invdcdate,a.gsttype,a.customername from\r\n"
			+ " purchaseinvoice a where \r\n"
			+ "		a.orgid=?1 and a.suppliercode=?2 group by a.docid,a.docdate,a.grncleartime,a.pono,a.gst_no,\r\n"
			+ "			a.address,a.inwardno,a.currency,a.exchangerate,a.invdcno,a.invdcdate,a.gsttype,a.customername order by a.docid")
	Set<Object[]> getPurchaseInvoiceNumberFromPurchaseInvoice(Long orgId, String supplierCode);

	@Query(nativeQuery = true, value = "select a.locationcode from stocklocation a where a.orgid=?1 group by a.locationcode order by a.locationcode")
	Set<Object[]> getLocationFromStockLocation(Long orgId);

	@Query(nativeQuery = true, value = "SELECT\r\n"
			+ "    a.itemcode,\r\n"
			+ "    a.itemname,\r\n"
			+ "    a.hsnsaccode,\r\n"
			+ "    a.taxtype,\r\n"
			+ "    a.primaryunit,\r\n"
			+ "    a.porate,\r\n"
			+ "    a.unitprice,\r\n"
			+ "    g.rejectqty,\r\n"
			+ "    a.orderqty,\r\n"
			+ "    a.challanqty,\r\n"
			+ "    a.amount\r\n"
			+ "FROM purchaseinvoice a1\r\n"
			+ "JOIN purchaseinvoiceitem a\r\n"
			+ "    ON a.purchaseinvoiceid = a1.purchaseinvoiceid\r\n"
			+ "JOIN grn gr\r\n"
			+ "    ON gr.grnno = a1.grnno\r\n"
			+ "   AND gr.orgid = a1.orgid\r\n"
			+ "JOIN grndetails g\r\n"
			+ "    ON g.grnid = gr.grnid\r\n"
			+ "   AND g.itemcode = a.itemcode\r\n"
			+ "WHERE a1.orgid = ?1\r\n"
			+ "  AND a1.docid = ?2\r\n"
			+ "GROUP BY\r\n"
			+ "    a.itemcode,\r\n"
			+ "    a.itemname,\r\n"
			+ "    a.hsnsaccode,\r\n"
			+ "    a.taxtype,\r\n"
			+ "    a.primaryunit,\r\n"
			+ "    a.porate,\r\n"
			+ "    a.unitprice,\r\n"
			+ "    g.rejectqty,\r\n"
			+ "    a.orderqty,\r\n"
			+ "    a.challanqty,\r\n"
			+ "    a.amount\r\n"
			+ "ORDER BY a.itemcode")
	Set<Object[]> getItemCodeAndItemDescFromPurchsaeInvoice(Long orgId, String purchaseInvoiceNo);
	
	@Query(nativeQuery = true, value = "select p.docid,p.docdate,p.customername,p.customercode,p.gatepassno,p.invdcno,p.pono,p.purchaseinvoiceno,p.suppliername,p.suppliercode,p.tolocation,\n"
			+ " p.totalamount,p1.itemname,p1.itemcode,p1.rejectqty,p1.orderqty,p1.amount,p1.landedvalue,p.purchasereturnid from purchasereturn p join purchasereturnitem p1 \n"
			+ " on p.purchasereturnid=p1.purchasereturnid where p.orgid=?1 and p.branchcode=?2\n"
			+ " and p.finyear=?3 and (p.suppliername=?4 or ?4='ALL') and  (?5 IS NULL OR p.docdate >= ?5 ) and (?6 IS NULL OR p.docdate <= ?6 ) group by p.docid,p.docdate,p.customername,p.customercode,p.gatepassno,p.invdcno,p.pono,p.purchaseinvoiceno,p.suppliername,p.suppliercode,p.tolocation,\n"
			+ " p.totalamount,p1.itemname,p1.itemcode,p1.rejectqty,p1.orderqty,p1.amount,p1.landedvalue,p.purchasereturnid  order by  p.docdate desc")
	Set<Object[]> getPurchaseReturnReport(Long orgId, String branchCode, String finYear, String supplierName,String fromDate,String toDate);
}
