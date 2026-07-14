package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ToolsIssueToCalibrationVO;

@Repository
public interface ToolsIssueToCalibrationRepo extends JpaRepository<ToolsIssueToCalibrationVO, Long> {

	@Query(nativeQuery = true, value = "select * from toolissuetocalibration where orgid=?1 and finyear=?2 and branchcode=?3")
	List<ToolsIssueToCalibrationVO> findToolsIssueToCalibrationByOrgId(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select * from toolissuetocalibration where toolissuetocalibrationid=?1")
	ToolsIssueToCalibrationVO findToolsIssueToCalibrationById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getToolsIssueToCalibrationDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "select m.instrumentcode,m.instrumentname,m.instrumentdesc,i.primaryunit from measuringinstruments m\r\n"
			+ "left join item i on i.itemname = m.instrumentname where\r\n"
			+ " m.finyear=?2\r\n"
			+ "and m.branchcode=?3\r\n"
			+ "and m.active = 1\r\n"
			+ "and m.cancel = 0  and m.orgid=?1\r\n"
			+ "and i.materialtype = 'INSTRUMENT' group by m.instrumentcode,m.instrumentname,m.instrumentdesc,i.primaryunit ")
	Set<Object[]> findInstrumentdetforToolIssueForcalibration(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select a.partyname , Concat( b.addressline1,b.addressline2,b.addressline3 ) as partyaddress from partymaster a join partyaddress b on a.partymasterid=b.partymasterid where partytype='TOOLS-CALIBRATION' and orgid=?1 and finyear=?2 and branchcode=?3 and active=1 and cancel =0 ")
	Set<Object[]> getPartyMasterDetailsforToolIssueForcalibration(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select partyname from partymaster where  orgid=?1 and finyear=?2 and branchcode=?3  and partytype='CUSTOMER' and active=1 and cancel =0 ")
	Set<Object[]> getCustomerNameforTollIssueForEntry(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select  concat(itemname, ' - ', itemdesc) from item\r\n"
			+ " where i.orgid=?1 "
			+ " where itemtype = \"instrument\" and active = 1")
	Set<Object[]> getItemNameAndDese(Long orgId);
	
	@Query(nativeQuery = true, value = "select  concat(instrumentcode, ' - ', instrumentname) as item from measuringinstruments where orgid=?1  and active=1 and cancel=0")
	Set<Object[]> getInstrumentCodeAndName(Long orgId);

	@Query(nativeQuery = true, value = "SELECT \n"
			+ "    t.docid,\n"
			+ "    t.docdate,\n"
			+ "    t.issuepartyname,\n"
			+ "    t.issuepartyaddress,\n"
			+ "    d.instrumentid,\n"
			+ "    d.instrumentname,\n"
			+ "    CONCAT(d.instrumentid, ' - ', d.instrumentname) AS instrumentdetails,\n"
			+ "    t.toolissuetocalibrationid \n"
			+ "FROM toolissuetocalibration t\n"
			+ "JOIN toolisstocalibdetails d\n"
			+ "    ON d.toolissuetocalibrationid = t.toolissuetocalibrationid\n"
			+ "WHERE t.orgid = ?1\n"
			+ "  AND (?2 IS NULL OR t.docdate >= ?2)\n"
			+ "  AND (?3 IS NULL OR t.docdate <= ?3)\n"
			+ "  AND ( ?4 = 'ALL'\n"
			+ "        OR CONCAT(d.instrumentid, ' - ', d.instrumentname) = ?4 )")
	Set<Object[]> getToolIssueToCalibrationReport(Long orgId, String fromdate, String todate, String issuepartyname);

	ToolsIssueToCalibrationVO findByDocId(String docId);

	ToolsIssueToCalibrationVO getAllToolsIssueToCalibrationById(Long id);
}
