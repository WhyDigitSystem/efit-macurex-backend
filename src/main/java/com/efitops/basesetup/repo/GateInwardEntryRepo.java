package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.GateInwardEntryVO;

@Repository
public interface GateInwardEntryRepo extends JpaRepository<GateInwardEntryVO, Long>{

	@Query(nativeQuery = true, value = "select * from gateinwardentry where orgid=?1 and finyear=?2 and branchcode=?3 ")
	List<GateInwardEntryVO> findGateInwardEntryByOrgId(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select * from gateinwardentry where gateinwardentryid=?1")
	List<GateInwardEntryVO> findgetGateInwardEntryById(Long id);


	@Query(nativeQuery = true,value="select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getGateInwardEntryDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "select docid from purchaseorder where orgid=?1 and suppliercode=?2 and active =1 order by 1")
	Set<Object[]> findPurchaseOrderNoForGateInward(Long orgId, String supplierCode);

	@Query(nativeQuery = true, value = "SELECT \r\n"
			+ "    b.item,\r\n"
			+ "    b.itemdesc,\r\n"
			+ "    b.uom,\r\n"
			+ "    SUM(b.qty) AS qty,\r\n"
			+ "\r\n"
			+ "    COALESCE(gi.inwardqty, 0) AS inwardqty,\r\n"
			+ "\r\n"
			+ "    CASE\r\n"
			+ "        WHEN gi.inwardqty IS NULL THEN 0\r\n"
			+ "        ELSE SUM(b.qty) - gi.inwardqty\r\n"
			+ "    END AS pobalanceqty\r\n"
			+ "\r\n"
			+ "FROM purchaseorder a\r\n"
			+ "JOIN purchaseorderdetails b \r\n"
			+ "    ON a.purchaseorderid = b.purchaseorderid\r\n"
			+ "\r\n"
			+ "LEFT JOIN (\r\n"
			+ "    SELECT \r\n"
			+ "        g.ponumber,\r\n"
			+ "        g1.itemname,\r\n"
			+ "        SUM(g1.inwardqty) AS inwardqty\r\n"
			+ "    FROM gateinwardentry g\r\n"
			+ "    JOIN gateinwardentrydetails g1 \r\n"
			+ "        ON g.gateinwardentryid = g1.gateinwardentryid\r\n"
			+ "    GROUP BY g.ponumber, g1.itemname\r\n"
			+ ") gi\r\n"
			+ "    ON gi.ponumber = a.docid\r\n"
			+ "    AND gi.itemname = b.item\r\n"
			+ "\r\n"
			+ "WHERE a.orgid = ?1\r\n"
			+ "  AND a.docid = ?2\r\n"
			+ "  AND a.active = 1\r\n"
			+ "\r\n"
			+ "GROUP BY \r\n"
			+ "    b.item, b.itemdesc, b.uom, gi.inwardqty\r\n"
			+ "\r\n"
			+ "HAVING SUM(b.qty) - COALESCE(gi.inwardqty, 0) > 0")
	Set<Object[]> findItemDetailsForGateInwardEntry(Long orgId, String purchaseOrderNo);
	
	@Query(nativeQuery = true, value = "select p.docid,p.docdate,p.couriername,p.courierno,p.invoiceno,p.invoicedate,p.ponumber,p.suppliername,p.suppliercode,p.vehicleno,\n"
			+ "			 p1.itemname,p1.itemdesc,p1.uom,p1.invoiceqty,p.gateinwardentryid from gateinwardentry p join gateinwardentrydetails p1 \n"
			+ "			 on p.gateinwardentryid=p1.gateinwardentryid where p.orgid=?1 and (p.branchcode=?2 or ?2='ALL')\n"
			+ "	and  (p.suppliername=?3 or ?3='ALL') and  (?4 IS NULL OR p.docdate >= ?4 ) and (?5 IS NULL OR p.docdate <= ?5 ) group by p.docid,\n"
			+ "    p.docid,p.docdate,p.couriername,p.courierno,p.invoiceno,p.invoicedate,p.ponumber,p.suppliername,p.suppliercode,p.vehicleno,\n"
			+ "			 p1.itemname,p1.itemdesc,p1.uom,p1.invoiceqty,p.gateinwardentryid")
	Set<Object[]> getGateInwardReport(Long orgId,String branchCode, String supplierName,String fromDate,String toDate);

}
