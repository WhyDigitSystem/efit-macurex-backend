package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.EnquiryAttachmentVO;

public interface EnquiryAttachmentRepo extends JpaRepository<EnquiryAttachmentVO, Long> {
	
	@Query(value = """
	        SELECT *
	        FROM enquiryattachment
	        WHERE enquiry_id = :enquiryId
	        """, nativeQuery = true)
	List<EnquiryAttachmentVO> findByEnquiryId(@Param("enquiryId") Long enquiryId);

}
