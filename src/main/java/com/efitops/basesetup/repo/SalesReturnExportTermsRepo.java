package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SalesReturnExportTermsVO;
import com.efitops.basesetup.entity.SalesReturnExportVO;

@Repository
public interface SalesReturnExportTermsRepo extends JpaRepository<SalesReturnExportTermsVO, Long> {

	List<SalesReturnExportTermsVO> findBySalesReturnExportVO(SalesReturnExportVO salesReturnExportVO);

}
