package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.JobWorkOutVO;
import com.efitops.basesetup.entity.SubContractEnquiryDetailsVO;
import com.efitops.basesetup.entity.SubContractEnquiryVO;
@Repository
public interface SubContractEnquiryDetailsRepo extends JpaRepository<SubContractEnquiryDetailsVO, Long> {

	List<SubContractEnquiryDetailsVO> findBySubContractEnquiryVO(SubContractEnquiryVO subContractEnquiryVO);

}
