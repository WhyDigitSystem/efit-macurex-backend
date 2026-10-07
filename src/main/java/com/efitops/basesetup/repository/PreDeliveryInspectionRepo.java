package com.efitops.basesetup.repository;


import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PreDeliveryInspectionVO;

@Repository
public interface PreDeliveryInspectionRepo extends JpaRepository<PreDeliveryInspectionVO, Long> {

    @Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and screen_code=?3")
    String getPreDeliveryInspectionDocId(Long orgId, String financialYear, String screenCode);

    @Query(nativeQuery = true, value = "select # from pre_delivery_inspection_basic where pdi_basic_id=?1 and active=1 and cancel=0")
    PreDeliveryInspectionVO getPreDeliveryInspectionById(Long id);

    @Query(nativeQuery = true, value = "select * from pre_delivery_inspection_basic where org_id=?1 and branch=?2 and active=1 and cancel=0")
    List<PreDeliveryInspectionVO> getPreDeliveryInspectionByOrgId(Long orgId, Long branch);
    
    @Query(nativeQuery = true, value = "select doc_id,doc_date from fg_transfer_slip_basic where org_id=?1 and branch=?2 and active=1 and cancel=0")
	Set<Object[]> getFgTransferSlipNo(Long orgId, Long branch);
	
	@Query(nativeQuery = true, value = "select f.fg_item,i.item_code,i.item_description,f.schedule_no,f.schedule_date,i.drawing_no,f.scheduled_qty,i.customer_part_no,f.customer,c.customer_name,c.customer_code from\r\n"
			+ " fg_transfer_slip_basic f left join item i on i.item_id=f.fg_item left join customer_header c on c.customer_id=f.customer\r\n"
			+ " where f.org_id=?1 and f.branch=?2 and f.doc_id=?3 and f.active=1 and f.cancel=0 group by \r\n"
			+ " f.fg_item,i.item_code,i.item_description,f.schedule_no,f.schedule_date,i.drawing_no,f.scheduled_qty,i.customer_part_no,f.customer,c.customer_name,\r\n"
			+ " c.customer_code")
	Set<Object[]> getItemDetailsFromFgTransferSlipNo(Long orgId, Long branch,String transferSlipNo);
	
    @Query(nativeQuery = true, value = "select doc_id,doc_date from initial_planning_basic where org_id=?1  and item=?2")
	Set<Object[]> getInitialPlanningNo(Long orgId, Long item);
	
	@Query(nativeQuery = true, value = "select i1.parameter,i1.specification,i1.uom,i1.acc_criteria,i1.inspection_method,u.unit_id from initial_planning_basic i join initial_planning_details i1 on i.initial_planning_basic_id=i.initial_planning_basic_id\r\n"
			+ "left join unitmaster u on u.unitmaster_id=i1.uom where \r\n"
			+ "i.org_id=?1 and i.item=?2 and i.doc_id=?3 group by i1.parameter,i1.specification,i1.uom,i1.acc_criteria,i1.inspection_method,u.unit_id")
	Set<Object[]> getInspectionDetailsFromFgTransferSlipNo(Long orgId, Long item,String transferSlipNo);
	
}