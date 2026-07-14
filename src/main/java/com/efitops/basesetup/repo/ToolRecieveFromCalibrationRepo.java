package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ToolRecieveFromCalibrationVO;

@Repository
public interface ToolRecieveFromCalibrationRepo extends JpaRepository<ToolRecieveFromCalibrationVO, Long> {

	@Query(nativeQuery = true, value = "select * from toolrecievefromcalibration where orgid=?1 and finyear=?2 and branchcode=?3")
	List<ToolRecieveFromCalibrationVO> findToolsRecieveFromCalibrationByOrgId(Long orgId, String finYear,
			String branchCode);

	@Query(nativeQuery = true, value = "select * from toolrecievefromcalibration where toolrecievefromcalibrationid=?1")
	ToolRecieveFromCalibrationVO findToolsRecieveFromCalibrationById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getToolsRecieveFromCalibrationDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "SELECT \r\n" + "    a.docid,\r\n" + "    a.docdate,\r\n"
			+ "    a.issuepartyname\r\n" + "FROM toolissuetocalibration a \r\n" + "WHERE a.orgid = ?1 \r\n"
			+ "  AND a.finyear = ?2 \r\n" + "  AND a.branchcode = ?3 \r\n" + "  AND a.active = 1\r\n"
			+ "  AND a.cancel = 0\r\n" + "  AND NOT EXISTS (\r\n" + "        SELECT 1\r\n"
			+ "        FROM toolrecievefromcalibration b\r\n" + "        WHERE b.orgid = ?1 \r\n"
			+ "          AND b.finyear = ?2 \r\n" + "          AND b.branchcode = ?3 \r\n"
			+ "          AND b.active = 1\r\n" + "          AND b.cancel = 0\r\n"
			+ "          AND b.docid = a.docid   \r\n" + "  )")
	Set<Object[]> getIssueDetailsforToolIssueNoForRecieveFormCalibration(Long orgId, String finyear, String branchCode);

	@Query(nativeQuery = true, value = "select instrumentid,instrumentname from toolisstocalibdetails b join toolissuetocalibration a on a.toolissuetocalibrationid=b.toolissuetocalibrationid   WHERE a.orgid =?1 \r\n"
			+ "  AND a.finyear = ?2 \r\n" + "  AND a.branchcode =?3 \r\n" + "  And a.docid=?4 \r\n"
			+ "  AND a.active = 1\r\n" + "  AND a.cancel = 0")
	Set<Object[]> findInstrumentdetforToolIssueNoForRecieveFormCalibration(Long orgId, String finYear,
			String branchCode, String issueNo);

	@Query(nativeQuery = true, value = "SELECT\n"
			+ "    t.orgid,\n"
			+ "    t.docid,\n"
			+ "    t.docdate,\n"
			+ "    t.issueno,\n"
			+ "    t.issuedate,\n"
			+ "    t.recievedfrom,\n"
			+ "    CONCAT(d.instrumentid, ' - ', d.instrumentname) AS instrumentdetails,\n"
			+ "    CASE\n"
			+ "        WHEN c.docid IS NOT NULL THEN 'Received'\n"
			+ "        ELSE 'Not Received'\n"
			+ "    END AS status\n"
			+ "FROM toolrecievefromcalibration t\n"
			+ "JOIN toolRecieveFromCalibrationDetails d\n"
			+ "    ON d.toolrecievefromcalibrationid = t.toolrecievefromcalibrationid\n"
			+ "LEFT JOIN toolissuetocalibration c\n"
			+ "    ON t.issueno = c.docid\n"
			+ "WHERE t.orgid = ?1\n"
			+ "  AND (?2 IS NULL OR t.docdate >= ?2)\n"
			+ "  AND (?3 IS NULL OR t.docdate <= ?3)\n"
			+ "HAVING status = 'Received'")
	Set<Object[]> getToolRecieveFromCalibration(Long orgId, String fromdate, String todate);

	@Query(nativeQuery = true, value = "SELECT i.orgid, CONCAT(i.itemname, ' - ', i.itemdesc) " + "FROM item i "
			+ "WHERE i.orgid = ?1 " + "AND i.itemtype = 'instrument' " + "AND i.active = 1")
	Set<Object[]> getItemNameAndDesc(Long orgId);

	ToolRecieveFromCalibrationVO findByDocId(String docId);

	ToolRecieveFromCalibrationVO getAllToolRecieveFromCalibrationVOById(Long id);

}
