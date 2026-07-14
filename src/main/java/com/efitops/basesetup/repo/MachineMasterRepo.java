package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.MachineMasterVO;

@Repository
public interface MachineMasterRepo extends JpaRepository<MachineMasterVO, Long>{

	@Query(nativeQuery =true,value ="select * from machinemaster where orgid=?1   and branchcode=?2 ")
	List<MachineMasterVO> getMachineMasterByOrgId(Long orgId,  String branchCode);

	@Query(nativeQuery =true,value ="select * from machinemaster where machinemasterid=?1")
	Optional<MachineMasterVO> getMachineMasterById(Long id);

	@Query(nativeQuery = true,value="select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1  and finyear=?2 and branchcode=?3 and screencode=?4")
	String getMachineMasterByDocId(Long orgId, String finYear, String branchCode, String screenCode);

	
    @Query(nativeQuery =true,value ="SELECT * FROM machinemaster where orgid=?1 and docid=?2")
	MachineMasterVO findALLMachineMasterByDocId(Long orgId, String docId);

	MachineMasterVO findByDocId(String docId);

	MachineMasterVO getAllMachineMasterById(Long id);


}