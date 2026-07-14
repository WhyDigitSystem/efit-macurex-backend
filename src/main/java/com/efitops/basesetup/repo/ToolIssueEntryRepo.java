package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ToolIssueEntryVO;

@Repository
public interface ToolIssueEntryRepo extends JpaRepository<ToolIssueEntryVO, Long> {

	@Query(nativeQuery = true, value = "select*from toolissueentry where orgid=?1 and finyear=?2 and branchcode=?3 ")
	List<ToolIssueEntryVO> findToolIssueEntryByOrgId(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select*from toolissueentry where toolissueentryid=?1")
	List<ToolIssueEntryVO> getToolIssueEntryById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getToolIssueEntryDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "select instrumentname,instrumentcode,ranges,leastcount,calibrationfrequence from measuringinstruments where orgid=?1 and finyear=?2 and branchcode=?3 ")
	Set<Object[]> findInstrumentforTollIssueForEntry(Long orgId,String finYear,String branchCode);

	@Query(nativeQuery = true, value = "select count(*) as lastcount from efit_ops.toolissueentry where instrumentname='Pressure Gauge'")
	Set<Object[]> getlastcountforTollIssueForEntry(Long orgId);

	@Query(nativeQuery = true, value = "SELECT\r\n"
			+ "    CONCAT(instrumentcode, ' - ', instrumentdesc) AS instrumentdetails\r\n"
			+ "FROM toolissueentry\r\n"
			+ "WHERE orgid = ?1\r\n"
			+ "  AND cancel = 0\r\n"
			+ "ORDER BY docdate DESC ")
	Set<Object[]> getToolsIssueEntryInstrumentCodeDesc(Long orgId);
	
	@Query(nativeQuery = true, value = "SELECT\n"
			+ "    toolissueentryid,\n"
			+ "    active,\n"
			+ "    calibratedcertificateno,\n"
			+ "    calibrateddate,\n"
			+ "    docdate,\n"
			+ "    docid,\n"
			+ "    dueforcalib,\n"
			+ "    frequencyofcalib,\n"
			+ "    instrumentcode,\n"
			+ "    instrumentdesc,\n"
			+ "\n"
			+ "    CONCAT(instrumentcode, ' - ', instrumentdesc) AS instrumentdetails,\n"
			+ "\n"
			+ "    instrumentname,\n"
			+ "    instrumentrange,\n"
			+ "    lastcount,\n"
			+ "    leastcount,\n"
			+ "    location,\n"
			+ "    orgid,\n"
			+ "    preparedby,\n"
			+ "    seqcode,\n"
			+ "    branch,\n"
			+ "    branchcode,\n"
			+ "    finyear\n"
			+ "FROM toolissueentry\n"
			+ "WHERE orgid = ?1\n"
			+ "  AND cancel = 0\n"
			+ "  AND ( ?2 IS NULL OR DATE(docdate) >= DATE(?2) )\n"
			+ "  AND ( ?3 IS NULL OR DATE(docdate) <= DATE(?3) )\n"
			+ "  AND ( ?4 IS NULL \n"
			+ "        OR CONCAT(instrumentcode, ' - ', instrumentdesc) = ?4 )\n"
			+ "ORDER BY docdate DESC;")
	Set<Object[]> getTollIssueForEntryReport(Long orgId, String fromDate,String toDate,String instrumentCodeAndName);

	ToolIssueEntryVO findByDocId(String docId);

	ToolIssueEntryVO getAlltoolIssueEntryById(Long id);
  

}
