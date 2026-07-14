package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SettingApprovalVO;

@Repository
public interface SettingApprovalRepo extends JpaRepository<SettingApprovalVO, Long> {

	@Query(nativeQuery = true, value = "select * from  settingapproval where orgid=?1 and finyear=?2 and branchcode=?3 ")
	List<SettingApprovalVO> getAllSettingApprovalByOrgId(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select * from  settingapproval where settingapprovalid=?1")
	SettingApprovalVO getSettingApprovalById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getSettingApprovalDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "select a.docid,a.fgpartname,a.fgpartdesc,b.operationdesc from routecardentry a join routecardentrydetails b on a.routecardentryid=b.routecardentryid where a.orgid=?1 and a.finyear=?2 and a.branchcode=?3 and a.status='PENDING' and a.active=1 order by a.docid desc")
	Set<Object[]> findRouteCardDetailsForSettingApproval(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select a.drawingno from drawingmaster a where a.orgid=?1 and a.finyear=?2 and a.branchcode=?3 and  a.fgpartno=?4 and active=1 order by a.drawingno desc")
	Set<Object[]> findDrawingNoForSettingApproval(Long orgId, String finYear, String branchCode, String partNo);

	@Query(nativeQuery = true, value = "select a.machineno,a.machinename from machinemaster a where a.orgid=?1 and finyear=?2 and branchCode=?3  and active=1 order by a.machineno desc")
	Set<Object[]> findMachineNoForSettingApproval(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select  a.employeename from employeeMaster a join employeedetails b on a.employeemasterid=b.employeemasterid  where a.orgid=?1 and a.branchcode=?2  and b.designation='OPERATOR' and active=1 and cancel=0 group by \r\n"
			+ " a.employeename order by  a.employeename")
	Set<Object[]> findOperatorNameForSettingApproval(Long orgId, String branchCode);

	@Query(nativeQuery = true, value = "select  a.employeename from employeeMaster a join employeedetails b on a.employeemasterid=b.employeemasterid  where a.orgid=?1 and a.branchcode=?2  and b.designation='SETTER' and active=1 and cancel=0 group by \r\n"
			+ " a.employeename order by  a.employeename")
	Set<Object[]> findSetterNameForSettingApproval(Long orgId, String branchCode);

	@Query(nativeQuery = true, value = "select  a.employeename from employeeMaster a join employeedetails b on a.employeemasterid=b.employeemasterid  where a.orgid=?1 and a.branchcode=?2  and b.designation='QUALITY' and active=1 and cancel=0 group by \r\n"
			+ " a.employeename order by  a.employeename")
	Set<Object[]> findQualityNameForSettingApproval(Long orgId, String branchCode);

	@Query(nativeQuery = true, value = "select  a.employeename from employeeMaster a join employeedetails b on a.employeemasterid=b.employeemasterid  where a.orgid=?1 and a.branchcode=?2  and b.designation='SHIFTINCHARGE' and active=1 and cancel=0 group by \r\n"
			+ " a.employeename order by  a.employeename")
	Set<Object[]> findShiftInChargeForSettingApproval(Long orgId, String branchCode);

	@Query(nativeQuery = true, value = "SELECT\r\n"
			+ "    sa.settingapprovalid,\r\n"
			+ "    sa.docid,\r\n"
			+ "    sa.partno,\r\n"
			+ "    sa.partname,\r\n"
			+ "    sa.operation,\r\n"
			+ "    sa.operatorname,\r\n"
			+ "    sad.settingapprovaldetailsid,\r\n"
			+ "    sad.characteristics,\r\n"
			+ "    sad.lsl,\r\n"
			+ "    sad.usl,\r\n"
			+ "    sad.methodofinspection,\r\n"
			+ "    sad.specification,\r\n"
			+ "    sad.qulity1,\r\n"
			+ "    sad.qulity2,\r\n"
			+ "    sad.qulity3,\r\n"
			+ "    sad.qulity4,\r\n"
			+ "    sad.qulity5,\r\n"
			+ "    sad.setter1,\r\n"
			+ "    sad.setter2,\r\n"
			+ "    sad.setter3,\r\n"
			+ "    sad.setter4,\r\n"
			+ "    sad.setter5,\r\n"
			+ "    sa.routecardno\r\n"
			+ "FROM settingapproval sa\r\n"
			+ "LEFT JOIN settingapprovaldetails sad\r\n"
			+ "    ON sa.settingapprovalid = sad.settingapprovalid\r\n"
			+ "WHERE sa.orgid = ?1\r\n"
			+ "  AND sa.branchcode = ?2\r\n"
			+ " and (?3 IS NULL OR sa.docdate >= ?3)\r\n"
			+ "AND (?4 IS NULL OR sa.docdate <= ?4) and (sa.routecardno=?5 or ?5='ALL')\r\n"
			+ "ORDER BY sa.docdate, sa.docid")
	Set<Object[]> getSettingApprovalReport(Long orgId, String branchCode, String fromDate, String toDate,String routeCardNo);

	@Query(nativeQuery = true, value = "select  a.employeename,b.designation from employeeMaster a join employeedetails b on a.employeemasterid=b.employeemasterid \r\n"
			+ " where a.orgid=?1 and a.branchcode=?2  and active=1 and cancel=0 group by \r\n"
			+ "a.employeename,b.designation order by  a.employeename")
	Set<Object[]> getEmployeeNameBasedOnDesgnation(Long orgId, String branchCode);

	SettingApprovalVO findByDocId(String docId);
}
