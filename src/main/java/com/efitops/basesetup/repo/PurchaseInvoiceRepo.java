package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseInvoiceVO;

@Repository
public interface PurchaseInvoiceRepo extends JpaRepository<PurchaseInvoiceVO, Long> {

	@Query(nativeQuery = true, value = "select * from  purchaseinvoice where orgid=?1 and finyear=?2 and branchcode=?3")
	List<PurchaseInvoiceVO> getAllPurchaseInvoiceByOrgId(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select * from purchaseinvoice where purchaseinvoiceid=?1")
	PurchaseInvoiceVO getPurchaseInvoiceById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getPurchaseInvoiceDocId(Long orgId, String finyear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "select a.docid from purchaseorder a where a.orgid=?1 and a.suppliercode=?2 and a.active = 1 group by a.docid order by a.docid")
	Set<Object[]> getPurchaseOrderPoNumber(Long orgId, String supplierCode);
 
	
	@Query(nativeQuery = true, value = "SELECT \r\n"
			+ "    a.grnno,\r\n"
			+ "    a.grndate,\r\n"
			+ "    a.location,\r\n"
			+ "    a.inwardno,\r\n"
			+ "    a1.partycode,\r\n"
			+ "    a.gstno,\r\n"
			+ "    a.address,\r\n"
			+ "    a.currency,\r\n"
			+ "    a.exchanderate,\r\n"
			+ "    a.grncleartime,\r\n"
			+ "    a.invdcno,\r\n"
			+ "    a.invdcdate,\r\n"
			+ "    a.gsttype,\r\n"
			+ "--     a.customer,\r\n"
			+ "    p.customername\r\n"
			+ "FROM grn a\r\n"
			+ "JOIN partymaster a1 \r\n"
			+ "    ON a.suppliername = a1.partyname \r\n"
			+ "    join purchaseorder p on p.docid=a.pono\r\n"
			+ "WHERE a.orgid =?1\r\n"
			+ "  AND a.pono =?2\r\n"
			+ "GROUP BY \r\n"
			+ "    a.grnno,\r\n"
			+ "    a.grndate,\r\n"
			+ "    a.location,\r\n"
			+ "    a.inwardno,\r\n"
			+ "    a1.partycode,\r\n"
			+ "    a.gstno,\r\n"
			+ "    a.address,\r\n"
			+ "    a.currency,\r\n"
			+ "    a.exchanderate,\r\n"
			+ "    a.grncleartime,\r\n"
			+ "    a.invdcno,\r\n"
			+ "    a.invdcdate,\r\n"
			+ "    a.gsttype,\r\n"
			+ "--     a.customer,\r\n"
			+ "    p.customername\r\n"
			+ "ORDER BY a.grnno")
	Set<Object[]> getGrnNoAndGrnDateFromGrnDetails(Long orgId, String poNo);

	@Query(nativeQuery = true, value = "SELECT \r\n"
			+ "    a.itemcode,\r\n"
			+ "    a.itemdesc,\r\n"
			+ "    a.hsnsaccode,\r\n"
			+ "    a.taxtype,\r\n"
			+ "    a.primaryunit,\r\n"
			+ "    a.porate,\r\n"
			+ "    a.recievedqty,\r\n"
			+ "    a.acceptqty,\r\n"
			+ "    a.orderqty,\r\n"
			+ "    a.challanqty\r\n"
			+ "FROM grn a1\r\n"
			+ "JOIN grndetails a\r\n"
			+ "    ON a.grnid = a1.grnid\r\n"
			+ "WHERE a1.orgid = ?1\r\n"
			+ "  AND a1.grnno = ?2\r\n"
			+ "GROUP BY \r\n"
			+ "    a.itemcode,\r\n"
			+ "    a.itemdesc,\r\n"
			+ "    a.hsnsaccode,\r\n"
			+ "    a.taxtype,\r\n"
			+ "    a.primaryunit,\r\n"
			+ "    a.porate,\r\n"
			+ "    a.recievedqty,\r\n"
			+ "    a.acceptqty,\r\n"
			+ "	a.orderqty,\r\n"
			+ "    a.challanqty\r\n"
			+ "ORDER BY a.itemcode")
	Set<Object[]> getItemCodeAndItemDescFromGrn(Long orgId, String grnNo);

	@Query(nativeQuery = true, value = "select a1.purchaseorderdetailsid from purchaseorder a, purchaseorderdetails a1 where a.purchaseorderid=a1.purchaseorderid and \r\n"
			+ "			a.docid=?1 and a1.item=?2 and a.orgid=?3 group by a1.purchaseorderdetailsid")
	Set<Object[]> getPoDetailsId(String docId, String item, Long orgId);
	
	@Query(nativeQuery = true, value = "SELECT\n"
			+ "a.docid,a.docdate,\n"
			+ "    a.grnno,\n"
			+ "    a.invdcno,\n"
			+ "    a.inwardno,\n"
			+ "    a.gst_no,\n"
			+ "    a.location,\n"
			+ "    a.pono,\n"
			+ "    a.suppliername,\n"
			+ "    a.suppliercode,\n"
			+ "    b.itemcode,\n"
			+ "    b.itemname,\n"
			+ "    b.primaryunit,\n"
			+ "    b.porate,\n"
			+ "    b.acceptqty,\n"
			+ "    b.amount,\n"
			+ "    b.igst,\n"
			+ "    b.taxvalue,\n"
			+ "    b.amount + b.taxvalue AS totalAmount\n"
			+ "FROM\n"
			+ "    purchaseinvoice a,\n"
			+ "    purchaseinvoiceitem b\n"
			+ "WHERE\n"
			+ "    a.purchaseinvoiceid = b.purchaseinvoiceid\n"
			+ "    AND a.orgid =?1\n"
			+ "    AND (a.suppliername =?2 OR ?2= 'ALL')\n"
			+ "    AND (?3 IS NULL OR a.grndate >= ?3)\n"
			+ "    AND (?4 IS NULL OR a.grndate <= ?4)\n"
			+ "    AND (a.branchcode = ?5 OR ?5 = 'ALL')\n"
			+ "ORDER BY\n"
			+ "    a.createdon DESC")
	Set<Object[]> getPurchaseInvoiceDetails(Long orgId, String supplierName,String fromDate,String toDate,String branchCode);
	
	@Query(nativeQuery = true, value = "SELECT a.docid,a.docdate,\n"
			+ "    a.grnno,\n"
			+ "    a.grndate,\n"
			+ "    a.invdcno,\n"
			+ "    a.inwardno,\n"
			+ "    a.gst_no,\n"
			+ "    a.location,\n"
			+ "    a.pono,\n"
			+ "    a.suppliername,\n"
			+ "    a.suppliercode,\n"
			+ "    a.gsttype,\n"
			+ "    a.grossamount,\n"
			+ "    a.totalamounttax,\n"
			+ "    a.netamount,\n"
			+ "    CAST(\n"
			+ "        CASE\n"
			+ "            WHEN a.gsttype = 'INTER' THEN a.totalamounttax\n"
			+ "            ELSE 0\n"
			+ "        END AS DECIMAL(18,2)\n"
			+ "    ) AS igst,\n"
			+ "    CAST(\n"
			+ "        CASE\n"
			+ "            WHEN a.gsttype = 'INTRA' THEN a.totalamounttax / 2\n"
			+ "            ELSE 0\n"
			+ "        END AS DECIMAL(18,2)\n"
			+ "    ) AS cgst,\n"
			+ "    CAST(\n"
			+ "        CASE\n"
			+ "            WHEN a.gsttype = 'INTRA' THEN a.totalamounttax / 2\n"
			+ "            ELSE 0\n"
			+ "        END AS DECIMAL(18,2)\n"
			+ "    ) AS sgst\n"
			+ "FROM\n"
			+ "    purchaseinvoice a\n"
			+ "WHERE\n"
			+ "    a.orgid = ?1\n"
			+ "    AND (a.suppliername = ?2 OR ?2 = 'ALL')\n"
			+ "    AND (?3 IS NULL OR a.grndate >= ?3)\n"
			+ "    AND (?4 IS NULL OR a.grndate <= ?4)\n"
			+ "    AND (a.branchcode = ?5 OR ?5 = 'ALL')\n"
			+ "ORDER BY\n"
			+ "    a.createdon DESC")
	Set<Object[]> getPurchaseInvoiceSummaryDetails(Long orgId, String supplierName,String fromDate,String toDate,String branchCode);

}
