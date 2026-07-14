package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.QualityDocumentChangeRecordVO;

@Repository
public interface QualityDocumentChangeRecordRepo extends JpaRepository<QualityDocumentChangeRecordVO, Long> {

	@Query(nativeQuery = true, value = "select * from qualitydocumentchangerecord where orgid=?1 and finyear=?2 and branchcode=?3")
	List<QualityDocumentChangeRecordVO> getAllQualityDocumentChangeRecordByOrgId(Long orgId, String finYear,
			String branchCode);

	@Query(nativeQuery = true, value = "select * from qualitydocumentchangerecord where qualitydocumentchangerecordid=?1")
	QualityDocumentChangeRecordVO getAllQualityDocumentChangeRecordById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getQualityDocumentChangeRecordDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "select d.employeename, e.designation from employeedetails e \r\n"
			+ "join employeemaster d\r\n" + " ON e.employeemasterid = d.employeemasterid\r\n"
			+ "where d.orgid=?1 and d.active=1;")
	Set<Object[]> getEmployeeNameAndDesignation(Long orgId);

	@Query(nativeQuery = true, value = "SELECT qualitydocumentchangerecordid,  changes,  currentrevisionstatus, designation, detailsofchangerequired, docdate, docid, documentdescription, \r\n"
			+ "documentformateno, documentrefno,  name,reasonforchange, \r\n"
			+ "recorddate,  signature FROM qualitydocumentchangerecord \r\n"
			+ "WHERE orgid =?1 AND branchcode = ?2  \r\n" + "AND (?3 IS NULL OR docdate >= ?3)\r\n"
			+ "	  AND (?4 IS NULL OR docdate <= ?4)  group by  qualitydocumentchangerecordid,  changes,  currentrevisionstatus, designation, detailsofchangerequired, docdate, docid, documentdescription, \r\n"
			+ "documentformateno, documentrefno,  name,reasonforchange, \r\n" + "recorddate,  signature")
	Set<Object[]> getQualityDocumentChangeRecordReport(Long orgId, String branchCode, String fromDate, String toDate);

	QualityDocumentChangeRecordVO findByDocId(String docId);

}
