package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PickListVO;

@Repository
public interface PickListRepo extends JpaRepository<PickListVO, Long> {

	@Query(nativeQuery = true, value = "select * from picklist where picklistid=?1")
	List<PickListVO> findPickListById(Long id);

	@Query(nativeQuery = true, value = "select * from picklist where orgid=?1 and finyear=?2 and branchcode=?3 ")
	List<PickListVO> findPickListByOrgId(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getPickListDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "select  \n" + "    b.item,\n" + "    b.itemdesc,\n" + "    b.unit,\n"
			+ "    b.issueqty - COALESCE(SUM(p1.pickedqty), 0) AS qty,\n" + "     b.issueqty\n"
			+ "from itemisstoprod a\n" + "join itemisstoproddtls b \n"
			+ "    on a.itemisstoprodid = b.itemisstoprodid\n" + "left join picklist p \n"
			+ "    on p.itemissuetoproductionno = a.docid\n" + "left join picklistdetails p1 \n"
			+ "    on p.picklistid = p1.picklistid \n" + "    and p1.item = b.item\n" + "where a.orgid = ?1\n"
			+ "  AND a.docid = ?2\n" + "  AND a.active = 1\n" + "  AND a.cancel = 0\n"
			+ "GROUP BY b.item, b.itemdesc, b.unit, b.issueqty having b.issueqty - COALESCE(SUM(p1.pickedqty), 0)  >0\n"
			+ "ORDER BY b.item")
	Set<Object[]> findItemIssueToProductionDetailsfromPickList(Long orgId, String itemIssueToProduction);

//	@Query(nativeQuery = true, value = "select  a.docid from itemisstoprod a where   a.orgid=?1 and a.routecardno=?2 and active=1 and cancel=0 group by  a.docid  order by  a.docid")
//	Set<Object[]> findItemIssueToProductionNofromPickList(Long orgId, String routeCardEntryNo);

//	@Query(nativeQuery = true, value = "SELECT\n"
//			+ "a.docid,\n"
//			+ "    b.item,\n"
//			+ "    b.issueqty - COALESCE(SUM(p1.pickedqty), 0) AS qty,\n"
//			+ "    b.issueqty\n"
//			+ "FROM itemisstoprod a\n"
//			+ "JOIN itemisstoproddtls b\n"
//			+ "    ON a.itemisstoprodid = b.itemisstoprodid\n"
//			+ "LEFT JOIN picklist p\n"
//			+ "    ON p.itemissuetoproductionno = a.docid\n"
//			+ "LEFT JOIN picklistdetails p1\n"
//			+ "    ON p.picklistid = p1.picklistid\n"
//			+ "    AND p1.item = b.item\n"
//			+ "WHERE a.orgid =?1\n"
//			+ " and a.routecardno=?2\n"
//			+ "  AND a.active = 1\n"
//			+ "  AND a.cancel = 0\n"
//			+ "GROUP BY\n"
//			+ "a.docid,\n"
//			+ "    b.item,\n"
//			+ "    b.issueqty\n"
//			+ "HAVING b.issueqty - COALESCE(SUM(p1.pickedqty), 0) > 0\n"
//			+ "ORDER BY b.item")
//	Set<Object[]> findItemIssueToProductionNofromPickList(Long orgId, String routeCardEntryNo);

	@Query(nativeQuery = true, value = "SELECT DISTINCT a.docid\r\n" + "FROM itemisstoprod a\r\n"
			+ "JOIN itemisstoproddtls b\r\n" + "    ON a.itemisstoprodid = b.itemisstoprodid\r\n"
			+ "LEFT JOIN picklist p\r\n" + "    ON p.itemissuetoproductionno = a.docid\r\n"
			+ "LEFT JOIN picklistdetails p1\r\n" + "    ON p.picklistid = p1.picklistid\r\n"
			+ "    AND p1.item = b.item\r\n" + "WHERE a.orgid = ?1\r\n" + "  AND a.routecardno = ?2\r\n"
			+ "  AND a.active = 1\r\n" + "  AND a.cancel = 0\r\n" + "GROUP BY a.docid, b.item, b.issueqty\r\n"
			+ "HAVING (b.issueqty - COALESCE(SUM(p1.pickedqty), 0)) > 0")
	Set<Object[]> findItemIssueToProductionNofromPickList(Long orgId, String routeCardEntryNo);

	@Query(nativeQuery = true, value = "SELECT a.docid\r\n" + "FROM routecardentry a\r\n"
			+ "WHERE a.orgid = ?1  and a.customercode=?2 \r\n" + "  AND UPPER(a.status) = 'PENDING'\r\n"
			+ "  AND a.active = 1\r\n" + "  AND a.cancel = 0\r\n" + "ORDER BY a.docid")
	Set<Object[]> getRouteCardEntryNoForPickList(Long orgId, String customerCode);

	@Query(nativeQuery = true, value = "select sum(qty) qty,rackno,partno from rackstockdetails where orgid=?1 and branchcode=?2 \r\n"
			+ "and partno=?3   group by  rackno,partno having sum(qty) > 0 order by rackno,partno")
	Set<Object[]> getRackNoForRackDetails(Long orgId, String branchCode, String itemCode);

	@Query(nativeQuery = true, value = "select itemissuetoproductionno from picklist where orgid=?1 and routecardno=?2 and branchcode=?3 ")
	Set<Object[]> getItemIssueToProductionNoforPickList(Long orgId, String routeCardEntryNo, String branchCode);

	@Query(nativeQuery = true, value = "SELECT\r\n" + "    p.picklistid,\r\n" + "    p.customername,\r\n"
			+ "    p.department,\r\n" + "    p.docdate,\r\n" + "    p.docid,\r\n" + "    p.fgpartno,\r\n"
			+ "    p.itemissuetoproductionno,\r\n" + "    p.location,\r\n" + "    p.orgid,\r\n" + "    p.pickedby,\r\n"
			+ "    p.routecardno,\r\n" + "    p.shift,\r\n" + "    p.workorderno,\r\n" + "    p.branch,\r\n"
			+ "    p.branchcode,\r\n" + "    p.finyear,\r\n" + "    p.status,\r\n" + "    p.customercode,\r\n"
			+ "    d.item,\r\n" + "    d.itemname,\r\n" + "    d.unit,\r\n" + "    d.rackno,\r\n" + "    d.rackqty,\r\n"
			+ "    d.pickedqty,\r\n" + "    d.issuedqty,\r\n" + "    d.remainingqty\r\n" + "\r\n"
			+ "FROM picklist p\r\n" + "JOIN picklistdetails d\r\n" + "    ON p.picklistid = d.picklistid\r\n" + "\r\n"
			+ "WHERE p.active = 1\r\n" + "  AND p.cancel = 0\r\n" + "  AND p.orgid = ?1\r\n"
			+ "  AND p.branchcode = ?3 and (p.routecardno=?4 or ?4='ALL') \r\n" + "  AND (\r\n"
			+ "        p.itemissuetoproductionno in( ?2)\r\n" + "        OR ?2 = 'ALL'\r\n" + "      )")
	Set<Object[]> getPickListReport(Long orgId, String itemIssueToProductionNo, String branchCode,
			String routeCardEntryNo);

	@Query(nativeQuery = true, value = "select routecardno,itemissuetoproductionno from picklist where orgid=?1 and active=1 and cancel=0")
	Set<Object[]> getRouteCardNoAndItemIssueNumber(Long orgId);
}
