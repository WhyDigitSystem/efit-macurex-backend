package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.efitops.basesetup.entity.WorkOrderShortCloseVO;

@Repository
public interface WorkOrderShortCloseRepo extends JpaRepository<WorkOrderShortCloseVO, Long> {

	@Query(nativeQuery = true, value = "select * from workordershortclose  where  orgid=?1 and finyear=?2 and branchcode=?3 ")
	List<WorkOrderShortCloseVO> getAllWorkOrderShortCloseByOrgId(Long orgId, String finYear, String branchCode);

	@Query(nativeQuery = true, value = "select * from workordershortclose  where  workordershortcloseid=?1")
	WorkOrderShortCloseVO getWorkOrderShortCloseById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getWorkOrderShortCloseDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "select w.docid,w.docdate,w.customername,w.customercode,w.customerpono,w.currency,w.productionmgr from workorder w\r\n"
			+ "where w.orgid=?1 and w.branchcode=?2   and w.docid=?3 and w.active=1  and cancel=0")
	Set<Object[]> getWorkOrderNumber(Long orgId, String branchCode, String workOrderNo);

	@Query(nativeQuery = true, value = "select w1.partno,w1.partname,w1.drawingno,w1.revisionno,w1.uom,w1.ordqty from workorder w join workorderdetails w1 on w.workorderid=w1.workorderid\r\n"
			+ "where w.orgid=?1 and w.branchcode=?2 and w.docid=?3 and cancel=0")
	Set<Object[]> getWorkOrderDetails(Long orgId, String branchCode, String workOrderNo);

	@Query(nativeQuery = true, value = "select docid\r\n"
			+ " from workorder where orgid=?1  and active=1 and cancel=0")
	Set<Object[]> getWorkOrderDocId(Long orgId);

	WorkOrderShortCloseVO findByOrgIdAndIdAndDocId(Long orgId, Long id, String docId);
	
	@Query(nativeQuery = true, value = "select w.docid,w.docdate,w.customername,w.customercode,w.customerpono,w.productionmgr,w.workordernumber,w1.partno,w1.partname,w1.drawingno,w1.revisionno,w1.uom,w1.orderqty,w1.shortageqty \r\n"
			+ "from workordershortclose w join workordershortclosedetails w1 on w.workordershortcloseid=w1.workordershortcloseid where w.orgid=?1 and w.branchcode=?2  and \r\n"
			+ "(?3 IS NULL OR w.docdate >= ?3)  and  (?4 IS NULL OR w.docdate <= ?4)\r\n"
			+ "group by  w.docid,w.docdate,w.customername,w.customercode,w.customerpono,w.productionmgr,w.workordernumber,w1.partno,w1.partname,w1.drawingno,w1.revisionno,w1.uom,w1.orderqty,w1.shortageqty")
	Set<Object[]> getWorkOrderShortCloseReport(Long orgId,String branchCode,String fromDate,String toDate);
	
}
