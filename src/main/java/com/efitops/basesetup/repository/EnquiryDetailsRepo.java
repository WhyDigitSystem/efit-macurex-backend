package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.EnquiryDetailsVO;

public interface EnquiryDetailsRepo extends JpaRepository<EnquiryDetailsVO, Long> {
	
	@Query(value = """
	        SELECT *
	        FROM enquirydetails
	        WHERE enquiry_id = :enquiryId
	        """, nativeQuery = true)
	List<EnquiryDetailsVO> findByEnquiryId(@Param("enquiryId") Long enquiryId);

   
}