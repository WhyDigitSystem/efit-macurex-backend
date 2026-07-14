package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.FgIssueToPackingVO;

@Repository
public interface FgIssueToPackingRepo extends JpaRepository<FgIssueToPackingVO, Long> {

	@Query(nativeQuery = true, value = "select * from fgissuetopacking where orgid=?1")
	List<FgIssueToPackingVO> getAllFgIssueToPackingVOByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from fgissuetopacking where fgissuetopackingid=?1")
	FgIssueToPackingVO getFgIssueToPackingVOById(Long id);

	@Query(nativeQuery = true,value ="select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and  screencode=?2")
	String getFgIssueToPackingDocId(Long orgId, String screenCode);


	@Query(nativeQuery = true,value = "select departmentname,departmentcode from department where orgid=?1"
			+ "  and active=1 and cancel=0 group by \r\n"
			+ "			 departmentname,departmentcode order by  departmentname")	
	Set<Object[]> findDeptfromFgIssueToPacking(Long orgId);
	
	@Query(nativeQuery = true,value = "select docid from routecardentry where orgid=?1 and active=1 and cancel=0 group by \r\n"
			+ " docid order by  docid")
	Set<Object[]> findRouteCardEntryNoFromFgIssueToPacking(Long orgId);

	@Query(nativeQuery = true,value = "SELECT a.itemname, a.itemdesc, a.primaryunit, b.fgqty \r\n"
			+ "			FROM item a JOIN routecardentry b ON b.fgpartname = a.itemname  \r\n"
			+ "			WHERE a.orgid=?1 and b.docid =?2  ORDER BY a.itemname")
	Set<Object[]> findItemDetailsFromFgIssueToPacking(Long orgId, String routeCardEntryNo);
	
	@Query(nativeQuery = true,value = "SELECT f.orgid, f.fgissuetopackingid,f.docdate,f.fromdept,f.todept,f.routecardno,f1.partname,f1.partdesc,f1.issueqty,f.docid\n"
			+ " FROM fgissuetopacking f\n"
			+ " join fgissuetopackingdetails f1 on f1.fgissuetopackingid = f.fgissuetopackingid\n"
			+ "where f.orgid =?1  and  (?2 IS NULL OR f.docdate >= ?2 )\n"
			+ "		 and (?3 is null or f.docdate <= ?3 ) \n"
			+ " and (f.routecardno =?4  or ?4='ALL')")
	Set<Object[]> getFgIssueToPackingReport(Long orgId, String fromDate,String toDate,String routeCardNo);

}
