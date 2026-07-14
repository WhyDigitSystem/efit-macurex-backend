package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.GrnVO;

@Repository
public interface GrnRepo extends JpaRepository<GrnVO, Long> {

	@Query(nativeQuery = true, value = "select*from grn where orgid=?1 and finyear=?2 and branchcode=?3 ")
	List<GrnVO> findGrnByOrgId(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select*from grn where grnid=?1")
	List<GrnVO> getGrnById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getGrnDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "SELECT a1.docid,a1.ponumber,a1.suppliername,a1.vehicleno,a1.invoiceno,a1.invoicedate,b.currency,b.gstin,a1.suppliercode,d1.buyingexrate FROM gateinwardentry a1 \r\n"
			+ "					join partymaster b on a1.suppliername = b.partyname join dailymonthlyexratesdtl d1 on  d1.currency=b.currency \r\n"
			+ "					left join grn g on g.inwardno=a1.docid\r\n"
			+ "					where upper(partytype) ='SUPPLIER' and a1.orgid =?1 and g.inwardno is null\r\n"
			+ "                    group by a1.docid,a1.ponumber,a1.suppliername,a1.vehicleno,a1.invoiceno,a1.invoicedate,b.currency,b.gstin,a1.suppliercode,d1.buyingexrate")
	Set<Object[]> findInwardNoForGRNDetails(Long orgId);

	@Query(nativeQuery = true, value = "SELECT \r\n" + "    a1.itemname,\r\n" + "    a1.itemdesc,\r\n"
			+ "    a1.inwardqty,\r\n" + "    a1.invoiceqty,\r\n" + "    a1.poqty,\r\n" + " --    c.uom,\r\n"
			+ "    c.hsncode,\r\n" + "    c.inspection,\r\n" + "    c.needqcapproval,\r\n" + "    d.price,\r\n"
			+ "    e.taxslab,c.primaryunit,    a1.pobalanceqty\r\n" + " \r\n" + "FROM gateinwardentrydetails a1\r\n"
			+ "JOIN gateinwardentry b\r\n" + "    ON a1.gateinwardentryid = b.gateinwardentryid\r\n" + "JOIN item c\r\n"
			+ "    ON a1.itemname = c.itemname\r\n" + "JOIN itempriceslab d\r\n" + "    ON c.itemid = d.itemid\r\n"
			+ "JOIN itemtaxslab e\r\n" + "    ON e.itemid = c.itemid\r\n" + "WHERE b.docid =?2\r\n"
			+ "  AND b.orgid =?1\r\n" + "  AND d.priceeffectivefrom = (\r\n"
			+ "        SELECT MAX(priceeffectivefrom)\r\n" + "        FROM itempriceslab\r\n"
			+ "        WHERE itemid = d.itemid\r\n" + "  )\r\n" + "  AND e.taxeffectivefrom = (\r\n"
			+ "        SELECT MAX(taxeffectivefrom)\r\n" + "        FROM itemtaxslab\r\n"
			+ "        WHERE itemid = e.itemid\r\n" + "  )")
	Set<Object[]> findItemForGRNDetails(Long orgId, String inwardNo);

	@Query(nativeQuery = true, value = "SELECT \r\n" + "    CONCAT(\r\n"
			+ "        COALESCE(a.addressline1, ''), ', ',\r\n" + "        COALESCE(a.addressline2, ''), ', ',\r\n"
			+ "        COALESCE(a.addressline3, ''), ', ',\r\n" + "        COALESCE(a.city, ''), ', ',\r\n"
			+ "        COALESCE(a.pincode, ''), ', ',\r\n" + "        COALESCE(a.state, '')\r\n"
			+ "    ) AS full_address,\r\n" + "    a.stategstin,\r\n" + "--     a.taxtype,\r\n" + "    a.state,\r\n"
			+ "    a.pincode,\r\n" + "    a.city\r\n" + "FROM efit_ops.partyaddress a\r\n"
			+ "JOIN efit_ops.partymaster b \r\n" + "    ON a.partymasterid = b.partymasterid\r\n"
			+ "WHERE b.cancel = 0\r\n" + "  AND b.active = 1\r\n" + "  AND b.partyname = ?2\r\n"
			+ "  AND b.orgid = ?1\r\n" + "  AND b.partytype = 'SUPPLIER'")
	Set<Object[]> findSupplierAddressDetails(Long orgId, String supplierName);

