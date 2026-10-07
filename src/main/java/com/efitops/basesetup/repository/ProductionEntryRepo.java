package com.efitops.basesetup.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ProductionEntryVO;

@Repository
public interface ProductionEntryRepo extends JpaRepository<ProductionEntryVO, Long> {

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and screen_code=?3")
	String getProductionEntryDocId(Long orgId, String financialYear, String screenCode);

	@Query(nativeQuery = true, value = "select * from production_entry_basic where production_entry_basic_id=?1 and active=1 and cancel=0")
	ProductionEntryVO getProductionEntryById(Long id);

	@Query(nativeQuery = true, value = "select * from production_entry_basic where org_id=?1 and branch=?2 and active=1 and cancel=0")
	List<ProductionEntryVO> getProductionEntryByOrgId(Long orgId, Long branch);

	@Query(nativeQuery = true, value = "select doc_id,doc_date from production_schedule_order_basic where org_id=?1 and branch=?2 and\r\n"
			+ " fg_item=?3  and active=1 and cancel=0 group by doc_id,doc_date")
	Set<Object[]> getSchNoFromProductionEntry(Long orgId, Long branch, Long fgItem);

	@Query(nativeQuery = true, value = "select doc_id,doc_date,bill_of_material_id from bill_of_material where org_id=?1 and branch=?2 and \r\n"
			+ "fg_item=?3  group by doc_id,doc_date,bill_of_material_id order by doc_date desc")
	Set<Object[]> getBomNoFromProductionEntry(Long orgId, Long branch,Long fgItem);
	
	@Query(nativeQuery = true, value = "select doc_id,doc_date from process_sheet_comp_routing_basic where org_id=?1 and branch=?2 \r\n"
			+ "and fg_sfg_item_code=?3 and active=1 and cancel=0")
	Set<Object[]> getProcessSheetProductionEntry(Long orgId, Long branch,Long fgItem);
	
	@Query(nativeQuery = true, value = "select p1.operation,o.description,m.machine_equipments_master_id,m.machine_instrument_name,m.machine_instrument_no from process_sheet_comp_routing_basic p join process_sheet_comp_routing_detail p1\r\n"
			+ " on p.process_sheet_comp_routing_basic_id=p1.process_sheet_comp_routing_basic_id  left join \r\n"
			+ " operation_master_basic o on o.operation_master_basic_id=p1.operation join  operation_master_machine_details o1 on o1.operation_master_basic_id=o.operation_master_basic_id\r\n"
			+ " left join machine_equipments_master m on m.machine_equipments_master_id=o1.operation_master_machine_details_id where \r\n"
			+ " p.org_id=?1 and p.branch=?2 and p.doc_id=?3")
	Set<Object[]> getProcessSheetOpreationDetailsProductionEntry(Long orgId, Long branch,String processSheet);
}