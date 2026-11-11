package com.efitops.basesetup.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.ProcessSheetCompRoutingVO;

public interface ProcessSheetCompRoutingRepo extends JpaRepository<ProcessSheetCompRoutingVO, Long> {

	

	@Query("SELECT p FROM ProcessSheetCompRoutingVO p " +
		       "WHERE p.orgId = :orgId " +
		       "AND p.branch.id = :branch")
		List<ProcessSheetCompRoutingVO> findByOrgIdAndBranch(
		        @Param("orgId") Long orgId,
		        @Param("branch") Long branch);

	
	
	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and screen_code=?3")
	String getProcessSheetCompRoutingDocId(Long orgId, String financialYear, String screenCode);
	
	@Query(nativeQuery = true, value = "select i.item_id,i.item_code,i.item_description,i.drawing_no from item i left join listofvaluesdetails l1\r\n"
			+ "		                       on l1.listofvaluesdetails_id=i.item_type where i.org_id=?1 and \r\n"
			+ "		                       i.branch=?2 and l1.value_description=?3\r\n"
			+ "		                       union 	            \r\n"
			+ "                               select i.item_id,i.item_code,i.item_description,i.drawing_no from item i left join listofvaluesdetails l1\r\n"
			+ "		                       on l1.listofvaluesdetails_id=i.item_type where i.org_id=?1 and \r\n"
			+ "		                       i.branch=?2 and l1.value_description=?3")
	Set<Object[]> getFgAndSfgItemDetailsProcessSheetComp(Long orgId, Long branch,String type);
	
	@Query(nativeQuery = true, value = "Select ob.operation_master_basic_id,ob.operation_id,ob.description,m.machine_instrument_no,m.machine_instrument_name,m.hourly_rate,t.tool_no,t.tool_description,T.tool_fixture_cost\r\n"
			+ "From operation_master_basic ob,operation_master_tool_details od,machine_equipments_master m,tool_master_basic t,operation_master_machine_details m1\r\n"
			+ "WHERE ob.cancel='F' and ob.org_id=?1 \r\n"
			+ "and m1.machine=m.machine_equipments_master_id\r\n"
			+ "and od.tool_id=t.tool_master_basic_id\r\n"
			+ "and OD.operation_master_basic_id=OB.operation_master_basic_id and \r\n"
			+ "m1.operation_master_basic_id=OB.operation_master_basic_id\r\n"
			+ "Order by ob.operation_master_basic_id,ob.operation_id,ob.description,\r\n"
			+ "m.machine_instrument_no,m.machine_instrument_name,m.hourly_rate,t.tool_no,t.tool_description,T.tool_fixture_cost")
	Set<Object[]> getOperationDetailsProcessSheetComp(Long orgId);
	
	@Query(nativeQuery = true, value = "select machine_equipments_master_id,machine_instrument_no,machine_instrument_name,hourly_rate from \r\n"
			+ "machine_equipments_master where org_id=?1 and branch=?2 group by \r\n"
			+ "machine_equipments_master_id,machine_instrument_no,machine_instrument_name,hourly_rate")
	Set<Object[]> getMachineDetailsProcessSheetComp(Long orgId, Long branch);
	
}