	@Query(nativeQuery = true, value = ("SELECT sgstpercentage  FROM efit_ops.gst where 'INTRA' = ?3 and gstslab=?2 AND orgid = ?1"))
	Set<Object[]> findgetSGSTandCGSTForGRN(Long orgId, String gstType, String taxType);

	@Query(nativeQuery = true, value = ("SELECT igstpercentage  FROM efit_ops.gst where 'INTER' =  ?3 and gstslab=?2 AND orgid = ?1"))
	Set<Object[]> findgetIGSTForGRN(Long orgId, String gstType, String taxType);

	@Query(nativeQuery = true, value = "SELECT partno, SUM(\r\n" + "    CASE \r\n"
			+ "        WHEN plusorminus = 'p' THEN qty \r\n" + "        ELSE -qty \r\n" + "    END\r\n"
			+ ") AS stock \r\n" + "FROM efit_ops.stockdetails \r\n" + "where location ='WDS-STORE'\r\n"
			+ "and orgid= ?1  \r\n" + "and partno= ?2  \r\n" + "group by partno")
	Set<Object[]> findGrnAvlStock(Long orgId, String itemCode);

	GrnVO findByOrgIdAndIdAndGrnNoAndSupplierName(Long orgId, Long id, String docId, String supplierName);

//	@Query(nativeQuery = true, value = "SELECT\r\n" + "    a1.itemcode,\r\n" + "    a1.itemdesc,\r\n"
//			+ "    a1.orderqty,\r\n" + "    a1.recievedqty,\r\n" + "    a1.porate,\r\n" + "    c.hsncode,\r\n"
//			+ "    c.inspection,\r\n" + "    c.needqcapproval,\r\n" + "    d.price,\r\n" + "    e.taxslab,\r\n"
//			+ "    c.primaryunit,\r\n" + "    b.inwardno\r\n" + "FROM grndetails a1\r\n" + "JOIN grn b\r\n"
//			+ "    ON a1.grnid = b.grnid\r\n" + "JOIN item c\r\n" + "    ON a1.itemcode = c.itemname\r\n"
//			+ "JOIN itempriceslab d\r\n" + "    ON c.itemid = d.itemid\r\n" + "JOIN itemtaxslab e\r\n"
//			+ "    ON e.itemid = c.itemid\r\n" + "WHERE b.grnno = ?2\r\n"
//			+ "  AND b.orgid = ?1 and c.inspection='Yes'\r\n" + "  AND d.priceeffectivefrom = (\r\n"
//			+ "        SELECT MAX(priceeffectivefrom)\r\n" + "        FROM itempriceslab\r\n"
//			+ "        WHERE itemid = d.itemid\r\n" + "  )\r\n" + "  AND e.taxeffectivefrom = (\r\n"
//			+ "        SELECT MAX(taxeffectivefrom)\r\n" + "        FROM itemtaxslab\r\n"
//			+ "        WHERE itemid = e.itemid\r\n" + "  )")

	@Query(nativeQuery=true,value ="select sum(qty) qty from stockdetails where orgid=?1  and branchcode=?2  and location=?3  and partno=?4")
	Set<Object[]> getAvailableStock(Long orgId,String branchCode,String location, String itemCode);
	
