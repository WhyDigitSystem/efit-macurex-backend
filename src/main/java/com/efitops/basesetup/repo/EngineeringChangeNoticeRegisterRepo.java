package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.EngineeringChangeNoticeRegisterVO;
import com.efitops.basesetup.entity.ToolRecieveFromCalibrationVO;

@Repository
public interface EngineeringChangeNoticeRegisterRepo extends JpaRepository<EngineeringChangeNoticeRegisterVO, Long> {

	@Query(nativeQuery = true, value = "select * from engineeringchangenoticeregister  where  orgid=?1 and finyear=?2 and branchcode=?3")
	List<EngineeringChangeNoticeRegisterVO> getEngineeringChangeNoticeRegisterByOrgId(Long orgId, String finYear,
			String branchCode);

	@Query(nativeQuery = true, value = "select * from engineeringchangenoticeregister  where  engineeringchangenoticeregisterid=?1")
	EngineeringChangeNoticeRegisterVO getEngineeringChangeNoticeRegisterById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getEngineeringChangeNoticeRegisterDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "select partyname from partymaster where partytype='CUSTOMER' and orgid=?1 and active=1 and cancel=0")
	Set<Object[]> getCustomerNameFormPartyMaster(Long orgId);

	@Query(nativeQuery = true, value = "select itemdesc,itemname from item where orgid=?1 and  active=1 and cancel=0")
	Set<Object[]> getPartNameFormPartyMaster(Long orgId);

	@Query(nativeQuery = true, value = "SELECT e.engineeringchangenoticeregisterid,e.orgid,e.documentformateno,e1.customer,e1.daterev,e1.detailsofrevision,e1.encrefno,e1.intecnno\r\n"
			+ ",e1.oldrev,e1.oldrevdate,e1.partname,e1.reasonforrevision,e1.remarks,e1.verified\r\n"
			+ " FROM engineeringchangenoticeregister e\r\n"
			+ "join engineeringchange_noticeregisterdetails e1 on e1.engineeringchangenoticeregisterid = e.engineeringchangenoticeregisterid\r\n"
			+ "where e.orgid = ?1 and (?2 is null or e.docdate >= ?2)" + "and (?3 is null or e.docdate <= ?3)")
	Set<Object[]> getEngineeringChangeNoticeRegisterReport(Long orgId, String fromdate, String todate);

	EngineeringChangeNoticeRegisterVO findByDocId(String docId);

	EngineeringChangeNoticeRegisterVO getAllEngineeringChangeNoticeRegisterById(Long id);


}
