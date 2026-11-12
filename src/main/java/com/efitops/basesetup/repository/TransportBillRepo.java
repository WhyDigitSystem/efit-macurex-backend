package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.TransportBillVO;

public interface TransportBillRepo extends JpaRepository<TransportBillVO, Long> {

    boolean existsByBillNoAndOrgId(String billNo, Long orgId);

    boolean existsByBillNoAndOrgIdAndIdNot(String billNo, Long orgId, Long id);

    @Query(value = """
    	    SELECT *
    	    FROM transport_bill
    	    WHERE org_id = :orgId
    	      AND branch = :branch and active=1 
    	      AND cancel = 0
    	    """, nativeQuery = true)
    	List<TransportBillVO> findByOrgIdAndBranch(@Param("orgId") Long orgId,
    	                                           @Param("branch") Long branch);
//    List<TransportBillVO> findByOrgIdAndPlant_Id(Long orgId, Long plantId);

    @Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
	String getTransportBillDocId(Long orgId, String financialYear, String screenCode);
}