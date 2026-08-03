package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.EnquiryTermsandCondVO;

public interface EnquiryTermsandCondRepo extends JpaRepository<EnquiryTermsandCondVO, Long> {

	@Query(value = """
	        SELECT *
	        FROM enquirytermsandcond
	        WHERE enquiry_id = :enquiryId
	        """, nativeQuery = true)
	List<EnquiryTermsandCondVO> findByEnquiryId(@Param("enquiryId") Long enquiryId);

}