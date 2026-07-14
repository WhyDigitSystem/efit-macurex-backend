package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.EnquiryAttachmentVO;
import com.efitops.basesetup.entity.EnquiryVO;

public interface EnquiryAttachmentRepo extends JpaRepository<EnquiryAttachmentVO,Long> {

	

	void deleteById(Long id);


	List<EnquiryAttachmentVO> findByEnquiryVO(EnquiryVO enquiryVO);


	void deleteByEnquiryVO(EnquiryVO enquiryVO);

}
