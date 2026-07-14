package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.FinalFgPartStockUpdateVO;

@Repository
public interface FinalFgPartStockUpdateRepo extends JpaRepository<FinalFgPartStockUpdateVO, Long>{

	@Query(nativeQuery = true, value = "select * from finalfgpartstockupdate where orgid=?1")
	List<FinalFgPartStockUpdateVO> getAllFgPartStockUpdateVOByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from finalfgpartstockupdate where finalfgpartstockupdateid=?1")
	FinalFgPartStockUpdateVO getFgPartStockUpdateVOById(Long id);

	@Query(nativeQuery = true,value = "select  a.docid ,a.wono from routecardentry a where a.orgid=?1 and upper(status)='PENDING' and active=1 and cancel=0 \r\n"
			+ "  order by  a.docid")
	Set<Object[]> findRouteCardEntryNoFromFgPartStockUpdate(Long orgId);
	

	@Query(nativeQuery = true,value = " SELECT a.fgpartname,a.fgpartdesc,a.fgqty,c.primaryunit,d.price FROM routecardentry a JOIN item c ON  \r\n"
			+ "    a.fgpartname = c.itemname join itempriceslab d on d.itemid=c.itemid WHERE  \r\n"
			+ "    a.orgid = ?1 AND a.docid =?2 AND a.active = 1 AND a.cancel = 0  \r\n"
			+ "    and priceeffectivefrom= (SELECT MAX(priceeffectivefrom) FROM efit_ops.itemtaxslab  WHERE d.itemid = c.itemid)\r\n"
			+ "    GROUP BY a.fgpartname, a.fgpartdesc, a.fgqty, c.primaryunit, d.price ORDER BY  \r\n"
			+ "    a.fgpartname")
	Set<Object[]> findRouteCardEntryDetailsFromFgPartStockUpdate(Long orgId, String routeCardEntryNo);

//	@Query(nativeQuery = true,value = " SELECT b.itemcode,b.itemdesc,b.qty,b.uom,d.price\r\n"
//			+ "FROM bom a JOIN bomdetails b ON a.bomid = b.bomid\r\n"
//			+ "JOIN item c ON a.productcode = c.itemname\r\n"
//			+ "JOIN itempriceslab d ON d.itemid = c.itemid\r\n"
//			+ "WHERE  a.orgid = ?1 AND a.productcode = ?2 AND a.active = 1 AND a.cancel = 0\r\n"
//			+ "GROUP BY b.itemcode, b.itemdesc, b.qty, b.uom, d.price\r\n"
//			+ "ORDER BY  b.itemcode")
//	Set<Object[]> getPriceDetails(Long orgId, String fgPartName);

	@Query(nativeQuery = true,value = "SELECT \n"
			+ "    b.itemcode,\n"
			+ "    b.itemdesc,\n"
			+ "    b.qty,\n"
			+ "    b.uom\n"
			+ "FROM bom a\n"
			+ "JOIN bomdetails b \n"
			+ "    ON a.bomid = b.bomid\n"
			+ "WHERE a.orgid =?1\n"
			+ "  AND a.productcode =?2\n"
			+ "  AND a.active = 1\n"
			+ "  AND a.cancel = 0\n"
			+ "  AND a.docdate = (\n"
			+ "        SELECT MAX(docdate)\n"
			+ "        FROM bom\n"
			+ "        WHERE orgid =?1\n"
			+ "          AND productcode =?2\n"
			+ "          AND active = 1\n"
			+ "          AND cancel = 0\n"
			+ "    )\n"
			+ "ORDER BY b.itemcode")
	Set<Object[]> findItemDetailsFromFgPartStockUpdate(Long orgId, String fgPartName);	

	
	@Query(nativeQuery = true,value = "select i1.price from item i join itempriceslab i1 on i.itemid=i1.itemid where i.orgid=?1 and i.itemname=?2  and i1.priceeffectivefrom= (select max(i1.priceeffectivefrom) from \n"
			+ "  item i join itempriceslab i1 on i.itemid=i1.itemid where i.orgid=?1 and i.itemname=?2)")
	Set<Object[]> getPriceDetails(Long orgId, String itemName);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getFgPartStockUpdateDocId(Long orgId, String finYear, String branchCode, String screenCode);
	
	@Query(nativeQuery = true,value = "select orgid, itemname,itemdesc ,concat(itemname, ' - ', itemdesc) from item\n"
			+ "where itemtype = 'FG' and active=1 and orgid = ?1\n"
			+ "group by orgid, itemname,itemdesc")
	Set<Object[]> getPartNameAndDesc(Long orgId);
	
	@Query(nativeQuery = true,value = "SELECT f.orgid,f.finalfgpartstockupdateid,f.docdate,f.routecardno,f.workorderno,f.tolocation,f.part,f.partdesc,f.qty,f.unit,concat(f.part ,' - ',f.partdesc) as partname,f.docid \n"
			+ "FROM finalfgpartstockupdate f \n"
			+ "where f.orgid=?1 and  (?2 IS NULL OR f.docdate >= ?2 )\n"
			+ "		 and (?3 is null or f.docdate <= ?3 ) \n"
			+ "and (?4 ='ALL'  or concat(f.part ,' - ',f.partdesc) =?4)")
	Set<Object[]> getFinalFgPartStockUpdateReport(Long orgId, String fromDate,String toDate,String partName);
}
