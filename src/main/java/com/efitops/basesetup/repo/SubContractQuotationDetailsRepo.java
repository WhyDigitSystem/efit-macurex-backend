package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SubContractQuotationDetailsVO;
import com.efitops.basesetup.entity.SubContractQuotationVO;
@Repository
public interface SubContractQuotationDetailsRepo extends JpaRepository<SubContractQuotationDetailsVO, Long> {

	List<SubContractQuotationDetailsVO> findBySubContractQuotationVO(SubContractQuotationVO subContractQuotationVO);

}
