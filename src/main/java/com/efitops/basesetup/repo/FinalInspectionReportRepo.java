package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.FinalInspectionReportVO;

@Repository
public interface FinalInspectionReportRepo extends JpaRepository<FinalInspectionReportVO, Long> {

	@Query(nativeQuery = true, value = "select * from  finalinspectionreport where orgid=?1 and finyear=?2 and branchcode=?3")
	List<FinalInspectionReportVO> getAllFinalInspectionReportByOrgId(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select * from finalinspectionreport  where finalinspectionreportid=?1")
	FinalInspectionReportVO getFinalInspectionReportById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getFinalInspectionReportDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "select a.docid from routecardentry a where a.orgid=?1 and a.finyear=?2 and a.branchcode=?3 and a.status='PENDING' and a.active=1 order by a.docid desc")
	Set<Object[]> getRouteCardNumberForFinalInspectionReport(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select a.fgpartname,a.fgpartdesc,b.primaryunit,a.customername,c.customerpono,a.invoice from routecardentry a,item b, workorder c where a.active = 1 and  c.customername=a.customername and c.orgid=a.orgid and\r\n"
			+ "            b.itemname=a.fgpartname and b.orgid=a.orgid and \r\n"
			+ "            a.orgid=?1 and a.finyear=?2 and a.branchcode=?3  and a.docid=?4  group by a.fgpartname,a.fgpartdesc,b.primaryunit,a.customername,c.customerpono,a.invoice order by  a.fgpartname")
	Set<Object[]> getPartNameForFinalInspectionReport(Long orgId, String finYear, String branchCode,
			String routeCardNumber);

	@Query(value = "select f.orgid, f.docid, f.docdate, f.inspectiondate, f.invoiceno, f.routecard,\n"
			+ "		    f.partname, f.partno, f.untis, f.customer, f.pono, f.lotqty, f.sampleqty,\n"
			+ "		    f.documentformatno,\n"
			+ "		    f1.characteristics, f1.methodofinspection, f1.sample1, f1.sample2, f1.sample3, f1.specification, f2.observation,f.finalinspectionreportid\n"
			+ "		    from finalinspectionreport f\n"
			+ "		    join firdimensionalinspection f1 on f.finalinspectionreportid = f1.finalinspectionreportid\n"
			+ "		    join firappearanceiunspection f2 on f.finalinspectionreportid = f2.finalinspectionreportid\n"
			+ "		    where f.orgid = ?1\n" + "		    and (?2 is null or f.docdate >= ?2)\n"
			+ "		    and (?3 is null or f.docdate <= ?3)", nativeQuery = true)
	Set<Object[]> getFinalInspectionReportDetails(Long orgId, String fromDate, String toDate);

	FinalInspectionReportVO findByDocId(String docId);

	FinalInspectionReportVO getAllFinalInspectionImagesById(Long id);
}
