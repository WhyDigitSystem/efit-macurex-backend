package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.WorkOrderVO;

@Repository
public interface WorkOrderRepo extends JpaRepository<WorkOrderVO, Long> {

	@Query(nativeQuery = true, value = "select * from workorder  where  orgid=?1 and finyear=?2 and branchcode=?3 ")
	List<WorkOrderVO> getAllWorkOrderByOrgId(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select * from workorder  where  workorderid=?1")
	WorkOrderVO getWorkOrderById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getWorkOrderDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "select a.docid,a.productionmanager,a.quotationid from quotation a where a.orgid=?1 and\r\n"
			+ "    a.customerid=?2 and a.status='PENDING' and a.active= 1  group by\r\n"
			+ "            a.docid,a.productionmanager,a.quotationid  order by  a.docid")
	Set<Object[]> getQuotationNumber(Long orgId, String custmoerId);

//	@Query(nativeQuery = true, value = "select a.partcode,a.partdescription,a.drawingno,a.revisionno,a.unit,a.qtyoffered,a1.customername,a1.customerid from \r\n"
//			+ "quotationdetails a,quotation a1 where a1.quotationid=a.quotationid and \r\n"
//			+ "		a1.orgid=?1 and a1.docid=?2 and a1.customerid=?3 and  a1.active =1 group by \r\n"
//			+ "	a.partcode,a.partdescription,a.drawingno,a.revisionno,a.unit,a.qtyoffered,a1.customername,a1.customerid  order by  a.partcode")
//	Set<Object[]> getWorkOrderPartNo(Long orgId, String docId, String custmoerId);
	
	

		@Query(nativeQuery = true, value = " SELECT \r\n"
				+ "    a.partcode, a.partdescription, a.drawingno, a.revisionno,\r\n"
				+ "    a.unit, a.qtyoffered, q.customername, q.customerid\r\n"
				+ "FROM quotationdetails a\r\n"
				+ "JOIN quotation q \r\n"
				+ "    ON q.quotationid = a.quotationid\r\n"
				+ "WHERE \r\n"
				+ "    ?2 IS NOT NULL\r\n"
				+ "    AND q.orgid = ?1\r\n"
				+ "    AND q.docid = ?2\r\n"
				+ "    AND q.customerid = ?3\r\n"
				+ "    AND q.active = 1\r\n"
				+ "\r\n"
				+ "UNION ALL\r\n"
				+ "\r\n"
				+ "SELECT\r\n"
				+ "    i.itemname,\r\n"
				+ "    i.itemdesc,\r\n"
				+ "    NULL,\r\n"
				+ "    NULL,\r\n"
				+ "    i.primaryunit,\r\n"
				+ "    0,\r\n"
				+ "    null,\r\n"
				+ "    null\r\n"
				+ "FROM item i\r\n"
				+ "WHERE \r\n"
				+ "    ?2 IS NULL\r\n"
				+ "    AND i.orgid = ?1\r\n"
				+ "    AND i.active = 1\r\n"
				+ "    AND i.itemtype IN ('FG','SFG') \r\n"
				+ "    \r\n"
				+ "    \r\n"
				+ "   ")
	Set<Object[]> getWorkOrderPartNo(Long orgId, String docId, String custmoerId);
	
	@Query(nativeQuery = true, value = "select sum(s.qty) avalibaleqty from stockdetails s join stocklocation s1 on s1.locationcode=s.location\n"
			+ " where s.orgid=?1 and s.branchcode=?2 and  s1.locationcode='VAP -FG' and  s.partno=?3 having sum(s.qty) >0")
	Set<Object[]> getWorkOrderShowsDetails(Long orgId, String branchCode, String itemCode);
	