	@Query(nativeQuery = true, value = "SELECT\r\n"
			+ "    a1.itemcode,\r\n"
			+ "    a1.itemdesc,\r\n"
			+ "    a1.orderqty,\r\n"
			+ "    a1.recievedqty,\r\n"
			+ "    a1.porate,\r\n"
			+ "    c.hsncode,\r\n"
			+ "    c.inspection,\r\n"
			+ "    c.needqcapproval,\r\n"
			+ "    d.price,\r\n"
			+ "    e.taxslab,\r\n"
			+ "    c.primaryunit,\r\n"
			+ "    b.inwardno\r\n"
			+ "FROM grndetails a1\r\n"
			+ "JOIN grn b\r\n"
			+ "    ON a1.grnid = b.grnid\r\n"
			+ "JOIN item c\r\n"
			+ "    ON a1.itemcode = c.itemname\r\n"
			+ "JOIN itempriceslab d\r\n"
			+ "    ON c.itemid = d.itemid\r\n"
			+ "JOIN itemtaxslab e\r\n"
			+ "    ON e.itemid = c.itemid\r\n"
			+ "WHERE b.grnno = ?2\r\n"
			+ "  AND b.orgid = ?1 and c.inspection='Yes'"
			+ "AND NOT EXISTS (\r\n"
			+ "        SELECT 1\r\n"
			+ "        FROM thirdpartyinspection t join thirdpartyinspectiondetails t1 on t1.thirdpartyinspectionid=t.thirdpartyinspectionid\r\n"
			+ "        WHERE t.grnno = b.grnno \r\n"
			+ "        AND t1.itemid = a1.itemcode\r\n"
			+ "  )\r\n"
			+ "  AND d.priceeffectivefrom = (\r\n"
			+ "        SELECT MAX(priceeffectivefrom)\r\n"
			+ "        FROM itempriceslab\r\n"
			+ "        WHERE itemid = d.itemid\r\n"
			+ "  )\r\n"
			+ "  AND e.taxeffectivefrom = (\r\n"
			+ "        SELECT MAX(taxeffectivefrom)\r\n"
			+ "        FROM itemtaxslab\r\n"
			+ "        WHERE itemid = e.itemid\r\n"
			+ "  )")
	Set<Object[]> getGrnItemDetails(Long orgId, String grnNo);

	@Query(nativeQuery = true, value = "SELECT \n" + "    po.docid AS pono,\n" + "    po.itemcode,\n"
			+ "    po.itemdesc,\n" + "    COALESCE(po.po_qty, 0)      AS orderqty,\n"
			+ "    COALESCE(pr.reject_qty, 0)  AS rejectqty,\n" + "    COALESCE(gr.grn_qty, 0)     AS receviedqty,\n"
			+ "    ( COALESCE(po.po_qty, 0)\n" + "    + COALESCE(pr.reject_qty, 0)\n"
			+ "    - COALESCE(gr.grn_qty, 0) ) AS remainingqty\n" + "FROM\n" + "(\n" + "    SELECT \n"
			+ "        p.docid,\n" + "        p1.item        AS itemcode,\n" + "        p1.itemdesc,\n"
			+ "        SUM(p1.qty)    AS po_qty\n" + "    FROM purchaseorder p\n"
			+ "    JOIN purchaseorderdetails p1 \n" + "        ON p.purchaseorderid = p1.purchaseorderid\n"
			+ "    WHERE   p.orgid=?1 and p.branchcode=?2 and  p.docid =?3 and p1.item=?4\n"
			+ "    GROUP BY p.docid, p1.item, p1.itemdesc\n" + ") po\n" + "\n" + "LEFT JOIN\n" + "(\n" + "    SELECT \n"
			+ "        g.pono,\n" + "        g1.itemcode,\n" + "        SUM(g1.challanqty) AS grn_qty\n"
			+ "    FROM grn g\n" + "    JOIN grndetails g1 \n" + "        ON g.grnid = g1.grnid\n"
			+ "    WHERE g.orgid=?1 and g.branchcode=?2 and  g.pono =?3 and g1.itemcode=?4\n"
			+ "    GROUP BY g.pono, g1.itemcode\n" + ") gr\n" + "    ON po.docid = gr.pono\n"
			+ "   AND po.itemcode = gr.itemcode\n" + "LEFT JOIN\n" + "(\n" + "    SELECT \n" + "        p.pono,\n"
			+ "        p1.itemcode,\n" + "        SUM(p1.rejectqty) AS reject_qty\n" + "    FROM purchasereturn p\n"
			+ "    JOIN purchasereturnitem p1 \n" + "        ON p.purchasereturnid = p1.purchasereturnid\n"
			+ "    WHERE p.orgid=?1 and p.branchcode=?2 and p.pono =?3 and p1.itemcode=?4\n"
			+ "    GROUP BY p.pono, p1.itemcode\n" + ") pr\n" + "    ON po.docid = pr.pono\n"
			+ "   AND po.itemcode = pr.itemcode having remainingqty >0")
	Set<Object[]> getRemainingBalanceQty(Long orgId, String branchCode, String purchaseOrderNo, String itemCode);

