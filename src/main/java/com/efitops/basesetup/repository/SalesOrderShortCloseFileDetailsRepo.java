package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SalesOrderShortCloseFileDetailsVO;
import com.efitops.basesetup.entity.SalesOrderShortCloseVO;

@Repository
public interface SalesOrderShortCloseFileDetailsRepo extends JpaRepository<SalesOrderShortCloseFileDetailsVO, Long> {

	List<SalesOrderShortCloseFileDetailsVO> findBySalesOrderShortCloseVO(SalesOrderShortCloseVO salesOrderShortCloseVO);

}