	@Query(nativeQuery = true,value = "SELECT\n"
			+ "    w.docid,\n"
			+ "    w.docdate,\n"
			+ "    w.customername,\n"
			+ "    w.customercode,\n"
			+ "    w.customerpono,\n"
			+ "    i.itemname,\n"
			+ "    i.itemdesc,\n"
			+ "    SUM(w1.requiredqty) AS requiredqty,\n"
			+ "    SUM(COALESCE(p.packedqty, 0)) AS packedqty,\n"
			+ "    w.duedate,\n"
			+ "    w.workorderid,\n"
			+ "    CASE\n"
			+ "        WHEN SUM(w1.requiredqty) <= SUM(COALESCE(p.packedqty, 0))\n"
			+ "            THEN 'Completed'\n"
			+ "        ELSE 'Pending'\n"
			+ "    END AS status\n"
			+ "FROM workorder w\n"
			+ "JOIN workorderdetails w1\n"
			+ "    ON w.workorderid = w1.workorderid\n"
			+ "JOIN item i\n"
			+ "    ON i.itemname = w1.partno\n"
			+ "LEFT JOIN (\n"
			+ "        SELECT\n"
			+ "            e.salesorderno,\n"
			+ "            e1.partno,\n"
			+ "            e1.partdesc,\n"
			+ "            SUM(e1.quantity) AS packedqty\n"
			+ "        FROM exportpackinglist e\n"
			+ "        JOIN exportpackinglistdetails e1\n"
			+ "            ON e.exportpackinglistid = e1.exportpackinglistid\n"
			+ "        GROUP BY\n"
			+ "            e.salesorderno,\n"
			+ "            e1.partno,\n"
			+ "            e1.partdesc\n"
			+ "        UNION ALL\n"
			+ "        SELECT\n"
			+ "            p.salesorderno,\n"
			+ "            p1.partno,\n"
			+ "            p1.partdesc,\n"
			+ "            SUM(p1.qty) AS packedqty\n"
			+ "        FROM packinglist p\n"
			+ "        JOIN packinglistdetails p1\n"
			+ "            ON p.packinglistid = p1.packinglistid\n"
			+ "        GROUP BY\n"
			+ "            p.salesorderno,\n"
			+ "            p1.partno,\n"
			+ "            p1.partdesc\n"
			+ ") p\n"
			+ "    ON p.partno   = w1.partno\n"
			+ "   AND p.partdesc = w1.partname\n"
			+ "\n"
			+ "WHERE w.orgid =?1\n"
			+ "  AND (w.branchcode =?2 OR ?2= 'ALL')\n"
			+ "  AND (w.customercode =?3 OR ?3= 'ALL')\n"
			+ "GROUP BY\n"
			+ "    w.docid,\n"
			+ "    w.docdate,\n"
			+ "    w.customername,\n"
			+ "    w.customercode,\n"
			+ "    w.customerpono,\n"
			+ "    i.itemname,\n"
			+ "    i.itemdesc,\n"
			+ "    w.duedate,\n"
			+ "    w.workorderid\n"
			+ "HAVING\n"
			+ "    (\n"
			+ "        ?4 = 'ALL'\n"
			+ "        OR (?4 = 'Completed'\n"
			+ "            AND SUM(w1.requiredqty) <= SUM(COALESCE(p.packedqty, 0)))\n"
			+ "        OR (?4 = 'Pending'\n"
			+ "            AND SUM(w1.requiredqty) > SUM(COALESCE(p.packedqty, 0)))\n"
			+ "    )")
	Set<Object[]> getWorkOrderReport(Long orgId,String branchCode,String customerCode,String status);
	
	@Query(nativeQuery = true, value = "select i.itemname,\r\n"
			+ "    i.itemdesc,\r\n"
			+ "    d.drawingno,\r\n"
			+ "    d.drawingrevno,\r\n"
			+ "    i.primaryunit,\r\n"
			+ "    0 as qtyOffered\r\n"
			+ "from item i\r\n"
			+ "left join drawingmaster d \r\n"
			+ "    on d.fgpartno = i.itemname\r\n"
			+ "    and d.drawingmasterid =(\r\n"
			+ "        select d1.drawingmasterid\r\n"
			+ "        from drawingmaster d1\r\n"
			+ "        where d1.fgpartno = i.itemname\r\n"
			+ "       order by d1.docdate desc, d1.drawingmasterid desc\r\n"
			+ "        limit 1\r\n"
			+ "    )\r\n"
			+ "where  \r\n"
			+ "    i.orgid = ?1\r\n"
			+ "    and i.active = 1\r\n"
			+ "    and i.cancel = 0")
	Set<Object[]> getItemDetailsWithoutQuotationId(Long orgId);
	
}
