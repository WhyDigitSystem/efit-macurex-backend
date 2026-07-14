package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.NcProductRegisterVO;

@Repository
public interface NcProductRegisterRepo extends JpaRepository<NcProductRegisterVO, Long> {

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getNcProductRegisterDocId(Long orgId, String finYear, String branchCode, String screenCode);

	@Query(nativeQuery = true, value = "select * from ncproductregister where orgid=?1 and branchcode=?2 and finyear=?3")
	List<NcProductRegisterVO> getNCProductRegisterOrgId(Long orgId,String branchCode,String finYear);

	@Query(nativeQuery = true, value = "select * from ncproductregister where ncproductregisterid=?1")
	NcProductRegisterVO getNCProductRegisterById(Long id);
	
	@Query(nativeQuery = true, value = "SELECT n.ncproductregisterid, n.docid,n.docno,n.docdate,n1.date,n1.stage,n1.partno,n1.partdescription,n1.processdescription,n1.ncquantity,n1.unit,\r\n"
			+ "n1.correctiveaction,n1.caparef,n1.signature,n1.remarks FROM ncproductregister n\r\n"
			+ "join ncproductregisterdetails n1 on n.ncproductregisterid = n1.ncproductregisterid\r\n"
			+ "where n.orgid = ?1 and (?2 is null or n.docdate >= ?2)"
			+ " and (?3 is null or n.docdate <= ?3)  and (n1.partno=?4 or ?4='ALL') ")
	Set<Object[]> getNCProductRegisterReport(Long id,String fromdate,String todate,String partNo);

	NcProductRegisterVO findByDocId(String docId);

	NcProductRegisterVO getAllNcProductRegisterById(Long id);


}
