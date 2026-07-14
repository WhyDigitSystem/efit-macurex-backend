package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SalesReturnExportDetailsVO;
import com.efitops.basesetup.entity.SalesReturnExportVO;

@Repository
public interface SalesReturnExportDetailsRepo extends JpaRepository<SalesReturnExportDetailsVO, Long> {

	List<SalesReturnExportDetailsVO> findBySalesReturnExportVO(SalesReturnExportVO salesReturnExportVO);

}
