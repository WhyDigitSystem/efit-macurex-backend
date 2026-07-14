package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.EnquirySummaryVO;
import com.efitops.basesetup.entity.EnquiryVO;

@Repository
public interface EnquirySummaryRepo extends JpaRepository<EnquirySummaryVO, Long> {

	List<EnquirySummaryVO> findByEnquiryVO(EnquiryVO enquiryVO);

}
