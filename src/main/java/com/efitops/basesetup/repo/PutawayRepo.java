package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PutawayVO;

@Repository
public interface PutawayRepo extends JpaRepository<PutawayVO, Long> {

	@Query(nativeQuery = true, value = "select * from putaway where orgid=?1 and finyear=?2 and branchcode=?3 ")
	List<PutawayVO> findPutawayByOrgId(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select * from putaway where putawayid=?1")
	List<PutawayVO> findPutawayById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getPutawayDocId(Long orgId, String finYear, String branchCode, String screenCode);

//	@Query(nativeQuery = true, value = "SELECT\r\n"
//			+ "    a.grnno,\r\n"
//			+ "    a.grndate,\r\n"
//			+ "    a.suppliername,\r\n"
//			+ "    a.invdcno,\r\n"
//			+ "    c.invoiceno,\r\n"
//			+ "    c.vehicleno\r\n"
//			+ "FROM grn a\r\n"
//			+ "JOIN gateinwardentry c\r\n"
//			+ "    ON c.invoiceno = a.invdcno and c.orgid=a.orgid \r\n"
//			+ "JOIN (\r\n"
//			+ "    SELECT\r\n"
//			+ "        g.grnid,\r\n"
//			+ "        SUM(b.acceptqty) AS total_acceptqty\r\n"
//			+ "    FROM grn g\r\n"
//			+ "    JOIN grndetails b ON b.grnid = g.grnid\r\n"
//			+ "    GROUP BY g.grnid\r\n"
//			+ ") acc ON acc.grnid = a.grnid\r\n"
//			+ "LEFT JOIN (\r\n"
//			+ "    SELECT\r\n"
//			+ "        p.grnno,\r\n"
//			+ "        SUM(p1.putawayqty) AS total_putawayqty\r\n"
//			+ "    FROM putaway p\r\n"
//			+ "    JOIN putawaydetails p1 ON p1.putawayid = p.putawayid\r\n"
//			+ "    GROUP BY p.grnno\r\n"
//			+ ") pa ON pa.grnno = a.grnno\r\n"
//			+ "WHERE a.orgid = ?1 \r\n"
//			+ "  AND acc.total_acceptqty > COALESCE(pa.total_putawayqty, 0)\r\n"
//			+ "ORDER BY a.grnno")
//	Set<Object[]> findGrnDetailsForPutaway(Long orgId);
	
	@Query(nativeQuery = true, value = "SELECT  \n"
			+ "a.grnno,a.grndate,a.suppliername,a.invdcno, g2.invoiceno,g2.vehicleno,	\n"
			+ "		    SUM(b.acceptqty) - COALESCE(SUM(p1.putawayqty), 0) AS remainingqty\n"
			+ "			FROM grn a\n"
			+ "			JOIN grndetails b \n"
			+ "			    ON a.grnid = b.grnid\n"
			+ "                join 	gateinwardentry g2 on g2.invoiceno = a.invdcno and g2.orgid=a.orgid\n"
			+ "			LEFT JOIN putaway p \n"
			+ "			    ON p.grnno = a.grnno\n"
			+ "			LEFT JOIN putawaydetails p1 \n"
			+ "			    ON p.putawayid = p1.putawayid \n"
			+ "			   AND p1.item = b.itemcode\n"
			+ "			WHERE a.orgid = ?1 \n"
			+ "			  AND a.active = 1\n"
			+ "			  AND a.cancel = 0\n"
			+ "			GROUP BY a.grnno,a.grndate,a.suppliername,a.invdcno,g2.invoiceno,g2.vehicleno\n"
			+ "			    having  SUM(b.acceptqty) - COALESCE(SUM(p1.putawayqty), 0) >0\n"
			+ "			ORDER BY \n"
			+ "			    a.grnno")
	Set<Object[]> findGrnDetailsForPutaway(Long orgId);

	@Query(nativeQuery = true, value = "select  locationcode from stocklocation where  orgid=?1 and  cancel='F'")
	Set<Object[]> findLocationCodeForPutaway(Long orgId);

	@Query(nativeQuery = true, value = "SELECT  \n"
			+ "    b.itemcode,\n"
			+ "    b.itemdesc,\n"
			+ "    b.primaryunit,\n"
			+ "    SUM(b.acceptqty) AS acceptqty,\n"
			+ "    COALESCE(SUM(p1.putawayqty), 0) AS putawayqty,\n"
			+ "    SUM(b.acceptqty) - COALESCE(SUM(p1.putawayqty), 0) AS remainingqty\n"
			+ "FROM grn a\n"
			+ "JOIN grndetails b \n"
			+ "    ON a.grnid = b.grnid\n"
			+ "LEFT JOIN putaway p \n"
			+ "    ON p.grnno = a.grnno\n"
			+ "LEFT JOIN putawaydetails p1 \n"
			+ "    ON p.putawayid = p1.putawayid \n"
			+ "   AND p1.item = b.itemcode\n"
			+ "WHERE a.orgid = ?1\n"
			+ "  AND a.grnno = ?2\n"
			+ "  AND a.active = 1\n"
			+ "  AND a.cancel = 0\n"
			+ "GROUP BY \n"
			+ "    b.itemcode,\n"
			+ "    b.itemdesc,\n"
			+ "    b.primaryunit having  SUM(b.acceptqty) - COALESCE(SUM(p1.putawayqty), 0) >0\n"
			+ "ORDER BY \n"
			+ "    b.itemcode")
	Set<Object[]> findFillGridForPutaway(Long orgId, String grnNo);

//	@Query(nativeQuery = true, value = "select  rackno from rackmaster where  orgid=?1 ")
//	Set<Object[]> findRackNoForPutaway(Long orgId);
	
	@Query(nativeQuery = true, value = "select rm.rackno,coalesce(sum(rs.qty), 0) as qty  from rackmaster rm left join rackstockdetails rs on rs.rackno = rm.rackno and rs.orgid = rm.orgid\r\n"
			+ "where rm.orgid =?1 group by rm.rackno having coalesce(sum(rs.qty), 0) = 0 order by rm.rackno")
	Set<Object[]> findRackNoForPutaway(Long orgId);
	
	@Query(nativeQuery = true, value = "select p1.rackno,p1.putawayqty  from putaway p join putawaydetails p1 on p.putawayid=p1.putawayid  where p.orgid=?1 and \r\n"
			+ "p1.item=?2")
	Set<Object[]> getRackDetails(Long orgId,String item);
	
	@Query(nativeQuery = true, value = "SELECT\n"
			+ "    a.docid,\n"
			+ "    a.docdate,\n"
			+ "    a.dcno,\n"
			+ "    a.fromlocation,\n"
			+ "    a.goodstype,\n"
			+ "    a.grnno,\n"
			+ "    a.tolocation,\n"
			+ "    a.vehicleno,\n"
			+ "    a.supplier,\n"
			+ "    a.totalputaway_qty,\n"
			+ "    b.item,\n"
			+ "    b.itemdesc,\n"
			+ "    b.rackno,\n"
			+ "    b.putawayqty,\n"
			+ "    b.recqty,a.putawayid\n"
			+ "FROM\n"
			+ "    putaway a,\n"
			+ "    putawaydetails b\n"
			+ "WHERE\n"
			+ "    a.putawayid = b.putawayid\n"
			+ "    AND a.orgid =?1 \n"
			+ "     AND (a.supplier =?2 OR  ?2= 'ALL')\n"
			+ "    AND (?3 IS NULL OR a.docdate >= ?3)\n"
			+ "    AND (?4 IS NULL OR a.docdate <= ?4)\n"
			+ "  AND (a.branchcode =?5 OR ?5= 'ALL')  and (a.grnno=?6  or ?6='ALL') \n"
			+ "ORDER BY\n"
			+ "    a.createdon DESC")
	Set<Object[]> getPutAwayDetails(Long orgId, String supplierName,String fromDate,String toDate,String branchCode,String grnNo);
}