	@Query(nativeQuery = true, value = "select sum(s.qty) avalibaleqty from stockdetails s join stocklocation s1 on s1.locationcode=s.location\n"
			+ " where s.orgid=?1 and s.branchcode=?2 and  s1.locationcode=?3 and  s.partno=?4 having sum(s.qty) >0")
	Set<Object[]> getAllShowsAvalibaleqty(Long orgId, String branchCode, String location, String itemCode);

	@Query(nativeQuery = true, value = "SELECT\n" + "    a.grnno,\n" + "    a.grndate,\n" + "    a.invdcno,\n"
			+ "    a.inwardno,\n" + " --    a.invoiceno,\n" + "    a.gstno,\n" + "    a.location,\n" + "    a.pono,\n"
			+ "    a.suppliername,\n" + "    a.suppliercode,\n" + "    a.status,\n" + "    b.itemcode,\n"
			+ "    b.itemdesc,\n" + "    b.primaryunit,\n" + "    b.porate,\n" + "    b.acceptqty,\n"
			+ "    b.amount,\n" + "    b.igst,\n" + "    b.taxvalue,\n"
			+ "    b.amount+b.taxvalue as totalAmount,a.grnid\n" + "FROM\n" + "    grn a,\n" + "    grndetails b\n"
			+ "WHERE\n" + "    a.grnid = b.grnid\n" + "    AND a.orgid =?1 \n"
			+ "     AND (a.suppliername =?2 OR  ?2= 'ALL')\n" + "    AND (?3 IS NULL OR a.grndate >= ?3)\n"
			+ "    AND (?4 IS NULL OR a.grndate <= ?4)\n" + "  AND (a.branchcode =?5 OR ?5= 'ALL')\n" + "ORDER BY\n"
			+ "    a.createdon DESC")
	Set<Object[]> getGrnDetails(Long orgId, String supplierName, String fromDate, String toDate, String branchCode);

	@Query(nativeQuery = true, value = "SELECT\n" + " a.grnno,\n" + "    a.grndate,\n" + "    a.invdcno,\n"
			+ "    a.inwardno,\n" + "    a.gstno,\n" + "    a.location,\n" + "    a.pono,\n" + "    a.suppliername,\n"
			+ "    a.suppliercode,\n" + "    a.status,\n" + "    a.gsttype,\n" + "    a.grossamount,\n"
			+ "    a.totalamounttax,\n" + "    a.netamount,\n" + "    CAST(\n" + "        CASE\n"
			+ "            WHEN a.gsttype = 'INTER' THEN a.totalamounttax\n" + "            ELSE 0\n"
			+ "        END AS DECIMAL(18,2)\n" + "    ) AS igst,\n" + "    CAST(\n" + "        CASE\n"
			+ "            WHEN a.gsttype = 'INTRA' THEN a.totalamounttax / 2\n" + "            ELSE 0\n"
			+ "        END AS DECIMAL(18,2)\n" + "    ) AS cgst,\n" + "    CAST(\n" + "        CASE\n"
			+ "            WHEN a.gsttype = 'INTRA' THEN a.totalamounttax / 2\n" + "            ELSE 0\n"
			+ "        END AS DECIMAL(18,2)\n" + "    ) AS sgst,\n" + "    CASE\n"
			+ "        WHEN a.approvestatus IS NULL THEN 'Not Appproved'\n" + "        ELSE a.approvestatus\n"
			+ "    END AS approvestatus,a.grnid\n" + "FROM\n" + "    grn a\n" + "WHERE\n" + "   a.orgid =?1 \n"
			+ "     AND (a.suppliername =?2 OR  ?2= 'ALL')\n" + "    AND (?3 IS NULL OR a.grndate >= ?3)\n"
			+ "    AND (?4 IS NULL OR a.grndate <= ?4)\n" + "  AND (a.branchcode =?5 OR ?5= 'ALL')\n" + "ORDER BY\n"
			+ "    a.createdon DESC")
	Set<Object[]> getGrnSummaryDetails(Long orgId, String supplierName, String fromDate, String toDate,
			String branchCode);

	@Query(nativeQuery = true, value = "select partyname,partycode from partymaster where partytype='SUPPLIER' and orgid=?1 and active=1 and cancel=0")
	Set<Object[]> getSupplierName(Long orgId);
}
