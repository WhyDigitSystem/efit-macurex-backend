package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ItemIssueToProductionVO;

@Repository
public interface ItemIssueToProductionRepo extends JpaRepository<ItemIssueToProductionVO, Long> {

	@Query(nativeQuery = true, value = "select * from itemisstoprod where itemisstoprodid=?1 ")
	List<ItemIssueToProductionVO> findItemIssueToProductionById(Long id);

	@Query(nativeQuery = true, value = "select * from itemisstoprod where orgid=?1 and finyear=?2 and branchcode=?3  ")
	List<ItemIssueToProductionVO> getItemIssueToProductionByOrgId(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getItemIssueToProductionDocId(Long orgId, String finYear, String branchCode, String screenCode);

//	@Query(nativeQuery = true, value = "SELECT a.docid\r\n"
//			+ "FROM routecardentry a\r\n"
//			+ "WHERE a.orgid = ?1 and a.customercode=?2 \r\n"
//			+ "  AND UPPER(a.status) = 'PENDING'\r\n"
//			+ "  AND a.active = 1\r\n"
//			+ "  AND a.cancel = 0\r\n"
//			+ "  AND EXISTS (\r\n"
//			+ "      SELECT 1\r\n"
//			+ "      FROM itemisstoprod i\r\n"
//			+ "      JOIN itemisstoproddtls id\r\n"
//			+ "          ON id.itemisstoprodid = i.itemisstoprodid\r\n"
//			+ "      WHERE i.orgid = a.orgid\r\n"
//			+ "        AND i.routecardno = a.docid\r\n"
//			+ "      GROUP BY id.item\r\n"
//			+ "      HAVING SUM(id.issueqty) < MAX(id.reqqty)\r\n"
//			+ "  )\r\n"
//			+ "ORDER BY a.docid")
//	Set<Object[]> findRouteCardEntryNoForItemIssueToProduction(Long orgId, String customerCode);

	@Query(nativeQuery = true, value = "SELECT a.docid\r\n"
			+ "FROM routecardentry a\r\n"
			+ "WHERE a.orgid = ?1\r\n"
			+ "  AND UPPER(a.status) = 'PENDING'\r\n"
			+ "  AND a.active = 1\r\n"
			+ "  AND a.cancel = 0\r\n"
			+ "  AND (\r\n"
			+ "        NOT EXISTS (\r\n"
			+ "            SELECT 1\r\n"
			+ "            FROM itemisstoprod i\r\n"
			+ "            WHERE i.orgid = a.orgid\r\n"
			+ "              AND i.routecardno = a.docid\r\n"
			+ "        )\r\n"
			+ "        OR\r\n"
			+ "        EXISTS (\r\n"
			+ "            SELECT 1\r\n"
			+ "            FROM itemisstoprod i\r\n"
			+ "            LEFT JOIN itemisstoproddtls id\r\n"
			+ "                ON id.itemisstoprodid = i.itemisstoprodid\r\n"
			+ "            WHERE i.orgid = a.orgid\r\n"
			+ "              AND i.routecardno = a.docid\r\n"
			+ "            GROUP BY i.itemisstoprodid\r\n"
			+ "            HAVING \r\n"
			+ "                COUNT(id.itemisstoprodid) = 0 \r\n"
			+ "                OR\r\n"
			+ "                COALESCE(SUM(id.issueqty),0) < COALESCE(MAX(id.reqqty),0)\r\n"
			+ "        )\r\n"
			+ "  )\r\n"
			+ "ORDER BY a.docid")
	Set<Object[]> findRouteCardEntryNoForItemIssueToProduction(Long orgId);

	@Query(nativeQuery = true, value = "select  a.wono,a.fgpartname,a.fgpartdesc,a.fgqty from routecardentry a where a.orgid=?1 and a.docid=?2 and active=1 and cancel=0 group by a.wono,a.fgpartname,a.fgpartdesc,a.fgqty order by  a.wono")
	Set<Object[]> findRouteCardEntryDetailsForItemIssueToProduction(Long orgId, String routeCardNo);

	@Query(nativeQuery = true, value = "SELECT \r\n"
			+ "    b.itemcode,\r\n"
			+ "    b.itemdesc,\r\n"
			+ "    b.uom,\r\n"
			+ "    b.qty\r\n"
			+ "FROM bom a\r\n"
			+ "JOIN bomdetails b \r\n"
			+ "    ON a.bomid = b.bomid\r\n"
			+ "LEFT JOIN itemisstoproddtls i \r\n"
			+ "    ON i.item = b.itemcode\r\n"
			+ "LEFT JOIN itemisstoprod h\r\n"
			+ "    ON h.itemisstoprodid = i.itemisstoprodid\r\n"
			+ "    AND h.orgid = a.orgid\r\n"
			+ "    AND h.fgitemid = a.productcode\r\n"
			+ "WHERE \r\n"
			+ "    a.orgid = ?1\r\n"
			+ "    AND a.productcode =?2\r\n"
			+ "    AND a.active = 1\r\n"
			+ "    AND a.cancel = 0\r\n"
			+ "GROUP BY \r\n"
			+ "    b.itemcode,\r\n"
			+ "    b.itemdesc,\r\n"
			+ "    b.uom,\r\n"
			+ "    b.qty\r\n"
			+ "HAVING \r\n"
			+ "    COUNT(i.item) = 0 \r\n"
			+ "    OR \r\n"
			+ "    COALESCE(MAX(i.reqqty),0) > COALESCE(SUM(i.issueqty),0)  \r\n"
			+ "ORDER BY \r\n"
			+ "    b.itemcode")
	Set<Object[]> findItemIssueToProductionDetailsfromBom(Long orgId, String fgItemId);

	@Query(nativeQuery = true, value = "select  COALESCE(SUM(i1.issueqty), 0) AS issueQty " + "from itemisstoprod i "
			+ "join itemisstoproddtls i1 ON i.itemisstoprodid = i1.itemisstoprodid " + "where i.orgid = ?1 "
			+ "and i.routecardno = ?2 " + "and i.workorder = ?3 " + "and i1.item = ?4")
	Set<Object[]> getItemIssueQty(Long orgId, String routeCardNo, String workorder, String item);

	@Query(value = "SELECT  i.routecardno , i.orgid, i.workorder,i.fgitemid ,i1.item,i1.itemdesc,sum(i1.reqqty),sum(i1.issueqty) as issueqty,sum(i1.reqqty-issueqty) as pendingqty\n"
			+ "from itemisstoprod i\n" + "left join  itemisstoproddtls i1 on i.itemisstoprodid = i1.itemisstoprodid\n"
			+ "where  orgid = ?1 and (routecardno =?2 or ?2='ALL')\n"
			+ "group by i.routecardno , i.orgid, i.workorder,i.fgitemid ,i1.item,i1.itemdesc", nativeQuery = true)
	Set<Object[]> getItemIssuedProductionDetails(Long orgId, String routecardno);

}
