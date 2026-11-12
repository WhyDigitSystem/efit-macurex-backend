package com.efitops.basesetup.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SubContractingGRNConsumptionVO;
import com.efitops.basesetup.entity.SubContractingGRNDetailsVO;

@Repository
public interface SubContractingGRNConsumptionRepo extends JpaRepository<SubContractingGRNConsumptionVO, Long> {

	List<SubContractingGRNConsumptionVO> findBySubContractingGRNDetailsVO(SubContractingGRNDetailsVO oldDetail);

	@Modifying
	@Transactional
	@Query(value = """
	        DELETE c
	        FROM sub_contracting_grn_consumption c
	        INNER JOIN sub_contracting_grn_details d
	            ON d.sub_contracting_grn_details_id =
	               c.sub_contracting_grn_details_id
	        WHERE d.sub_contracting_grn_id = :grnId
	        """, nativeQuery = true)
	void deleteByDetailsId(@Param("grnId") Long grnId);

	
	

}
