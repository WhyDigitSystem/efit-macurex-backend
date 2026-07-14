package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.EcnApprovalRecordVO;

@Repository
public interface EcnApprovalRecordRepo extends JpaRepository<EcnApprovalRecordVO, Long> {
	@Query(nativeQuery = true, value = "select * from ecnapprovalrecord where orgid=?1 and finyear=?2 and branchcode=?3")
	List<EcnApprovalRecordVO> getAllEcnApprovalRecordByOrgId(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select * from ecnapprovalrecord where ecnapprovalrecordid=?1 ")
	EcnApprovalRecordVO getAllEcnApprovalRecordById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getEcnApprovalRecordDocId(Long orgId, String finyear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "select  distinct drawingno from drawingmaster where orgid=?1 and partno=?2  and active=1 and cancel=0")
	Set<Object[]> getDrawingNo(Long orgId,String partNo);
	
	
	@Query(nativeQuery = true, value = "WITH ranked_data AS (\r\n"
			+ "    SELECT \r\n"
			+ "        drawingrevno,\r\n"
			+ "        docdate,\r\n"
			+ "        ROW_NUMBER() OVER (ORDER BY docdate DESC) AS rn_desc,\r\n"
			+ "        ROW_NUMBER() OVER (ORDER BY docdate ASC)  AS rn_asc\r\n"
			+ "    FROM drawingmaster\r\n"
			+ "    WHERE orgid =?1\r\n"
			+ "      AND drawingno = ?2\r\n"
			+ "      AND active = 1\r\n"
			+ "      AND cancel = 0\r\n"
			+ ")\r\n"
			+ "SELECT\r\n"
			+ "    MAX(CASE WHEN rn_desc = 1 THEN drawingrevno END) AS currentRevNo,\r\n"
			+ "    MAX(CASE WHEN rn_desc = 1 THEN docdate END)      AS currentRevDate,\r\n"
			+ "    MAX(CASE WHEN rn_asc  = 1 THEN drawingrevno END) AS oldRevNo\r\n"
			+ "FROM ranked_data")
	Set<Object[]> getDrawingOldRevNo(Long orgId,String drawingNo);

	@Query(nativeQuery = true, value = "SELECT ecnapprovalrecordid, currentrevisiondate, currentrevisionid, customer, departmenta, departmentc, departmentp,\r\n"
			+ " departments, departmentv, detailsofrevision, documentformateno, drawingno, finyear, oldrev, orgid, partname,\r\n"
			+ " partno, preparedby, resonforrevision, remarks, tagedrawingsmodifiedby, statusa, statusc, statusv, \r\n"
			+ " verifiedby FROM ecnapprovalrecord where orgid =?1 AND branchcode = ?2  \r\n"
			+ "AND (?3 IS NULL OR docdate >= ?3)\r\n"
			+ "	  AND (?4 IS NULL OR docdate <= ?4)  group by  ecnapprovalrecordid, currentrevisiondate, currentrevisionid, customer, departmenta, departmentc, departmentp,\r\n"
			+ " departments, departmentv, detailsofrevision, documentformateno, drawingno, finyear, oldrev, orgid, partname,\r\n"
			+ " partno, preparedby, resonforrevision, remarks, tagedrawingsmodifiedby, statusa, statusc, statusv, \r\n"
			+ " verifiedby")
	Set<Object[]> getEcnApprovalRecordReport(Long orgId, String branchCode, String fromDate, String toDate);

	EcnApprovalRecordVO findByDocId(String docId);
}