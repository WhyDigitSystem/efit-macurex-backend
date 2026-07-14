package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.InprocessInspectionVO;

@Repository
public interface InprocessInspectionRepo extends JpaRepository<InprocessInspectionVO, Long> {

	@Query(nativeQuery = true, value = "select * from  inprocessinspection where orgid=?1 and finyear=?2 and branchcode=?3 ")
	List<InprocessInspectionVO> getAllInprocessInspectionByOrgId(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select * from inprocessinspection  where inprocessinspectionid=?1")
	InprocessInspectionVO getInprocessInspectionById(Long id);
	
	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getInprocessInspectionDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "select a.docid,a.wono,a.fgpartname,fgpartdesc,a.customername from routecardentry a where \r\n"
			+ "a.orgid=?1 and finyear=?2 and branchcode=?3 and a.active =1 and a.status='PENDING' group by a.docid,a.wono,a.fgpartname,fgpartdesc,a.customername order by a.docid desc")
	Set<Object[]> getDocIdFromRouteCardNumber(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select a.drawingno from drawingmaster a where a.orgid=?1  and finyear=?2 and branchcode=?3 and a.fgpartno=?4 and \r\n"
			+ " a.active = 1 and a.cancel = 0 order by a.drawingno desc")
	Set<Object[]> getDrawingNumberForInProcessInspection(Long orgId, String finYear, String branchCode, String fgPartno);

	@Query(nativeQuery = true, value = "select employeename,employeecode from employeemaster where orgid=?1 and branchcode=?2 and active = 1 order by employeecode asc")
	Set<Object[]> getEmployeeNameFromEmployeeMaster(Long orgId, String branchCode);

	@Query(nativeQuery = true, value = "SELECT\r\n"
			+ "    inprocessinspectionid,\r\n"
			+ "    active,\r\n"
			+ "    approvedby,\r\n"
			+ "    checkedby,\r\n"
			+ "    customer,\r\n"
			+ "    docdate,\r\n"
			+ "    docid,\r\n"
			+ "    documentformatno,\r\n"
			+ "    drawingno,\r\n"
			+ "    lotqty,\r\n"
			+ "    materialdrawingno,\r\n"
			+ "    naration,\r\n"
			+ "    orgid,\r\n"
			+ "    partname,\r\n"
			+ "    partno,\r\n"
			+ "    receivedqty,\r\n"
			+ "    routecardno,\r\n"
			+ "    sampleqty,\r\n"
			+ "    workorderno,\r\n"
			+ "    branch,\r\n"
			+ "    branchcode,\r\n"
			+ "    finyear\r\n"
			+ "FROM inprocessinspection\r\n"
			+ "WHERE orgid = ?1\r\n"
			+ "  AND branchcode = ?2\r\n"
			+ "  AND (?3 is null or docdate >= ?3) and (?4 is null or docdate <= ?4) and (routecardno=?5 or ?5='ALL')")
	Set<Object[]> getInProcessInspectionReport(Long orgId, String branchCode, String fromDate, String toDate,String routeCardNo);

	@Query(nativeQuery = true, value = "SELECT \r\n"
			+ "    d1.documentformateno,\r\n"
			+ "    d1.revisionno,\r\n"
			+ "    d1.issueno\r\n"
			+ "FROM documentnumberchange d\r\n"
			+ "JOIN documentnumberchangedetails d1\r\n"
			+ "    ON d.documentnumberchangeid = d1.documentnumberchangeid\r\n"
			+ "WHERE d.orgid = ?1\r\n"
			+ "    AND d.documentscreenname = ?2\r\n"
			+ "    AND d.active = 1\r\n"
			+ "    AND d.cancel = 0\r\n"
			+ "    AND d1.date = (\r\n"
			+ "        SELECT MAX(d2.date)\r\n"
			+ "        FROM documentnumberchangedetails d2\r\n"
			+ "        JOIN documentnumberchange d3\r\n"
			+ "            ON d2.documentnumberchangeid = d3.documentnumberchangeid\r\n"
			+ "        WHERE d3.documentscreenname = d.documentscreenname\r\n"
			+ "    )")
			
	Set<Object[]> getDocumentFormateNumber(Long orgId, String screenName);

	InprocessInspectionVO findByDocId(String docId);


	InprocessInspectionVO getAllInprocessInspectionImagesById(Long id);

}
