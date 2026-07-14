package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.dto.ImageResponseDTO;
import com.efitops.basesetup.entity.DailyPatrolInspectionVO;

@Repository

public interface DailyPatrolInspectionRepo extends JpaRepository<DailyPatrolInspectionVO, Long> {

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getDailyPatrolInspectionDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "select * from dailypatrolinspection d where d.dailypatrolinspectionid=?1")
	Optional<DailyPatrolInspectionVO> getDailyPatrolInspectionById(Long id);

	@Query(nativeQuery = true, value = "select * from dailypatrolinspection d where d.orgid=?1 and d.finyear=?2 and d.branchcode=?3")
	List<DailyPatrolInspectionVO> getAllDPI(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select r.docid,r.fgpartname,r.fgpartdesc From routecardentry r where cancel =0 and  r.orgid=?1 and r.finYear=?2 and r.branchCode=?3  and r.status='PENDING' and active=1 order by r.docid desc")
	Set<Object[]> getRouteCardNoForDailyPatrollInspection(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select m.machineno,m.machinename from machinemaster m where m.orgid=?1 and m.finyear=?2 and m.branchcode=?3 order by m.machineno desc")
	Set<Object[]> getMachineDetailsForDailyPatrolInspection(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select s.shiftmastid,s.shiftname,s.timing from shiftmast s where s.orgid=?1  ")
	Set<Object[]> getShiftDetails1(Long orgId);

//	@Query(nativeQuery = true, value = "select s.shiftmastid,s.shiftname,s.timing from shiftmast s")
//	Set<Object[]> getJobOrderNo1();

	@Query(nativeQuery = true, value = "select a.drawingno from drawingmaster a where a.orgid=?1 and a.finyear=?2 and a.branchcode=?3  and a.fgpartno=?4 and active=1 order by a.drawingno desc")
	Set<Object[]> getDrawingMasterNoForDailyPatrolInspection(Long orgId, String finYear, String branchCode,
			String partNo);

	@Query(nativeQuery = true, value = "select a.docid from joborder a where a.orgid=?1 and a.finyear=?2 and a.branchcode=?3 and a.routecardno=?4  and active=1 order by a.docid desc")
	Set<Object[]> getJobOrderNoForDailyPatrolInspection(Long orgId, String finYear, String branchCode,
			String routeCardNo);

	@Query(value = "select d.orgid,d.docid,d.docdate,d.routecardno,d.partno,d.partname,d.drgno,d.shift,d.machineno,d.machinename,d.time,d.joborderno,\n"
			+ "d1.characteristic,d1.methodofinspection,d1.sample1,d1.sample2,d1.sample3,d.dailypatrolinspectionid\n"
			+ " from dailypatrolinspection d\n"
			+ "join dailypatrolinspectiondetails1 d1 on d.dailypatrolinspectionid = d1.dailypatrolinspectionid\n"
			+ "where d.orgid = ?1 " + "AND (?2 IS NULL OR d.docdate >= ?2) \n"
			+ "AND (?3 IS NULL OR d.docdate <= ?3)", nativeQuery = true)
	Set<Object[]> getDailyPatrolInspectionDetails(Long orgId, String fromDate, String toDate);

	@Query(value = "select  a.employeename,b.department from employeeMaster a join employeedetails b on a.employeemasterid=b.employeemasterid \n"
			+ " where a.orgid=?1 and a.branchcode=?2  and b.department='QUALITY DEPT' and active=1 and cancel=0 group by \n"
			+ "a.employeename,b.department order by  a.employeename", nativeQuery = true)
	Set<Object[]> getEmployeeNameBasedOnDepartment(Long orgId, String branchCode);

	@Query(value = "select distinct employeename from  employeemaster where orgid=?1 and branchcode=?2 and active=1 and cancel=0", nativeQuery = true)
	Set<Object[]> getInspectionByInchargeName(Long orgId, String branchCode);

	DailyPatrolInspectionVO findByDocId(String docId);

	DailyPatrolInspectionVO getAllDailyPatrolInspectionById(Long id);

	DailyPatrolInspectionVO getAllDailyPatrolInspectionImagesById(Long id);

}
