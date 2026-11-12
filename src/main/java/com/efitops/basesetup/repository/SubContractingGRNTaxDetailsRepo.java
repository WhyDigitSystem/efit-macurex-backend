package com.efitops.basesetup.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SubContractingGRNTaxDetailsVO;
import com.efitops.basesetup.entity.SubContractingGRNVO;

@Repository
public interface SubContractingGRNTaxDetailsRepo extends JpaRepository<SubContractingGRNTaxDetailsVO, Long>{

	List<SubContractingGRNTaxDetailsVO> findBySubContractingGRNVO(SubContractingGRNVO subContractingGRNVO);

	@Modifying
	@Transactional
	@Query(value = """
	        DELETE FROM sub_contracting_grn_tax_details
	        WHERE sub_contracting_grn_id = :grnId
	        """, nativeQuery = true)
	void deleteByGRNId(@Param("grnId") Long grnId);
}
