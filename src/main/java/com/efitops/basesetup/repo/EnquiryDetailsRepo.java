package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.entity.EnquiryDetailsVO;
import com.efitops.basesetup.entity.EnquiryVO;
@Service
public interface EnquiryDetailsRepo extends JpaRepository<EnquiryDetailsVO, Long> {

	List<EnquiryDetailsVO> findByEnquiryVO(EnquiryVO enquiryVO);

}
