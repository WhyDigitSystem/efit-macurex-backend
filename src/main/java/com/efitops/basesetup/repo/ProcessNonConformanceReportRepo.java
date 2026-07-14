package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ProcessNonConformanceReportVO;

@Repository
public interface ProcessNonConformanceReportRepo extends JpaRepository<ProcessNonConformanceReportVO, Long> {


	@Query(nativeQuery = true, value = "select * from processnonconformancereport where orgid=?1 and finyear=?2 and branchcode=?3 ")
	List<ProcessNonConformanceReportVO> getAllProcessNonConformanceReportByOrgId(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select * from processnonconformancereport where processnonconformancereportid=?1")
	ProcessNonConformanceReportVO getProcessNonConformanceReportById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getProcessNonConformanceReportDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "SELECT processnonconformancereportid, actualdateofcompletion, adequacy, briefdescription, correctiveaction, created, date, disposition, docid, drawingno, effectivenessofcorrective, narration, partname, partno, process,\r\n"
			+ " qtyavailable, qtydefective, responsibility, rootcause, signature, targetdate, \r\n"
			+ " verify,parttype from processnonconformancereport where orgid =?1 and\r\n"
			+ " (?2 is null or docdate >= ?2) and (?3 is null or docdate <= ?3)  and \r\n"
			+ " (partno=?4 or ?4='ALL')  \r\n"
			+ " and active = 1 AND cancel = 0  group by processnonconformancereportid, actualdateofcompletion, adequacy, briefdescription, correctiveaction, created, date, disposition, docid, drawingno, effectivenessofcorrective, narration, partname, partno, process,\r\n"
			+ " qtyavailable, qtydefective, responsibility, rootcause, signature, targetdate, \r\n"
			+ " verify,parttype")
	Set<Object[]> getProcessNonConformanceReport(Long orgId, String fromdate, String todate,String partNo);

	ProcessNonConformanceReportVO findByDocId(String docId);

	ProcessNonConformanceReportVO getAllProcessNonConformanceReportById(Long id);
}
